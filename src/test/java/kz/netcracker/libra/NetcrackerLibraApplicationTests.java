package kz.netcracker.libra;

import kz.netcracker.libra.dto.AuthorDto;
import kz.netcracker.libra.dto.BookDto;
import kz.netcracker.libra.service.AuthorService;
import kz.netcracker.libra.service.BookService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@Tag("integration")
class NetcrackerLibraApplicationTests {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Test
    void contextLoads() {
        assertThat(bookService).isNotNull();
        assertThat(authorService).isNotNull();
    }

    @Test
    void applicationStartsWithoutErrors() {
        assertDoesNotThrow(() -> {
            Page<BookDto> books = bookService.getAllBooks(0, 10, "title", "ASC");
            List<AuthorDto> authors = authorService.getAllAuthors();

            // Verify sample data was loaded
            assertThat(books).isNotEmpty();
            assertThat(authors).isNotEmpty();
        });
    }
}
