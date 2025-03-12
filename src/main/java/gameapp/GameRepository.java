package gameapp;

import gameapp.entity.Game;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Repository;

import java.util.Optional;


@Repository
public interface GameRepository extends CrudRepository<Game, Long> {

    @Find
    Optional<Game> findByTitleAndDeveloper(String title, String developer);
}
