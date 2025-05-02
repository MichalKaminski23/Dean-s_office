package polsl.take.deansoffice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import polsl.take.deansoffice.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	boolean existsByEmail(String email);

	boolean existsByPhone(String phone);

	boolean existsByEmailAndUserIdNot(String email, Integer userId);

	boolean existsByPhoneAndUserIdNot(String phone, Integer userId);

	List<User> findByActive(boolean active);
}
