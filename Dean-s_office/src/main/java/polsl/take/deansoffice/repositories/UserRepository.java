package polsl.take.deansoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import polsl.take.deansoffice.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
