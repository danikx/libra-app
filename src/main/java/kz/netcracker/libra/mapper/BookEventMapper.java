package kz.netcracker.libra.mapper;

import kz.netcracker.libra.dto.BookDto;
import kz.netcracker.libra.event.BookEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookEventMapper {

    BookEvent toEvent(BookDto bookDto);
}