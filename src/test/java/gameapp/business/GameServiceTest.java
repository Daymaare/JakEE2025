package gameapp.business;

import gameapp.dto.CreateGame;
import gameapp.dto.UpdateGame;
import gameapp.exceptions.BadRequest;
import gameapp.exceptions.NotFound;
import gameapp.mapper.GameMapper;
import gameapp.persistence.GameRepository;
import gameapp.dto.GameResponse;
import gameapp.entity.Game;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    GameRepository gameRepository;

    @InjectMocks
    GameService gameService;

    @Test
    void getAllGames() {
        Game game1 = new Game();
        Game game2 = new Game();
        Mockito.when(gameRepository.findAll()).thenReturn(Stream.of(game1, game2));

        List<GameResponse> games = gameService.getAllGames();

        assertEquals(2, games.size());
        Mockito.verify(gameRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getAllGames_NotFound() {
        Mockito.when(gameRepository.findAll()).thenReturn(Stream.empty());

        NotFound exception = assertThrows(NotFound.class, () -> gameService.getAllGames());
        assertEquals("No games found", exception.getMessage());
    }

    @Test
    void getGameById() {
        Game game = new Game();
        game.setId(1L);
        Mockito.when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

        GameResponse response = gameService.getGameById(1L);

        assertEquals(1L, response.id());
        Mockito.verify(gameRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void createGame() {
        CreateGame createGame = new CreateGame("Title", "Developer", "Description", null, "123456789012");
        Game game = GameMapper.map(createGame);
        Mockito.when(gameRepository.insert(Mockito.any(Game.class))).thenReturn(game);

        Game createdGame = gameService.createGame(createGame);

        assertAll(
                () -> assertEquals("Title", createdGame.getTitle()),
                () -> assertEquals("Developer", createdGame.getDeveloper()),
                () -> assertEquals("Description", createdGame.getDescription()),
                () -> assertEquals("123456789012", createdGame.getUpc())
        );
        Mockito.verify(gameRepository, Mockito.times(1)).insert(Mockito.any(Game.class));
    }

    @Test
    void getGameById_NotFound() {
        Mockito.when(gameRepository.findById(1L)).thenReturn(Optional.empty());

        NotFound exception = assertThrows(NotFound.class, () -> gameService.getGameById(1L));
        assertEquals("Game with ID 1 not found", exception.getMessage());
    }

    @Test
    void createGame_BadRequest() {
        CreateGame createGame = null;

        BadRequest exception = assertThrows(BadRequest.class, () -> gameService.createGame(createGame));
        assertEquals("Game cannot be null", exception.getMessage());
    }

    @Test
    void updateGame() {
        UpdateGame updateGame = new UpdateGame("New Title", null, null, null, null);
        Game existingGame = new Game();
        existingGame.setId(1L);
        existingGame.setTitle("Old Title");
        Mockito.when(gameRepository.findById(1L)).thenReturn(Optional.of(existingGame));

        gameService.updateGame(updateGame, 1L);

        assertEquals("New Title", existingGame.getTitle());
        Mockito.verify(gameRepository, Mockito.times(1)).update(existingGame);
    }

    @Test
    void updateGame_NotFound() {
        UpdateGame updateGame = new UpdateGame("New Title", null, null, null, null);
        Mockito.when(gameRepository.findById(1L)).thenReturn(Optional.empty());

        NotFound exception = assertThrows(NotFound.class, () -> gameService.updateGame(updateGame, 1L));
        assertEquals("Game with id 1 not found", exception.getMessage());
    }

    @Test
    void findDeveloper() {
        Game game = new Game();
        game.setDeveloper("Developer");
        Mockito.when(gameRepository.findByDeveloper("Developer")).thenReturn(List.of(game));

        List<GameResponse> games = gameService.findDeveloper("Developer");

        assertEquals(1, games.size());
        Mockito.verify(gameRepository, Mockito.times(1)).findByDeveloper("Developer");
    }

    @Test
    void findDeveloper_NotFound() {
        Mockito.when(gameRepository.findByDeveloper("Developer")).thenReturn(List.of());

        NotFound exception = assertThrows(NotFound.class, () -> gameService.findDeveloper("Developer"));
        assertEquals("Game with developer Developer not found", exception.getMessage());
    }

    @Test
    void findTitle() {
        Game game = new Game();
        game.setTitle("Title");
        Mockito.when(gameRepository.findByTitle("Title")).thenReturn(Optional.of(game));

        List<GameResponse> games = gameService.findTitle("Title");

        assertEquals(1, games.size());
        Mockito.verify(gameRepository, Mockito.times(1)).findByTitle("Title");
    }

    @Test
    void findTitle_NotFound() {
        Mockito.when(gameRepository.findByTitle("Title")).thenReturn(Optional.empty());

        NotFound exception = assertThrows(NotFound.class, () -> gameService.findTitle("Title"));
        assertEquals("Game with title Title not found", exception.getMessage());
    }
}
