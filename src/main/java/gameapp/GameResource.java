package gameapp;

import gameapp.dto.GameResponse;
import gameapp.entity.Game;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.java.Log;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Path("games")
@Log
public class GameResource {

    private static final Logger logger = Logger.getLogger(GameResource.class.getName());

    private EntityManager entityManager;

    public GameResource() {}

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public GameResponse firstTest() {
        Game newGame = new Game();
        entityManager.persist(newGame);
        entityManager.flush();

        Long gameId = newGame.getId();

        var game = entityManager.find(Game.class, gameId);
        logger.info(game.toString());
        return new GameResponse(
                game.getTitle(),
                game.getDeveloper(),
                game.getDescription(),
                game.getReleaseDate().toLocalDate(),
                game.getUpc()
        );
    }

    @GET
    @Path("first")
    @Produces(MediaType.APPLICATION_JSON)
    public GameResponse firstCreate() {
        return new GameResponse("The Legend of Zelda: Breath of the Wild", "Nintendo", "An open-world action-adventure game.", LocalDate.of(2017, 3, 3), "1234567890");
    }

    @GET
    @Path("many")
    @Produces(MediaType.APPLICATION_JSON)
    public Games manyCreate() {
        List<GameResponse> games = new ArrayList<>();
        games.add(new GameResponse("The Legend of Zelda: Breath of the Wild", "Nintendo", "An open-world action-adventure game.", LocalDate.of(2017, 3, 3), "1234567890"));
        games.add(new GameResponse("The Witcher 3: Wild Hunt", "CD Projekt Red", "An open-world RPG.", LocalDate.of(2015, 5, 19), "1234567891"));
        games.add(new GameResponse("Red Dead Redemption 2", "Rockstar Games", "An epic tale of life in America at the dawn of the modern age.", LocalDate.of(2018, 10, 26), "1234567892"));
        games.add(new GameResponse("God of War", "Santa Monica Studio", "An action-adventure game set in Norse mythology.", LocalDate.of(2018, 4, 20), "1234567893"));
        games.add(new GameResponse("Minecraft", "Mojang", "A sandbox video game.", LocalDate.of(2011, 11, 18), "1234567894"));
        return new Games(games, games.size());
    }

    public record Games(List<GameResponse> games, int totalGames) {}
}
