package kz.netcracker.libra.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.netcracker.libra.dto.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Tag(name = "Books", description = "Book operations")
public interface BookManagementApi {

    @Operation(summary = "Get all books", description = "Retrieve a paginated list of all books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved paginated list"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Page<BookDto>> getAllBooks(
            @Parameter(description = "Page number (zero-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort field name (title, author.firstName)") @RequestParam(defaultValue = "title") String sortBy,
            @Parameter(description = "Sort direction (ASC or DESC)") @RequestParam(defaultValue = "ASC") String direction);


    @Operation(summary = "Get a book by ID", description = "Retrieve a book by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved book"),
            @ApiResponse(responseCode = "404", description = "The book with the given ID was not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    BookDto getBook(@PathVariable Long id);


    @Operation(summary = "Get books by author ID", description = "Retrieve a list of books by author ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "404", description = "The author with the given ID was not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    List<BookDto> getBooksByAuthor(@PathVariable Long authorId);


    @Operation(summary = "Create a new book", description = "Create a new book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created book"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    BookDto createBook(@Valid @RequestBody BookDto bookDto);


    @Operation(summary = "Update a book", description = "Update a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated book"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "The book with the given ID was not found")
    })
    BookDto updateBook(@PathVariable Long id, @Valid @RequestBody BookDto bookDto);


    @Operation(summary = "Return a book", description = "Return a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned book"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "The book with the given ID was not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    BookDto returnBook(@PathVariable String qrCode);


    @Operation(summary = "Borrow a book", description = "Borrow a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully borrowed book"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "The book with the given ID was not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    BookDto borrowBookByQRCode(@PathVariable String qrCode);

    @Operation(summary = "Delete a book", description = "Delete a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted book"),
            @ApiResponse(responseCode = "404", description = "The book with the given ID was not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Void> deleteBook(@PathVariable Long id);
}