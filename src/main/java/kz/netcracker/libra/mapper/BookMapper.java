package kz.netcracker.libra.mapper;

import kz.netcracker.libra.dto.BookDto;
import kz.netcracker.libra.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {AuthorMapper.class})
public interface BookMapper {

    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "authorFullName", expression = "java(getAuthorFullName(book))")
    BookDto toDto(Book book);

    @Mapping(target = "author", ignore = true)
    Book toEntity(BookDto bookDto);

    @Mapping(target = "author", ignore = true)
    void updateEntity(@MappingTarget Book book, BookDto bookDto);

    default String getAuthorFullName(Book book) {
        if (book.getAuthor() == null) {
            return null;
        }
        return String.format("%s %s",
                book.getAuthor().getFirstName(),
                book.getAuthor().getLastName());
    }
}