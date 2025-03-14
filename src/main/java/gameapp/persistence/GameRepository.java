package gameapp.persistence;

import gameapp.entity.Game;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface GameRepository extends CrudRepository<Game, Long> {

    @Find
    List<Game> findByTitleAndDeveloper(String title, String developer);

    @Find
    List<Game> findByDeveloper(String developer);

    @Find
    Optional<Game> findByTitle(String title);
}
