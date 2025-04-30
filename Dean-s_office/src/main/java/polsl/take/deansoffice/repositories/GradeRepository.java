package polsl.take.deansoffice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import polsl.take.deansoffice.models.Grade;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {
	
	List<Grade> findByStudentStudentId(Integer studentId);
}
