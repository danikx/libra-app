package kz.netcracker.libra.service;

import kz.netcracker.libra.dto.BookDto;
import kz.netcracker.libra.entity.Book;
import kz.netcracker.libra.event.BookEventPublisher;
import kz.netcracker.libra.event.EventType;
import kz.netcracker.libra.exception.EntityNotFoundException;
import kz.netcracker.libra.mapper.BookMapper;
import kz.netcracker.libra.repository.AuthorRepository;
import kz.netcracker.libra.repository.BookRepository;
import kz.netcracker.libra.service.impl.BookServiceImpl;
import kz.netcracker.libra.util.QRCodeGenerator;
import kz.netcracker.libra.util.TestDataFactory;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    
    @Mock
    private AuthorRepository authorRepository;
    
    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private QRCodeGenerator qrCodeGenerator;

    @Mock
    private BookEventPublisher eventPublisher;
    
    @Test
    void create_book_success() {
        // Given
        BookDto bookDto = TestDataFactory.createBookDto();
        Book book = TestDataFactory.createBook();

        when(bookMapper.toEntity(any())).thenReturn(book);
        when(bookRepository.save(any())).thenReturn(book);
        when(bookMapper.toDto(any())).thenReturn(bookDto);
        
        when(authorRepository.findById(any())).thenReturn(Optional.of(TestDataFactory.createAuthor()));
        when(bookRepository.save(any())).thenReturn(TestDataFactory.createBook());
        when(qrCodeGenerator.generateQRCode()).thenReturn("ABC123");  

        // When
        BookDto result = bookService.createBook(bookDto);

        // Then
        assertThat(result).isNotNull();
    }

    @Test
    void borrow_book_success() {
        // Given
        String qrCode = "ABC123";
        Book book = TestDataFactory.createBook();
        book.setAvailableCopies(5);
        BookDto bookDto = TestDataFactory.createBookDto();
        
        when(bookRepository.findByQrCode(qrCode)).thenReturn(Optional.of(book));
        when(bookRepository.save(any())).thenReturn(book);
        when(bookMapper.toDto(any())).thenReturn(bookDto);

        // When
        BookDto result = bookService.borrowBookByQrCode(qrCode);

        // Then
        assertThat(result).isNotNull();
        assertThat(book.getAvailableCopies()).isEqualTo(4);
    }

    @Test
    void return_book_success() {
        // Given
        String qrCode = "ABC123";
        Book book = TestDataFactory.createBook();
        book.setAvailableCopies(4);
        book.setTotalCopies(5);
        BookDto bookDto = TestDataFactory.createBookDto();
        
        when(bookRepository.findByQrCode(qrCode)).thenReturn(Optional.of(book));
        when(bookRepository.save(any())).thenReturn(book);
        when(bookMapper.toDto(any())).thenReturn(bookDto);

        // When
        BookDto result = bookService.returnBookByQrCode(qrCode);

        // Then
        assertThat(result).isNotNull();
        assertThat(book.getAvailableCopies()).isEqualTo(5);
    }
}