package kz.netcracker.libra.service;

import kz.netcracker.libra.dto.AuthorDto;
import kz.netcracker.libra.entity.Author;
import kz.netcracker.libra.exception.EntityNotFoundException;
import kz.netcracker.libra.mapper.AuthorMapper;
import kz.netcracker.libra.repository.AuthorRepository;
import kz.netcracker.libra.service.impl.AuthorServiceImpl;
import kz.netcracker.libra.util.TestDataFactory;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
class AuthorServiceTest {
    @Mock
    private AuthorRepository authorRepository;
    
    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    void createAuthor_Success() {
        // Given
        AuthorDto authorDto = TestDataFactory.createAuthorDto();
        Author author = TestDataFactory.createAuthor();
        
        when(authorMapper.toEntity(any())).thenReturn(author);
        when(authorRepository.save(any())).thenReturn(author);
        when(authorMapper.toDto(any())).thenReturn(authorDto);

        // When
        AuthorDto result = authorService.createAuthor(authorDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo(authorDto.getFirstName());
        assertThat(result.getLastName()).isEqualTo(authorDto.getLastName());
    }

    @Test
    void getAuthorById_NotFound() {
        // Given
        Long id = 999L;
        when(authorRepository.findById(id)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> authorService.getAuthorById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Author not found");
    }
}