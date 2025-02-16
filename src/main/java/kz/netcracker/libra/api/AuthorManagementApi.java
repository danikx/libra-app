package kz.netcracker.libra.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.netcracker.libra.dto.AuthorDto;
import kz.netcracker.libra.dto.ErrorResponseDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Author Controller", description = "APIs for managing authors")
public interface AuthorManagementApi {

    @Operation(summary = "Get all authors")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved authors"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    List<AuthorDto> getAllAuthors();

    @Operation(summary = "Get author by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved author"),
            @ApiResponse(responseCode = "404", description = "Author not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    AuthorDto getAuthor(@PathVariable Long id);

    @Operation(summary = "Create new author")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Author created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = AuthorDto.class
                            ),
                            examples = @ExampleObject(
                                    name = "Example Response",
                                    value = """
                                            {
                                                "id": 1,
                                                "firstName": "John",
                                                "lastName": "Doe"
                                            }
                                            """
                            )
                    )),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input or author already exists",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "timestamp": "2024-02-16T12:34:56.789",
                                                "status": 400,
                                                "error": "Bad Request",
                                                "message": "First name is required",
                                                "path": "/api/v1/authors"
                                            }
                                            """
                            )
                    )),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "timestamp": "2024-02-16T12:34:56.789",
                                                "status": 500,
                                                "error": "Internal Server Error",
                                                "message": "An unexpected error occurred",
                                                "path": "/api/v1/authors"
                                            }
                                            """
                            )
                    ))
    })
    AuthorDto createAuthor(@Valid @RequestBody AuthorDto authorDto);

    @Operation(summary = "Update existing author")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author updated successfully"),
            @ApiResponse(responseCode = "404", description = "Author not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    AuthorDto updateAuthor(@PathVariable Long id, @Valid @RequestBody AuthorDto authorDto);

    @Operation(summary = "Delete author by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Author deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Author not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Void> deleteAuthor(@PathVariable Long id);
}