package kz.netcracker.libra.controller;

import kz.netcracker.libra.config.TestSecurityConfig;
import kz.netcracker.libra.dto.AuthorDto;
import kz.netcracker.libra.service.AuthorService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorManagementController.class)
@Import(TestSecurityConfig.class)
@Tag("unit")
class AuthorManagementControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Test
    void createAuthor_Success() throws Exception {
        // Given
        AuthorDto authorDto = TestDataFactory.createAuthorDto();
        when(authorService.createAuthor(any())).thenReturn(authorDto);

        // When/Then
        mockMvc.perform(post("/api/v1/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "John",
                                    "lastName": "Doe"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void getAuthor_Success() throws Exception {
        // Given
        AuthorDto authorDto = TestDataFactory.createAuthorDto();
        when(authorService.getAuthorById(1L)).thenReturn(authorDto);

        // When/Then
        mockMvc.perform(get("/api/v1/authors/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }
}