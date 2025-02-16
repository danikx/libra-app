package kz.netcracker.libra.controller;

import jakarta.validation.Valid;
import kz.netcracker.libra.api.AuthorManagementApi;
import kz.netcracker.libra.dto.AuthorDto;
import kz.netcracker.libra.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/authors", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthorManagementController implements AuthorManagementApi {
    private final AuthorService authorService;

    @GetMapping
    @Override
    public List<AuthorDto> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    @Override
    public AuthorDto getAuthor(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public AuthorDto createAuthor(@Valid @RequestBody AuthorDto authorDto) {
        return authorService.createAuthor(authorDto);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public AuthorDto updateAuthor(@PathVariable Long id, @Valid @RequestBody AuthorDto authorDto) {
        return authorService.updateAuthor(id, authorDto);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}