package gameapp.presentation;

import gameapp.business.GameService;
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
        // Create your custom ExceptionMapper
        ExceptionMapper<NotFound> mapper = new NotFoundMapper();
        // Register your custom ExceptionMapper
        dispatcher.getProviderFactory().registerProviderInstance(mapper);
    }

    @Test
    void getAllBooks() throws URISyntaxException, UnsupportedEncodingException {
        Mockito.when(gameService.getAllGames()).thenReturn(List.of());

        MockHttpRequest request = MockHttpRequest.get("/games");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
        // Assert the response status code and content
        assertEquals(200, response.getStatus());
        assertEquals("""
                {"data":[]}""", response.getContentAsString());
    }
}