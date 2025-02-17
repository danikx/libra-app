package kz.netcracker.libra.dto;

import jakarta.validation.constraints.*;
import kz.netcracker.libra.validation.MaxCurrentYear;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "ISBN is required")
    private String isbn;

    @NotNull(message = "publication year is required")
    @Min(value = 1900, message = "Publication year must be after 1900")
    @MaxCurrentYear
    private Integer publicationYear;

    @Min(value = 0, message = "Total copies cannot be negative")
    @Max(value = 10, message = "Total copies cannot be more 10")
    private Integer totalCopies;

    @Min(value = 0, message = "Available copies cannot be negative")
    private Integer availableCopies;

    private String qrCode;

    private String authorFullName;

    @Positive(message = "Author ID must be a positive number")
    private Long authorId;
}