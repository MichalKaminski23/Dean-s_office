package polsl.take.deansoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import polsl.take.deansoffice.models.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

	boolean existsByUserUserId(Integer userId);

	boolean existsByAlbum(String album);

	boolean existsByAlbumAndStudentIdNot(String album, Integer studentId);
}
