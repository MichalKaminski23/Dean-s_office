package polsl.take.deansoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import polsl.take.deansoffice.models.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {

	boolean existsByName(String name);
	
	boolean existsByTeacherTeacherId(Integer teacherId);
}
