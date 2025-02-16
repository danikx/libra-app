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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;
    private final QRCodeGenerator qrCodeGenerator;

    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto getBookById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
    }

    @Override
    @Transactional
    public BookDto createBook(BookDto bookDto) {
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
        
        return bookMapper.toDto(bookRepository.save(book));
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
        Book book = bookRepository.findByQrCode(qrCode)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with QR code: " + qrCode));

        if (book.getAvailableCopies() <= 0) {
            throw new BookOperationException("No copies available for borrowing");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        return bookMapper.toDto(bookRepository.save(book));
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