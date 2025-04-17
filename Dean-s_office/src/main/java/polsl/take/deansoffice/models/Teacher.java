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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Teachers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "UserId")
@EqualsAndHashCode(callSuper = true)
public class Teacher extends User {
	@Column(nullable = false, length = 8)
	private String title;

	@Column(nullable = false, length = 32)
	private String department;

	@Column(nullable = false, length = 8)
	private String room;

	@OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TeacherSubject> teachersSubjects = new ArrayList<TeacherSubject>();
}