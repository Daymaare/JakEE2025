package gameapp.business;

import gameapp.exceptions.BadRequest;
import gameapp.persistence.GameRepository;
import gameapp.dto.CreateGame;
import gameapp.dto.GameResponse;
import gameapp.dto.UpdateGame;
import gameapp.entity.Game;
import gameapp.exceptions.NotFound;
import gameapp.mapper.GameMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.PathParam;
import lombok.extern.java.Log;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
@Log
public class GameService {

    private GameRepository gameRepository;

    @Inject
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public GameService() {
        this.gameRepository = null;
    }

    public List<GameResponse> getAllGames() {
        List<Game> games = gameRepository.findAll().toList();
        if (games.isEmpty()) {
            throw new NotFound("No games found");
        }
        log.info("Getting all games...");
        return games.stream()
                .filter(Objects::nonNull)
                .map(GameMapper::map)
                .toList();
    }

    public GameResponse getGameById(@PathParam("id") Long id) {
        var game = gameRepository.findById(id);
        if (game.isEmpty()) {
            throw new NotFound("Game with ID " + id + " not found");
        }
        log.info("Getting game with ID " + id);
        return game.map(GameMapper::map).orElseThrow(
                () -> new NotFound("Game with ID " + id + " not found"));
    }

    public Game createGame(@Valid CreateGame createGame) {
        if (createGame == null) {
            log.warning("Game cannot be null");
            throw new BadRequest("Game cannot be null");
        }
        log.info("Creating game: " + createGame);
        gameRepository.findByTitleAndDeveloper(createGame.title(), createGame.developer())
                .forEach(existingGame -> {
                    throw new BadRequest(
                            "A game with the title '" + createGame.title() + "' and developer '"
                                    + createGame.developer() + "' already exists"
                    );
                });
        Game game = GameMapper.map(createGame);
        return gameRepository.insert(game);
    }


    public void updateGame(@Valid UpdateGame newGame, Long id) {
        log.info("Updating game with ID " + id);
        Game oldGame = gameRepository.findById(id)
                .orElseThrow(() -> new NotFound("Game with id " + id + " not found"));
        log.info("Old game: " + oldGame);
        if (newGame.title() != null)
            oldGame.setTitle(newGame.title());
        if (newGame.developer() != null)
            oldGame.setDeveloper(newGame.developer());
        if (newGame.description() != null)
            oldGame.setDescription(newGame.description());
        if(newGame.releaseDate() != null)
            oldGame.setReleaseDate(newGame.releaseDate());
        if (newGame.upc() != null)
            oldGame.setUpc(newGame.upc());
        log.info("Updated game: " + oldGame);
        gameRepository.update(oldGame);

    }

    public List<GameResponse> findDeveloper(@Valid String developer) {
        if (developer == null || developer.trim().isEmpty()) {
            throw new BadRequest("Developer cannot be null or empty");
        }
        var games = gameRepository.findByDeveloper(developer.trim());
        if (games.isEmpty()) {
            throw new NotFound("Game with developer " + developer + " not found");
        }
        return games.stream()
                .map(GameMapper::map)
                .toList();
    }

    public List<GameResponse> findTitle(@Valid String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new BadRequest("Title cannot be null or empty");
        }
        var games = gameRepository.findByTitle(title.trim());
        if (games.isEmpty()) {
            throw new NotFound("Game with title " + title + " not found");
        }
        return games.stream()
                .map(GameMapper::map)
                .toList();
    }
}
