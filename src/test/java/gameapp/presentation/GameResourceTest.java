package gameapp.presentation;

import gameapp.business.GameService;
import gameapp.dto.GameResponse;
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
    void getAllBooks() throws URISyntaxException, UnsupportedEncodingException {
        Mockito.when(gameService.getAllGames()).thenReturn(List.of());

        MockHttpRequest request = MockHttpRequest.get("/games");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        assertEquals(200, response.getStatus());
        assertEquals("""
                {"data":[]}""", response.getContentAsString());
    }

    @Test
    void getOneBook() throws URISyntaxException, UnsupportedEncodingException {
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
}