package gameapp.dto;


import gameapp.entity.Game;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GameResponse {
    private Long id;
    private String title;
    private String developer;
    private String description;
    private LocalDate releaseDate;
    private String upc;

    public GameResponse(Long id,String title, String developer, String description, LocalDate releaseDate, String upc) {
        this.id = id;
        this.title = title;
        this.developer = developer;
        this.description = description;
        this.releaseDate = releaseDate;
        this.upc = upc;

    }

    public static GameResponse map(Game game) {
        return new GameResponse(
                game.getId(),
                game.getTitle(),
                game.getDeveloper(),
                game.getDescription(),
                game.getReleaseDate().toLocalDate(),
                game.getUpc() );
    }
}
