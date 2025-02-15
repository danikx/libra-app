package kz.netcracker.libra.service;

import kz.netcracker.libra.dto.BookDto;
import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks();
    BookDto getBookById(Long id);
    BookDto createBook(BookDto bookDto);
    BookDto updateBook(Long id, BookDto bookDto);
    BookDto borrowBook(Long id);
    BookDto returnBook(Long id);
    void deleteBook(Long id);
    List<BookDto> getBooksByAuthorId(Long authorId);
}