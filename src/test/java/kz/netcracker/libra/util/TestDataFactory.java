package kz.netcracker.libra.util;

import kz.netcracker.libra.dto.AuthorDto;
import kz.netcracker.libra.dto.BookDto;
import kz.netcracker.libra.entity.Author;
import kz.netcracker.libra.entity.Book;

public class TestDataFactory {

    public static AuthorDto createAuthorDto() {
        return new AuthorDto(null, "John", "Doe");
    }

    public static Author createAuthor() {
        return new Author(1L, "John", "Doe", null);
    }

    public static BookDto createBookDto() {
        return new BookDto(null, "Test Book", "1234567890", 2023, 5, 5,"DHF123", 1L);
    }

    public static Book createBook() {
        return new Book(1L, "Test Book", "1234567890", 2023, 5, 5, "ABC123", createAuthor());
    }
}