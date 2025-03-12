package gameapp.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UpdateGame(

        String title,

        String developer,

        @Size(max = 500, message = "Description cannot exceed 500 characters")
        String description,

        @Past
        LocalDate releaseDate,

        @Column(unique = true)
        @Pattern(regexp = "^\\d{12}$", message = "UPC must be 12 digits")
        String upc) {
}
