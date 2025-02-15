package kz.netcracker.libra.controller;

import jakarta.validation.Valid;
import kz.netcracker.libra.dto.AuthorDto;
import kz.netcracker.libra.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
@Tag(name = "Author Controller", description = "APIs for managing authors")
public class AuthorController {
    private final AuthorService authorService;

    @Operation(summary = "Get all authors")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successfully retrieved authors"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public List<AuthorDto> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @Operation(summary = "Get author by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successfully retrieved author"),
        @ApiResponse(responseCode = "404", description = "Author not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public AuthorDto getAuthor(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @Operation(summary = "Create new author")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Author created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input or author already exists"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public AuthorDto createAuthor(@Valid @RequestBody AuthorDto authorDto) {
        return authorService.createAuthor(authorDto);
    }

    @Operation(summary = "Update existing author")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Author updated successfully"),
        @ApiResponse(responseCode = "404", description = "Author not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public AuthorDto updateAuthor(@PathVariable Long id, @Valid @RequestBody AuthorDto authorDto) {
        return authorService.updateAuthor(id, authorDto);
    }

    @Operation(summary = "Delete author by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Author deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Author not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}