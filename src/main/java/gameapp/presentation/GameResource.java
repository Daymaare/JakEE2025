package gameapp.presentation;

import gameapp.business.GameService;
import gameapp.dto.CreateGame;
import gameapp.dto.GameResponse;

import gameapp.dto.ResponseDto;
import gameapp.dto.UpdateGame;
import gameapp.entity.Game;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.inject.Inject;
import lombok.extern.java.Log;

import java.util.List;

@Path("games")
@Log
public class GameResource {

    private GameService gameService;

    @Inject
    public GameResource(GameService gameService) {
        this.gameService = gameService;
    }

    public GameResource() {
        this.gameService = null;
    }

    //http://localhost:8080/api/games
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseDto getAllGames() {
        log.info("Getting all games...");
        return new ResponseDto(gameService.getAllGames());
    }

    //http://localhost:8080/api/games/{{id}}
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public GameResponse getOneGame(@PathParam("id") Long id) {
        log.info("Getting game with ID " + id + "...");
        return gameService.getGameById(id);
    }

    //http://localhost:8080/api/games
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewGame(@Valid CreateGame createGame) {
        if (createGame == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Game data cannot be null")
                    .build();
        }
        Game newGame = gameService.createGame(createGame);
        log.info("Created game: " + newGame);
        return Response.status(Response.Status.CREATED)
                .header("Location", "/api/games/" + newGame.getId())
                .entity(newGame)
                .build();
    }

    //http://localhost:8080/api/games/batch
    @POST
    @Path("batch")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewGames(@Valid List<CreateGame> createGames) {
        if (createGames == null || createGames.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Game list cannot be null or empty")
                    .build();
        }
        List<GameResponse> gameResponses = gameService.createGames(createGames);
        return Response.status(Response.Status.CREATED)
                .entity(gameResponses)
                .build();
    }

    //http:localhost:8080/api/games/{{id}}
    @PATCH
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public GameResponse updateGame(@PathParam("id") Long id, @Valid UpdateGame updateGame) {
        return gameService.updateGame(updateGame, id);
    }


    //http:localhost:8080/api/games/developer/{{developer}}
    @GET
    @Path("developer/{developer}")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseDto findDeveloper(@PathParam("developer") String developer) {
        return new ResponseDto(gameService.findDeveloper(developer));
    }

}
