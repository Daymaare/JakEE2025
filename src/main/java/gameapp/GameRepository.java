package gameapp;

import gameapp.entity.Game;
import jakarta.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Long> {
}
