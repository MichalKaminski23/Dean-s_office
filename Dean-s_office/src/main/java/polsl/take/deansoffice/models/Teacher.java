package polsl.take.deansoffice.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Teachers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "UserId")
public class Teacher extends User {

	@Column(nullable = false, length = 8)
	private String Title;

	@Column(nullable = false, length = 32)
	private String Department;

	@Column(nullable = false, length = 8)
	private String Room;

	@OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TeacherSubject> teachersSubjects = new ArrayList<TeacherSubject>();

	// @OneToOne(mappedBy = "teacher", cascade = CascadeType.ALL)
	// private Subject subject;
}
