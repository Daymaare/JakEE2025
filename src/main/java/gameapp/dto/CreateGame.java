package gameapp.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreateGame(
        @NotBlank
        String title,

        @NotBlank
        String developer,

        @Size(max = 500, message = "Description cannot exceed 500 characters")
        String description,

        @NotNull(message = "Release Date must not be null")
        LocalDate releaseDate,

        @Column(unique = true)
        @Pattern(regexp = "^\\d{12}$", message = "UPC must be 12 digits")
        String upc) {
}
