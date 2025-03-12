package gameapp.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreateGame(
        @NotBlank
        String title,

        @NotBlank
        String developer,

        @Size(max = 500)
        String description,

        @Past
        LocalDate releaseDate,

        @Column(unique = true)
        @Pattern(regexp = "^\\d{12}$", message = "UPC must be 12 digits")
        String upc) {
}
