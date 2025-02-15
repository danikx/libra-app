package kz.netcracker.libra.service;

import kz.netcracker.libra.dto.AuthorDto;
import java.util.List;

public interface AuthorService {
    List<AuthorDto> getAllAuthors();
    AuthorDto getAuthorById(Long id);
    AuthorDto createAuthor(AuthorDto authorDto);
    AuthorDto updateAuthor(Long id, AuthorDto authorDto);
    void deleteAuthor(Long id);
}