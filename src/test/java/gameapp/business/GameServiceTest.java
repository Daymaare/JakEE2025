package gameapp.business;

import gameapp.dto.CreateGame;
import gameapp.dto.UpdateGame;
import gameapp.exceptions.BadRequest;
import gameapp.exceptions.NotFound;
import gameapp.mapper.GameMapper;
import gameapp.persistence.GameRepository;
import gameapp.dto.GameResponse;
import gameapp.entity.Game;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    GameRepository gameRepository;

    @InjectMocks
    GameService gameService;

    @Test
    @DisplayName("Get all games returns list of games when games exist")
    void getAllGamesReturnsListOfGamesWhenGamesExist() {
        Game game1 = new Game();
        Game game2 = new Game();
        Mockito.when(gameRepository.findAll()).thenReturn(Stream.of(game1, game2));

        List<GameResponse> games = gameService.getAllGames();

        assertEquals(2, games.size());
        verify(gameRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Get all games throws NotFoundException when no games exist")
    void getAllGamesThrowsNotFoundExceptionWhenNoGamesExist() {
        Mockito.when(gameRepository.findAll()).thenReturn(Stream.empty());

        NotFound exception = assertThrows(NotFound.class, () -> gameService.getAllGames());
        assertEquals("No games found", exception.getMessage());
    }

    @Test
    @DisplayName("Get game by ID returns GameResponse when game exists")
    void getGameByIdReturnsGameResponseWhenGameExists() {
        Game game = new Game();
        game.setId(1L);
        Mockito.when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

        GameResponse response = gameService.getGameById(1L);

        assertEquals(1L, response.id());
        verify(gameRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Create game returns created game when valid input is provided")
    void createGameReturnsCreatedGameWhenValidInput() {
        LocalDate releaseDate = LocalDate.of(2020, 1, 1);
        CreateGame createGame = new CreateGame("Title", "Developer", "Description", releaseDate, "123456789012");
        Game game = GameMapper.map(createGame);
        Mockito.when(gameRepository.insert(any(Game.class))).thenReturn(game);

        Game createdGame = gameService.createGame(createGame);

        assertAll(
                () -> assertEquals("Title", createdGame.getTitle()),
                () -> assertEquals("Developer", createdGame.getDeveloper()),
                () -> assertEquals("Description", createdGame.getDescription()),
                () -> assertEquals(releaseDate, createdGame.getReleaseDate()),
                () -> assertEquals("123456789012", createdGame.getUpc())
        );
        verify(gameRepository, Mockito.times(1)).insert(any(Game.class));
    }

    @Test
    @DisplayName("Get game by ID throws NotFoundException when game does not exist")
    void getGameByIdThrowsNotFoundExceptionWhenGameDoesNotExist() {
        Mockito.when(gameRepository.findById(1L)).thenReturn(Optional.empty());

        NotFound exception = assertThrows(NotFound.class, () -> gameService.getGameById(1L));
        assertEquals("Game with ID 1 not found", exception.getMessage());
    }

    @Test
    @DisplayName("Create game throws BadRequestException when input is null")
    void createGameThrowsBadRequestExceptionWhenInputIsNull() {
        CreateGame createGame = null;

        BadRequest exception = assertThrows(BadRequest.class, () -> gameService.createGame(createGame));
        assertEquals("Game cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Update game updates game when game exists")
    void updateGameUpdatesGameWhenGameExists() {
        UpdateGame updateGame = new UpdateGame("New Title", null, null, null, null);
        Game existingGame = new Game();
        existingGame.setId(1L);
        existingGame.setTitle("Old Title");
        Mockito.when(gameRepository.findById(1L)).thenReturn(Optional.of(existingGame));

        gameService.updateGame(updateGame, 1L);

        assertEquals("New Title", existingGame.getTitle());
        verify(gameRepository, Mockito.times(1)).update(existingGame);
    }

    @Test
    @DisplayName("Update game throws NotFoundException when game does not exist")
    void updateGameThrowsNotFoundExceptionWhenGameDoesNotExist() {
        UpdateGame updateGame = new UpdateGame("New Title", null, null, null, null);
        Mockito.when(gameRepository.findById(1L)).thenReturn(Optional.empty());

        NotFound exception = assertThrows(NotFound.class, () -> gameService.updateGame(updateGame, 1L));
        assertEquals("Game with id 1 not found", exception.getMessage());
    }

    @Test
    @DisplayName("Find developer returns games when developer exists")
    void findDeveloperReturnsGamesWhenDeveloperExists() {
        Game game = new Game();
        game.setDeveloper("Developer");
        Mockito.when(gameRepository.findByDeveloper("Developer")).thenReturn(List.of(game));

        List<GameResponse> games = gameService.findDeveloper("Developer");

        assertEquals(1, games.size());
        verify(gameRepository, Mockito.times(1)).findByDeveloper("Developer");
    }

    @Test
    @DisplayName("Find developer throws NotFoundException when developer does not exist")
    void findDeveloperThrowsNotFoundExceptionWhenDeveloperDoesNotExist() {
        Mockito.when(gameRepository.findByDeveloper("Developer")).thenReturn(List.of());

        NotFound exception = assertThrows(NotFound.class, () -> gameService.findDeveloper("Developer"));
        assertEquals("Game with developer Developer not found", exception.getMessage());
    }

    @Test
    @DisplayName("Find title returns games when title exists")
    void findTitleReturnsGamesWhenTitleExists() {
        Game game = new Game();
        game.setTitle("Title");
        Mockito.when(gameRepository.findByTitle("Title")).thenReturn(Optional.of(game));

        List<GameResponse> games = gameService.findTitle("Title");

        assertEquals(1, games.size());
        verify(gameRepository, Mockito.times(1)).findByTitle("Title");
    }

    @Test
    @DisplayName("Find title throws NotFoundException when title does not exist")
    void findTitleThrowsNotFoundExceptionWhenTitleDoesNotExist() {
        Mockito.when(gameRepository.findByTitle("Title")).thenReturn(Optional.empty());

        NotFound exception = assertThrows(NotFound.class, () -> gameService.findTitle("Title"));
        assertEquals("Game with title Title not found", exception.getMessage());
    }

    @Test
    @DisplayName("Update game with all fields in UpdateGame")
    void updateGameWithAllFieldsInUpdateGame() {
        // Arrange
        UpdateGame updateGame = new UpdateGame(
                "New Title",
                "New Developer",
                "New Description",
                LocalDate.of(2021, 1, 1),
                "123456789013"
        );
        Game existingGame = new Game();
        existingGame.setId(1L);
        existingGame.setTitle("Old Title");
        existingGame.setDeveloper("Old Developer");
        existingGame.setDescription("Old Description");
        existingGame.setReleaseDate(LocalDate.of(2020, 1, 1));
        existingGame.setUpc("123456789012");

        Mockito.when(gameRepository.findById(1L)).thenReturn(Optional.of(existingGame));

        gameService.updateGame(updateGame, 1L);

        assertAll(
                () -> assertEquals("New Title", existingGame.getTitle()),
                () -> assertEquals("New Developer", existingGame.getDeveloper()),
                () -> assertEquals("New Description", existingGame.getDescription()),
                () -> assertEquals(LocalDate.of(2021, 1, 1), existingGame.getReleaseDate()),
                () -> assertEquals("123456789013", existingGame.getUpc())
        );
        verify(gameRepository, Mockito.times(1)).update(existingGame);
    }

    @Test
    @DisplayName("FindDeveloper throws BadRequestException when developer is null")
    public void testFindDeveloperThrowsBadRequestWhenDeveloperIsNull() {
        String developer = null;
        BadRequest exception = assertThrows(BadRequest.class, () -> {
            gameService.findDeveloper(developer);
        });

        assertEquals("Developer cannot be null or empty", exception.getMessage());

        verify(gameRepository, never()).findByDeveloper(any());
    }

    @Test
    @DisplayName("Create game throws BadRequestException when game with same title and developer exists")
    void createGameThrowsBadRequestExceptionWhenGameWithSameTitleAndDeveloperExists() {
        CreateGame createGame = new CreateGame("Title", "Developer", "Description", LocalDate.of(2020, 1, 1), "123456789012");
        Game existingGame = new Game();
        existingGame.setTitle("Title");
        existingGame.setDeveloper("Developer");

        Mockito.when(gameRepository.findByTitleAndDeveloper("Title", "Developer")).thenReturn(List.of(existingGame));

        BadRequest exception = assertThrows(BadRequest.class, () -> gameService.createGame(createGame));
        assertEquals("A game with the title 'Title' and developer 'Developer' already exists", exception.getMessage());
    }
}