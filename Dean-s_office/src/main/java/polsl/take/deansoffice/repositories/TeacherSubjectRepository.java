package polsl.take.deansoffice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import polsl.take.deansoffice.models.TeacherSubject;

@Repository
public interface TeacherSubjectRepository extends JpaRepository<TeacherSubject, Integer> {

	List<TeacherSubject> findByTeacherTeacherId(Integer teacherId);
	
    boolean existsByTeacherTeacherIdAndSubjectSubjectId(Integer teacherId, Integer subjectId);

}
