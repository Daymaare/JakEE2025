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
import lombok.extern.java.Log;

import java.util.List;
import java.util.Objects;

@Path("games")
@Log
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
        log.info("Getting all games...");
        return repository.findAll()
                .map(GameMapper::map)
                .filter(Objects::nonNull)
                .toList();
    }

    //http://localhost:8080/api/games/1
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public GameResponse getOneGame(@PathParam("id") Long id) {
        log.info("Getting game with ID " + id + "...");
        return repository.findById(id)
                .map(GameMapper::map)
                .orElseThrow(() -> new NotFound("Game with ID " + id + " not found"));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewGame(CreateGame game) {
        if (game == null) {
            log.info("Game cannot be null");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Game cannot be null")
                    .build();
        }
        Game newGame = GameMapper.map(game);
        newGame = repository.insert(newGame);
        GameResponse response = new GameResponse(newGame);
        log.info("Created new game: " + response);
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
            log.info("Game list cannot be null or empty");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Game list cannot be null or empty")
                    .build();
        }
        List<Game> createdGames = games.stream()
                .map(GameMapper::map)
                .map(repository::insert)
                .toList();
        List<GameResponse> responses = createdGames.stream()
                .map(GameMapper::map)
                .toList();
        log.info("Created games: " + responses);
        return Response.status(Response.Status.CREATED)
                .entity(responses)
                .build();
    }


}
