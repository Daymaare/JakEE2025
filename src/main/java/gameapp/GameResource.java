package gameapp;

import gameapp.dto.CreateGame;
import gameapp.dto.GameResponse;

import gameapp.entity.Game;
import gameapp.exceptions.NotFound;
import gameapp.mapper.GameMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Objects;

@Path("games")
public class GameResource {

    private GameRepository repository;

    @Inject
    public GameResource(GameRepository repository) {
        this.repository = repository;
    }

    public GameResource() {
        this.repository = null;
    }

    //http://localhost:8080/api/games
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<GameResponse> getGames() {
        return repository.findAll()
                .map(GameResponse::new)
                .filter(Objects::nonNull)
                .toList();
    }

    //http://localhost:8080/api/games/1
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public GameResponse getOneGame(@PathParam("id") Long id) {
        return repository.findById(id)
                .map(GameMapper::map)
                .orElseThrow(() -> new NotFound("Game with ID " + id + " not found"));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewGame(CreateGame game) {
        if (game == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Game cannot be null")
                    .build();
        }
        Game newGame = GameMapper.map(game);
        newGame = repository.insert(newGame);
        GameResponse response = new GameResponse(newGame);
        return Response.status(Response.Status.CREATED)
                .header("Location", "/api/games/" + newGame.getId())
                .entity(response)
                .build();
    }

    //http://localhost:8080/api/games/batch
    @POST
    @Path("batch")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewGames(List<CreateGame> games) {
        if (games == null || games.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Game list cannot be null or empty")
                    .build();
        }

        List<Game> createdGames = games.stream()
                .map(GameMapper::map)  // Mappa varje CreateGame till en Game
                .map(repository::insert) // Spara varje Game i databasen
                .toList();

        List<GameResponse> responses = createdGames.stream()
                .map(GameResponse::new)  // Mappa varje Game till en GameResponse
                .toList();

        return Response.status(Response.Status.CREATED)
                .entity(responses)
                .build();
    }


}
