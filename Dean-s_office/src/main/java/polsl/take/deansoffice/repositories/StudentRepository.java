package polsl.take.deansoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import polsl.take.deansoffice.models.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
