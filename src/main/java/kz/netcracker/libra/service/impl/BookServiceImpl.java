package kz.netcracker.libra.service.impl;

import kz.netcracker.libra.dto.BookDto;
import kz.netcracker.libra.entity.Author;
import kz.netcracker.libra.entity.Book;
import kz.netcracker.libra.exception.BookOperationException;
import kz.netcracker.libra.exception.DuplicateEntityException;
import kz.netcracker.libra.exception.EntityNotFoundException;
import kz.netcracker.libra.mapper.BookMapper;
import kz.netcracker.libra.repository.AuthorRepository;
import kz.netcracker.libra.repository.BookRepository;
import kz.netcracker.libra.service.BookService;
import kz.netcracker.libra.util.QRCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;
    private final QRCodeGenerator qrCodeGenerator;

    @Override
    public Page<BookDto> getAllBooks(int page, int size, String sortBy, String direction) {
        log.debug("Fetching books page={}, size={}, sortBy={}, direction={}", page, size, sortBy, direction);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(direction.toUpperCase()), sortBy);
        Page<Book> bookPage = bookRepository.findAll(pageable);
        log.debug("Found {} books", bookPage.getTotalElements());
        return bookPage.map(bookMapper::toDto);
    }

    @Override
    public BookDto getBookById(Long id) {
        log.debug("Fetching book with id: {}", id);

        return bookRepository.findById(id)
                .map(bookMapper::toDto)
                .orElseThrow(() -> {
                    log.error("Book not found with id: {}", id);
                    return new EntityNotFoundException("Book not found with id: " + id);
                });
    }

    @Override
    @Transactional
    public BookDto createBook(BookDto bookDto) {
        log.debug("Creating new book: {}", bookDto);

        // Check for duplicate ISBN
        if (bookRepository.existsByIsbnIgnoreCase(bookDto.getIsbn())) {
            throw new DuplicateEntityException("Book with ISBN " + bookDto.getIsbn() + " already exists");
        }

        // Check for duplicate title by the same author
        if (bookRepository.existsByTitleIgnoreCaseAndAuthorId(bookDto.getTitle(), bookDto.getAuthorId())) {
            throw new DuplicateEntityException("Book with title '" + bookDto.getTitle() +
                    "' already exists for this author");
        }

        // Set available copies if not provided
        if (bookDto.getAvailableCopies() == null) {
            bookDto.setAvailableCopies(bookDto.getTotalCopies());
        }

        // Validate available copies
        if (bookDto.getAvailableCopies() > bookDto.getTotalCopies()) {
            throw new IllegalArgumentException("Available copies cannot exceed total copies");
        }

        Author author = authorRepository.findById(bookDto.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + bookDto.getAuthorId()));

        Book book = bookMapper.toEntity(bookDto);
        book.setAuthor(author);
        book.setQrCode(qrCodeGenerator.generateQRCode());

        Book savedBook = bookRepository.save(book);
        log.info("Created new book with id: {}", savedBook.getId());
        return bookMapper.toDto(savedBook);
    }

    @Override
    @Transactional
    public BookDto updateBook(Long id, BookDto bookDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));

        Author author = authorRepository.findById(bookDto.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + bookDto.getAuthorId()));

        bookMapper.updateEntity(book, bookDto);
        book.setAuthor(author);

        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> getBooksByAuthorId(Long authorId) {
        return bookRepository.findByAuthorId(authorId).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public BookDto borrowBookByQrCode(String qrCode) {
        log.debug("Attempting to borrow book with QR code: {}", qrCode);

        Book book = bookRepository.findByQrCode(qrCode)
                .orElseThrow(() -> {
                    log.error("Book not found with QR code: {}", qrCode);
                    return new EntityNotFoundException("Book not found with QR code: " + qrCode);
                });

        if (book.getAvailableCopies() <= 0) {
            log.warn("No copies available for borrowing book with id: {}", book.getId());
            throw new BookOperationException("No copies available for borrowing");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        
        Book savedBook = bookRepository.save(book);
        log.info("Book borrowed successfully, id: {}, remaining copies: {}", 
                savedBook.getId(), savedBook.getAvailableCopies());

        return bookMapper.toDto(savedBook);
    }

    @Override
    @Transactional
    public BookDto returnBookByQrCode(String qrCode) {
        Book book = bookRepository.findByQrCode(qrCode)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with QR code: " + qrCode));

        if (book.getAvailableCopies() >= book.getTotalCopies()) {
            throw new BookOperationException("All copies are already returned");
        }

        book.setAvailableCopies(book.getAvailableCopies() + 1);
        return bookMapper.toDto(bookRepository.save(book));
    }
}