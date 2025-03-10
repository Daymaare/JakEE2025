package gameapp;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class GameService {

    private GameRepository gameRepository;

    //@Inject
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public GameService() {

    }
}
