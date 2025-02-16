package kz.netcracker.libra.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="Books", description="Book operations")
public class BookController {
    private final BookService bookService;

    @GetMapping
    @Operation(summary = "Get all books", description = "Retrieve a list of all books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a book by ID", description = "Retrieve a book by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved book"),
            @ApiResponse(responseCode = "404", description = "The book with the given ID was not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public BookDto getBook(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/author/{authorId}")
    @Operation(summary = "Get books by author ID", description = "Retrieve a list of books by author ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "404", description = "The author with the given ID was not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<BookDto> getBooksByAuthor(@PathVariable Long authorId) {
        return bookService.getBooksByAuthorId(authorId);
    }

    @PostMapping
    @Operation(summary = "Create a new book", description = "Create a new book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created book"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public BookDto createBook(@Valid @RequestBody BookDto bookDto) {
        return bookService.createBook(bookDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a book", description = "Update a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated book"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "The book with the given ID was not found")
    })
    public BookDto updateBook(@PathVariable Long id, @Valid @RequestBody BookDto bookDto) {
        return bookService.updateBook(id, bookDto);
    }

    @PutMapping("/return/qr/{qrCode}")
    @Operation(summary = "Return a book", description = "Return a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned book"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "The book with the given ID was not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public BookDto returnBook(@PathVariable String qrCode) {
        return bookService.returnBookByQrCode(qrCode);
    }

    @PutMapping("/borrow/qr/{qrCode}")
    @Operation(summary = "Borrow a book", description = "Borrow a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully borrowed book"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "The book with the given ID was not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public BookDto borrowBookByQRCode(@PathVariable String qrCode) {
        return bookService.borrowBookByQrCode(qrCode);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book", description = "Delete a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted book"),
            @ApiResponse(responseCode = "404", description = "The book with the given ID was not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}