package kz.netcracker.libra.service;

import kz.netcracker.libra.dto.BookDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {

    Page<BookDto> getAllBooks(int page, int size, String sortBy, String direction);

    BookDto getBookById(Long id);

    BookDto createBook(BookDto bookDto);

    BookDto updateBook(Long id, BookDto bookDto);

    List<BookDto> getBooksByAuthorId(Long authorId);

    BookDto borrowBookByQrCode(String qrCode);

    BookDto returnBookByQrCode(String qrCode);

    void deleteBook(Long id);
}