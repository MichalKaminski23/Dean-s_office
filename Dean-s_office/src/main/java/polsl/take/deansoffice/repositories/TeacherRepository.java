package polsl.take.deansoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import polsl.take.deansoffice.models.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

	boolean existsByUserUserId(Integer userId);
}
