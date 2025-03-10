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

    public static Game map(CreateGame game) {
        if (null == game)
            return null;

        log.info(String.format("Mapping CreateGame: title=%s, developer=%s",
                game.title(), game.developer()));

        Game newGame = new Game();
        newGame.setTitle(game.title());
        newGame.setDeveloper(game.developer());
        newGame.setDescription(game.description());
        newGame.setReleaseDate(game.releaseDate());
        newGame.setUpc(game.upc());

        log.info("Mapped Game entity: " + newGame);

        return newGame;
    }
}
