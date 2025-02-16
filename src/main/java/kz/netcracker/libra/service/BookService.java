package kz.netcracker.libra.service;

import kz.netcracker.libra.dto.BookDto;

import java.util.List;

public interface BookService {

    List<BookDto> getAllBooks();

    BookDto getBookById(Long id);

    BookDto createBook(BookDto bookDto);

    BookDto updateBook(Long id, BookDto bookDto);

    List<BookDto> getBooksByAuthorId(Long authorId);

    BookDto borrowBookByQrCode(String qrCode);

    BookDto returnBookByQrCode(String qrCode);

    void deleteBook(Long id);
}