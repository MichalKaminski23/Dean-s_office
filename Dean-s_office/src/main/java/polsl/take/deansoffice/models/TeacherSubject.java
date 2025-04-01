package polsl.take.deansoffice.models;

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
@Table(name = "TeachersSubjects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherSubject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int TeacherSubjectId;

	@ManyToOne
	@JoinColumn(name = "TeacherId", nullable = false)
	private Teacher teacher;

	@ManyToOne
	@JoinColumn(name = "SubjectId", nullable = false)
	private Subject subject;
}