package gameapp.mapper;


import gameapp.dto.CreateGame;
import gameapp.dto.GameResponse;
import gameapp.entity.Game;
import lombok.extern.java.Log;

@Log
public class GameMapper {


    private GameMapper() {
    }

    public static GameResponse map(Game game) {
        if (null == game){
            log.warning("CreateGame object is null.");
        return null;
        }
        return new GameResponse(
                game.getId(),
                game.getTitle(),
                game.getDeveloper(),
                game.getDescription(),
                game.getReleaseDate(),
                game.getUpc());
    }

    public static Game map(CreateGame createGame) {
        if (null == createGame)
            return null;

        log.info(String.format("Mapping CreateGame: title=%s, developer=%s",
                createGame.title(), createGame.developer()));

        Game game = new Game();
        game.setTitle(createGame.title());
        game.setDeveloper(createGame.developer());
        game.setDescription(createGame.description());
        game.setReleaseDate(createGame.releaseDate());
        game.setUpc(createGame.upc());

        log.info("Mapped Game entity: " + game);

        return game;
    }
}
