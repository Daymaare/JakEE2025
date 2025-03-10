package gameapp.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreateGame(
        @NotBlank
        @NotNull
        String title,
        @NotBlank
        String developer,
        @Size(max = 500)
        String description,
        @Past
        LocalDate releaseDate,
        @Pattern(regexp = "^\\d{12}$", message = "UPC must be 12 digits")
        String upc) {
}
