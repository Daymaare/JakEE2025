package gameapp;

import gameapp.dto.GameResponse;
import gameapp.entity.Game;
import gameapp.mapper.GameMapper;
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

    public GameResource() {
        // default implementation
    }

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
        return GameMapper.map(game);
    }

    @GET
    @Path("first")
    @Produces(MediaType.APPLICATION_JSON)
    public GameResponse firstCreate() {
        Game game = new Game();
        game.setTitle("The Legend of Zelda: Breath of the Wild");
        game.setDeveloper("Nintendo");
        game.setDescription("An open-world action-adventure game.");
        game.setReleaseDate(LocalDate.of(2017, 3, 3).atStartOfDay());
        game.setUpc("1234567890");

        return GameMapper.map(game);
    }

    @GET
    @Path("many")
    @Produces(MediaType.APPLICATION_JSON)
    public Games manyCreate() {
        List<GameResponse> games = new ArrayList<>();
        games.add(new GameResponse(1L, "The Legend of Zelda: Breath of the Wild", "Nintendo", "An open-world action-adventure game.", LocalDate.of(2017, 3, 3), "1234567890"));
        games.add(new GameResponse(2L, "The Witcher 3: Wild Hunt", "CD Projekt Red", "An open-world RPG.", LocalDate.of(2015, 5, 19), "1234567891"));
        games.add(new GameResponse(3L, "Red Dead Redemption 2", "Rockstar Games", "An epic tale of life in America at the dawn of the modern age.", LocalDate.of(2018, 10, 26), "1234567892"));
        games.add(new GameResponse(4L, "God of War", "Santa Monica Studio", "An action-adventure game set in Norse mythology.", LocalDate.of(2018, 4, 20), "1234567893"));
        games.add(new GameResponse(5L, "Minecraft", "Mojang", "A sandbox video game.", LocalDate.of(2011, 11, 18), "1234567894"));
        return new Games(games, games.size());
    }

    public record Games(List<GameResponse> games, int totalGames) {
    }
}
