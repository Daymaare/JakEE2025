package gameapp.mapper;


import gameapp.dto.CreateGame;
import gameapp.dto.GameResponse;
import gameapp.entity.Game;

public class GameMapper {

    private GameMapper() {
    }

    public static GameResponse map(Game game) {
        if (null == game)
            return null;
        return new GameResponse(
                game.getId(),
                game.getTitle(),
                game.getDeveloper(),
                game.getDescription(),
                game.getReleaseDate().toLocalDate(),
                game.getUpc());
    }

    public static Game map(CreateGame game) {
        if (null == game)
            return null;
        Game newGame = new Game();
        newGame.setTitle(game.title());
        newGame.setDeveloper(game.developer());
        newGame.setDescription(game.description());
        return newGame;
    }
}
