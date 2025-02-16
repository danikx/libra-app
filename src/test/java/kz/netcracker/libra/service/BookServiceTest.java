package kz.netcracker.libra.service;

import kz.netcracker.libra.dto.BookDto;
import kz.netcracker.libra.entity.Book;
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
    
    @Test
    void createBook_Success() {
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
}