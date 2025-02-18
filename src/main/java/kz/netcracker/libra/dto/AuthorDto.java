package kz.netcracker.libra.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonAlias;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {
    private Long id;

    @NotBlank(message = "First name is required")
     @JsonAlias({"firstName", "first_name"})
    private String firstName;

    @NotBlank(message = "Last name is required")
    @JsonAlias({"lastName", "last_name"})
    private String lastName;
}