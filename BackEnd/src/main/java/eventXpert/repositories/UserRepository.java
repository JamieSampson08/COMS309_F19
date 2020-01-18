package eventXpert.repositories;

import eventXpert.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * This will be auto implemented by Spring into a Bean called userRepository
 * CRUD refers to Create, Read, Update, Delete
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	@Query("SELECT u FROM User u WHERE u.email = :email")
	Iterable<User> findUserByEmail(@Param("email") String email);
}
