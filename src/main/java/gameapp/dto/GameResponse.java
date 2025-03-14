package gameapp.dto;


import gameapp.entity.Game;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;


public record GameResponse(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(nullable = false)
        Long id,

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

    public GameResponse(Game game) {
        this(
                game.getId(),
                game.getTitle(),
                game.getDeveloper(),
                game.getDescription(),
                game.getReleaseDate(),
                game.getUpc());
    }

}
