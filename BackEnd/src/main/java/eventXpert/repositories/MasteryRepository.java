package eventXpert.repositories;

import eventXpert.entities.Mastery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MasteryRepository extends CrudRepository<Mastery, Integer> {
	@Query("SELECT m FROM Mastery m Where m.maxPoints >= :points AND m.pointsNeeded <= :points")
	Iterable<Mastery> findMasteryByPoints(@Param("points") int points);
}
