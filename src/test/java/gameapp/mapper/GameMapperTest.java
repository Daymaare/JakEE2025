package gameapp.mapper;

import gameapp.dto.CreateGame;
import gameapp.dto.GameResponse;
import gameapp.entity.Game;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class GameMapperTest {

    @Test
    void mapCreateGameToGame() {
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
    void mapWithNullGameReturnsNull() {
        Game game = null;
        assertThat(GameMapper.map(game)).isEqualTo(null);
    }

    @Test
    void mapWithEmptyStrings() {
        CreateGame createGame = new CreateGame(
                "",
                "",
                "",
                LocalDate.of(2020, 1, 1),
                "123456789012"
        );

        Game game = GameMapper.map(createGame);

        assertThat(game).isNotNull();
        assertThat(game.getTitle()).isEqualTo("");
        assertThat(game.getDeveloper()).isEqualTo("");
        assertThat(game.getDescription()).isEqualTo("");
    }

    @Test
    void mapWithMaxLengthDescription() {
        String maxLengthDescription = "a".repeat(500);
        CreateGame createGame = new CreateGame(
                "Test Title",
                "Test Developer",
                maxLengthDescription,
                LocalDate.of(2020, 1, 1),
                "123456789012"
        );

        Game game = GameMapper.map(createGame);

        assertThat(game).isNotNull();
        assertThat(game.getDescription()).isEqualTo(maxLengthDescription);
    }

    @Test
    void mapWithInvalidUPCFormat() {
        CreateGame createGame = new CreateGame(
                "Test Title",
                "Test Developer",
                "Test Description",
                LocalDate.of(2020, 1, 1),
                "INVALIDUPC"
        );

        Game game = GameMapper.map(createGame);

        assertThat(game).isNotNull();
        assertThat(game.getUpc()).isEqualTo("INVALIDUPC");
    }

    @Test
    void mapWithFutureReleaseDate() {
        CreateGame createGame = new CreateGame(
                "Test Title",
                "Test Developer",
                "Test Description",
                LocalDate.of(2100, 1, 1),
                "123456789012"
        );

        Game game = GameMapper.map(createGame);

        assertThat(game).isNotNull();
        assertThat(game.getReleaseDate()).isEqualTo(LocalDate.of(2100, 1, 1));
    }
}



