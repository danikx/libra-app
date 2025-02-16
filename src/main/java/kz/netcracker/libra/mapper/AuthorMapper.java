package kz.netcracker.libra.mapper;

import kz.netcracker.libra.dto.AuthorDto;
import kz.netcracker.libra.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDto toDto(Author author);

    Author toEntity(AuthorDto authorDto);

    void updateEntity(@MappingTarget Author author, AuthorDto authorDto);
}