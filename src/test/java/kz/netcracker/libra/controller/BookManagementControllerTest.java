package kz.netcracker.libra.controller;

import kz.netcracker.libra.config.TestSecurityConfig;
import kz.netcracker.libra.dto.BookDto;
import kz.netcracker.libra.service.BookService;
import kz.netcracker.libra.util.TestDataFactory;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookManagementController.class)
@Tag("unit")
@Import(TestSecurityConfig.class)
class BookManagementControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    // @WithMockUser(username = "test", roles = "USER")
    void createBook_Success() throws Exception {
        // Given
        BookDto bookDto = TestDataFactory.createBookDto();
        when(bookService.createBook(any())).thenReturn(TestDataFactory.createBookDto());

        // When/Then
        mockMvc.perform(post("/api/v1/books")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "Test Book",
                                    "isbn": "1234567890",
                                    "publicationYear": 2023,
                                    "totalCopies": 5,
                                    "authorId": 1
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"));
    }
}