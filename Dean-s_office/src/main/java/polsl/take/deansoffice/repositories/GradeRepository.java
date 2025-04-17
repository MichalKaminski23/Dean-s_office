package polsl.take.deansoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import polsl.take.deansoffice.models.Grade;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {
}
