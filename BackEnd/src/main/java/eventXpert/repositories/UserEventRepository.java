package eventXpert.repositories;

import eventXpert.entities.UserEvent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * This will be auto implemented by Spring into a Bean called UserEventRepository
 * CRUD refers to Create, Read, Update, Delete
 */
public interface UserEventRepository extends CrudRepository<UserEvent, Integer> {
	/**
	 * Finds a userEvent relationship based on the user id and event id
	 *
	 * @param userId  id of user
	 * @param eventId id of event
	 * @return int userEvent id
	 */
	@Query("SELECT ue.userEventId FROM UserEvent ue WHERE ue.eventId = :eventId AND ue.userId = :userId")
	Integer findIdByUserAndEvent(@Param("userId") Integer userId, @Param("eventId") Integer eventId);
	
	/**
	 * Finds all event ids of one user
	 *
	 * @param userId id of user
	 * @return list of event ids associated with user
	 */
	@Query("SELECT ev.eventId FROM UserEvent ev WHERE ev.userId = :userId")
	Iterable<Integer> findEventsByUserId(@Param("userId") Integer userId);
	
	/**
	 * Finds all user ids of one event
	 *
	 * @param eventId id of event
	 * @return list of user ids associated with event
	 */
	@Query("SELECT ue.userId FROM UserEvent ue WHERE ue.eventId = :eventId")
	Iterable<Integer> findUsersByEventId(@Param("eventId") Integer eventId);
}

