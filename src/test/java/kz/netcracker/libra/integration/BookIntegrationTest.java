package kz.netcracker.libra.integration;

import kz.netcracker.libra.config.TestConfig;
import kz.netcracker.libra.dto.BookDto;
import kz.netcracker.libra.util.TestDataFactory;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestConfig.class)
@Tag("integration")
class BookIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createAndGetBook() {
        // Given
        BookDto bookDto = TestDataFactory.createBookDto();

        // When
        ResponseEntity<BookDto> createResponse = restTemplate.postForEntity("/api/v1/books", bookDto, BookDto.class);

        // Then
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createResponse.getBody()).isNotNull();
        assertThat(createResponse.getBody().getId()).isNotNull();

        // When
        ResponseEntity<BookDto> getResponse = restTemplate.getForEntity(
                "/api/v1/books/" + createResponse.getBody().getId(),
                BookDto.class);

        // Then
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getTitle()).isEqualTo(bookDto.getTitle());
    }
}