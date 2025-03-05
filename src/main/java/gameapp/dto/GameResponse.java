package gameapp.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GameResponse {
    private String title;
    private String developer;
    private String description;
    private LocalDate releaseDate;
    private String upc;

    public GameResponse(String title, String developer, String description, LocalDate releaseDate, String upc) {
        this.title = title;
        this.developer = developer;
        this.description = description;
        this.releaseDate = releaseDate;
        this.upc = upc;

    }
}
