package kz.netcracker.libra.controller;

import jakarta.validation.Valid;
import kz.netcracker.libra.api.BookManagementApi;
import kz.netcracker.libra.dto.BookDto;
import kz.netcracker.libra.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/books", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BookManagementController implements BookManagementApi {
    private final BookService bookService;


    @GetMapping
    @Override
    public ResponseEntity<Page<BookDto>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {
        return ResponseEntity.ok(bookService.getAllBooks(page, size, sortBy, direction));
    }

    @GetMapping("/{id}")
    @Override
    public BookDto getBook(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/author/{authorId}")
    @Override
    public List<BookDto> getBooksByAuthor(@PathVariable Long authorId) {
        return bookService.getBooksByAuthorId(authorId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public BookDto createBook(@Valid @RequestBody BookDto bookDto) {
        return bookService.createBook(bookDto);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public BookDto updateBook(@PathVariable Long id, @Valid @RequestBody BookDto bookDto) {
        return bookService.updateBook(id, bookDto);
    }

    @PutMapping("/return/qr/{qrCode}")
    @Override
    public BookDto returnBook(@PathVariable String qrCode) {
        return bookService.returnBookByQrCode(qrCode);
    }

    @PutMapping("/borrow/qr/{qrCode}")
    @Override
    public BookDto borrowBookByQRCode(@PathVariable String qrCode) {
        return bookService.borrowBookByQrCode(qrCode);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}