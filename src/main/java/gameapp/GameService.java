package gameapp;

import gameapp.dto.GameResponse;
import gameapp.exceptions.NotFound;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class GameService {

    private GameRepository gameRepository;

    @Inject
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public GameService() {
    }

    public List<GameResponse> getAllGames(){
        return gameRepository.findAll()
                .map(GameResponse::new)
                .filter(Objects::nonNull)
                .toList();
    }

    public GameResponse getGameById(Long id){
        return gameRepository.findById(id)
                .map(GameResponse::new)
                .orElseThrow(
                        () -> new NotFound("Game with ID " + id + " not found"));
    }
}
