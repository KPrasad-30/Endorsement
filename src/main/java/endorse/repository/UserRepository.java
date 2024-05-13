package endorse.repository;

import endorse.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// UserRepository
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}