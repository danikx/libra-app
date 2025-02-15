package kz.netcracker.libra.service.impl;

import kz.netcracker.libra.dto.AuthorDto;
import kz.netcracker.libra.entity.Author;
import kz.netcracker.libra.exception.DuplicateEntityException;
import kz.netcracker.libra.exception.EntityNotFoundException;
import kz.netcracker.libra.mapper.AuthorMapper;
import kz.netcracker.libra.repository.AuthorRepository;
import kz.netcracker.libra.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDto getAuthorById(Long id) {
        return authorRepository.findById(id)
                .map(authorMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));
    }

    @Override
    @Transactional
    public AuthorDto createAuthor(AuthorDto authorDto) {
        return Optional.of(authorDto)
                .filter(dto -> !authorRepository.existsByFirstNameAndLastName(dto.getFirstName(), dto.getLastName()))
                .map(authorMapper::toEntity)
                .map(authorRepository::save)
                .map(authorMapper::toDto)
                .orElseThrow(() -> new DuplicateEntityException("Author already exists with name: " +
                        authorDto.getFirstName() + " " + authorDto.getLastName()));
    }

    @Override
    @Transactional
    public AuthorDto updateAuthor(Long id, AuthorDto authorDto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));
        authorMapper.updateEntity(author, authorDto);
        return authorMapper.toDto(authorRepository.save(author));
    }

    @Override
    @Transactional
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}