package gameapp;

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

import static gameapp.mapper.GameMapper.map;

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
        log.info("Getting all games...");
        return gameRepository.findAll()
                .filter(Objects::nonNull)
                .map(GameMapper::map)
                .toList();
    }

    public GameResponse getGameById(@PathParam("id") Long id) {
        log.info("Getting game with ID " + id);
        return gameRepository.findById(id)
                .map(GameMapper::map)
                .orElseThrow(
                        () -> new NotFound("Game with ID " + id + " not found"));
    }

    public Game createGame(@Valid CreateGame createGame) {
        if (createGame == null) {
            log.warning("Game cannot be null");
            throw new IllegalArgumentException("Game cannot be null");
        }
        log.info("Creating game: " + createGame);
        gameRepository.findByTitleAndDeveloper(createGame.title(), createGame.developer())
                .ifPresent(existingGame -> {
                    throw new IllegalStateException(
                            "A game with the title '" + createGame.title() + "' and developer '"
                                    + createGame.developer() + "' already exists"
                    );
                });
    Game game = GameMapper.map(createGame);
    return gameRepository.insert(game);
    }

    public List<GameResponse> createGames(@Valid List<CreateGame> createGames) {
        if (createGames == null || createGames.isEmpty()) {
            throw new IllegalArgumentException("Game list cannot be null or empty");
        }
        return createGames.stream()
                .map(GameMapper::map)
                .map(gameRepository::insert)
                .map(GameMapper::map)
                .toList();
    }

    public GameResponse updateGame(@Valid UpdateGame updateGame, Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new NotFound("Game with id " + id + " not found"));

        game.setTitle(updateGame.title());
        game.setDeveloper(updateGame.developer());
        game.setDescription(updateGame.description());
        game.setReleaseDate(updateGame.releaseDate());
        game.setUpc(updateGame.upc());
        return map(gameRepository.insert(game));

    }
}
