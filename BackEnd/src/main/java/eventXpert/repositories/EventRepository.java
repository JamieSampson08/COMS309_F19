package eventXpert.repositories;

import eventXpert.entities.Event;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * This will be auto implemented by Spring into a Bean called userRepository
 * CRUD refers to Create, Read, Update, Delete
 */
public interface EventRepository extends CrudRepository<Event, Integer>, PagingAndSortingRepository<Event, Integer> {
	@Query("SELECT e FROM Event e WHERE e.name = :name")
	Iterable<Event> findByName(@Param("name") String name);
	
	@Query("SELECT e FROM Event e WHERE e.name = :name")
	Iterable<Event> findByNameAndSort(@Param("name") String name, Sort sort);
}
