package gameapp.dto;


import gameapp.entity.Game;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

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
