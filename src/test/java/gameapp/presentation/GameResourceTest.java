package gameapp.presentation;

import gameapp.business.GameService;
import gameapp.dto.CreateGame;
import gameapp.dto.GameResponse;
import gameapp.dto.UpdateGame;
import gameapp.entity.Game;
import gameapp.exceptions.NotFound;
import gameapp.exceptions.mapper.NotFoundMapper;
import jakarta.ws.rs.ext.ExceptionMapper;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.spi.Dispatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GameResourceTest {

    @Mock
    GameService gameService;


    Dispatcher dispatcher;

    @BeforeEach
    public void setup() {
        dispatcher = MockDispatcherFactory.createDispatcher();
        GameResource gameResource = new GameResource(gameService);
        dispatcher.getRegistry().addSingletonResource(gameResource);

        ExceptionMapper<NotFound> mapper = new NotFoundMapper();

        dispatcher.getProviderFactory().registerProviderInstance(mapper);
    }

    @Test
    void getAllGamesReturnsEmptyListWhenNoGamesExist() throws URISyntaxException, UnsupportedEncodingException {
        Mockito.when(gameService.getAllGames()).thenReturn(List.of());

        MockHttpRequest request = MockHttpRequest.get("/games");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        assertEquals(200, response.getStatus());
        assertEquals("""
                {"data":[]}""", response.getContentAsString());
    }

    @Test
    void getGameByIdReturnsGameResponseWhenGameExists() throws URISyntaxException, UnsupportedEncodingException {
        GameResponse testGameResponse = new GameResponse(1L, "Test Title", "Test Developer", "Test Description", null, "123456789012");
        Mockito.when(gameService.getGameById(1L)).thenReturn(testGameResponse);
        MockHttpRequest request = MockHttpRequest.get("/games/1");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        assertEquals(200, response.getStatus());
        assertEquals("""
                        {"id":1,"title":"Test Title","developer":"Test Developer","description":"Test Description","releaseDate":null,"upc":"123456789012"}""",
                response.getContentAsString());
    }


    @Test
    void createGameReturnsCreatedGameWhenValidInput() throws URISyntaxException, UnsupportedEncodingException {
        CreateGame testCreateGame = new CreateGame("Test Title", "Test Developer", "Test Description", null, "123456789012");
        Game testGame = new Game();
        testGame.setId(1L);
        testGame.setTitle("Test Title");
        testGame.setDeveloper("Test Developer");
        testGame.setDescription("Test Description");
        testGame.setReleaseDate(null);
        testGame.setUpc("123456789012");

        Mockito.when(gameService.createGame(testCreateGame)).thenReturn(testGame);

        String jsonBody = """
                    {
                        "title": "Test Title",
                        "developer": "Test Developer",
                        "description": "Test Description",
                        "releaseDate": null,
                        "upc": "123456789012"
                    }
                """;

        MockHttpRequest request = MockHttpRequest.post("/games")
                .contentType("application/json")
                .content(jsonBody.getBytes());
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        assertEquals(201, response.getStatus());
        assertEquals("""
                        {"id":1,"title":"Test Title","developer":"Test Developer","description":"Test Description","releaseDate":null,"upc":"123456789012"}""",
                response.getContentAsString());
    }

    @Test
    void updateGameUpdatesGameSuccessfullyWhenValidInput() throws URISyntaxException, UnsupportedEncodingException {
        UpdateGame updateGame = new UpdateGame("Updated Title", "Updated Developer", "Updated Description", null, "987654321098");
        Long gameId = 1L;

        Mockito.doNothing().when(gameService).updateGame(updateGame, gameId);

        String jsonBody = """
                    {
                        "title": "Updated Title",
                        "developer": "Updated Developer",
                        "description": "Updated Description",
                        "releaseDate": null,
                        "upc": "987654321098"
                    }
                """;

        MockHttpRequest request = MockHttpRequest.patch("/games/" + gameId)
                .contentType("application/json")
                .content(jsonBody.getBytes());
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        assertEquals(204, response.getStatus());
        Mockito.verify(gameService, Mockito.times(1)).updateGame(updateGame, gameId);
    }

    @Test
    void findGamesByDeveloperReturnsGamesListWhenDeveloperExists() throws URISyntaxException, UnsupportedEncodingException {
        String developer = "Developer";
        List<GameResponse> expectedGames = List.of(
                new GameResponse(1L, "Game 1", developer, "Description1", null, "123456789012"),
                new GameResponse(2L, "Game 2", developer, "Description2", null, "987654321098")
        );

        Mockito.when(gameService.findDeveloper(developer)).thenReturn(expectedGames);

        String encodedDeveloper = URLEncoder.encode(developer, StandardCharsets.UTF_8.toString());

        MockHttpRequest request = MockHttpRequest.get("/games/developer/" + encodedDeveloper);
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        assertEquals(200, response.getStatus());
        assertEquals("""
                        {"data":[{"id":1,"title":"Game 1","developer":"Developer","description":"Description1","releaseDate":null,"upc":"123456789012"},{"id":2,"title":"Game 2","developer":"Developer","description":"Description2","releaseDate":null,"upc":"987654321098"}]}""",
                response.getContentAsString());
        Mockito.verify(gameService, Mockito.times(1)).findDeveloper(developer);
    }

    @Test
    void findGamesByTitleReturnsGamesListWhenTitleExists() throws URISyntaxException, UnsupportedEncodingException {
        String title = "GameTitle";
        List<GameResponse> expectedGames = List.of(
                new GameResponse(1L, title, "Developer 1", "Description 1", null, "123456789012"),
                new GameResponse(2L, title, "Developer 2", "Description 2", null, "987654321098")
        );

        Mockito.when(gameService.findTitle(title)).thenReturn(expectedGames);

        String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8.toString());

        MockHttpRequest request = MockHttpRequest.get("/games/title/" + encodedTitle);
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        assertEquals(200, response.getStatus());
        assertEquals("""
                        {"data":[{"id":1,"title":"GameTitle","developer":"Developer 1","description":"Description 1","releaseDate":null,"upc":"123456789012"},{"id":2,"title":"GameTitle","developer":"Developer 2","description":"Description 2","releaseDate":null,"upc":"987654321098"}]}""",
                response.getContentAsString());
        Mockito.verify(gameService, Mockito.times(1)).findTitle(title);
    }

}