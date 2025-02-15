package kz.netcracker.libra.controller;

import jakarta.validation.Valid;
import kz.netcracker.libra.dto.BookDto;
import kz.netcracker.libra.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookDto getBook(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/author/{authorId}")
    public List<BookDto> getBooksByAuthor(@PathVariable Long authorId) {
        return bookService.getBooksByAuthorId(authorId);
    }

    @PostMapping
    public BookDto createBook(@Valid @RequestBody BookDto bookDto) {
        return bookService.createBook(bookDto);
    }

    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable Long id, @Valid @RequestBody BookDto bookDto) {
        return bookService.updateBook(id, bookDto);
    }

    @PutMapping("/{id}/borrow")
    public BookDto borrowBook(@PathVariable Long id) {
        return bookService.borrowBook(id);
    }

    @PutMapping("/{id}/return")
    public BookDto returnBook(@PathVariable Long id) {
        return bookService.returnBook(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}