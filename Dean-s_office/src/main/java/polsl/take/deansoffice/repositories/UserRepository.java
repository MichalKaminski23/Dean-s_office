package polsl.take.deansoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import polsl.take.deansoffice.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
