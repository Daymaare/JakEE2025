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
        return new ResponseDto(gameService.getAllGames());
    }

    //http://localhost:8080/api/games/{{id}}
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public GameResponse getOneGame(@PathParam("id") Long id) {
        return gameService.getGameById(id);
    }

    //http://localhost:8080/api/games
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewGame(@Valid CreateGame createGame) {
        Game newGame = gameService.createGame(createGame);
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
        List<GameResponse> gameResponses = gameService.createGames(createGames);
        return Response.status(Response.Status.CREATED)
                .entity(gameResponses)
                .build();
    }

    //http:localhost:8080/api/games/{{id}}
    @PATCH
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateGame(@Valid UpdateGame game, @PathParam("id") Long id) {
        gameService.updateGame(game, id);
        return Response.noContent().build();
    }


    //http:localhost:8080/api/games/developer/{{developer}}
    @GET
    @Path("/developer/{developer}")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseDto findDeveloper(@PathParam("developer") String developer) {
        return new ResponseDto(gameService.findDeveloper(developer));
    }

    //http://localhost:8080/api/games/title/{{title}}
    @GET
    @Path("/title/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseDto findTitle(@PathParam("title") String title) {
        return new ResponseDto(gameService.findTitle(title));

    }
}
