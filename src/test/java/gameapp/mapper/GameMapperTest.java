package gameapp.mapper;

import gameapp.dto.CreateGame;
import gameapp.dto.GameResponse;
import gameapp.entity.Game;
import gameapp.exceptions.BadRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameMapperTest {

    @Test
    @DisplayName("Map CreateGame to Game successfully")
    void mapCreateGameToGameSuccessfully() {
        CreateGame createGame = new CreateGame(
                "Test Title",
                "Test Developer",
                "Test Description",
                LocalDate.of(2020, 1, 1),
                "123456789012"
        );

        Game game = GameMapper.map(createGame);

        assertThat(game).isNotNull();
        assertThat(game.getTitle()).isEqualTo("Test Title");
        assertThat(game.getDeveloper()).isEqualTo("Test Developer");
        assertThat(game.getDescription()).isEqualTo("Test Description");
        assertThat(game.getReleaseDate()).isEqualTo(LocalDate.of(2020, 1, 1));
        assertThat(game.getUpc()).isEqualTo("123456789012");
    }

    @Test
    @DisplayName("Return null when mapping null CreateGame")
    void mapNullCreateGameReturnsNull() {
        Game game = GameMapper.map((CreateGame) null);
        assertThat(game).isNull();
    }

    @Test
    @DisplayName("Return null when mapping null Game")
    void mapWithNullGameReturnsNull() {
        Game game = null;
        assertThat(GameMapper.map(game)).isEqualTo(null);
    }

    @Test
    @DisplayName("Map valid Game to GameResponse")
    void mapValidGameToGameResponse() {
        Game game = new Game();
        game.setId(1L);
        game.setTitle("Test Title");
        game.setDeveloper("Test Developer");
        game.setDescription("Test Description");
        game.setReleaseDate(LocalDate.of(2020, 1, 1));
        game.setUpc("123456789012");

        GameResponse response = GameMapper.map(game);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.title()).isEqualTo("Test Title");
        assertThat(response.developer()).isEqualTo("Test Developer");
        assertThat(response.description()).isEqualTo("Test Description");
        assertThat(response.releaseDate()).isEqualTo(LocalDate.of(2020, 1, 1));
        assertThat(response.upc()).isEqualTo("123456789012");
    }

}