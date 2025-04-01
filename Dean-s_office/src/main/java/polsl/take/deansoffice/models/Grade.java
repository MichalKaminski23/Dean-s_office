package polsl.take.deansoffice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Grades")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grade {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int GradeId;

	@ManyToOne
	@JoinColumn(name = "StudentId", nullable = false)
	private Student student;

	@ManyToOne
	@JoinColumn(name = "SubjectId", nullable = false)
	private Subject subject;

	@Column(nullable = false, length = 2)
	private int FinalGrade;
}