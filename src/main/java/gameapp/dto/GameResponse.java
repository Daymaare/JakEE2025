package gameapp.dto;


import gameapp.entity.Game;
import jakarta.validation.constraints.*;

import java.time.LocalDate;


public record GameResponse(
        Long id,
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

    public GameResponse(Game game) {
        this(
                game.getId(),
                game.getTitle(),
                game.getDeveloper(),
                game.getDescription(),
                game.getReleaseDate(),
                game.getUpc());
    }

    public static GameResponse map(Game game) {
        return new GameResponse(
                game.getId(),
                game.getTitle(),
                game.getDeveloper(),
                game.getDescription(),
                game.getReleaseDate(),
                game.getUpc());
    }
}
