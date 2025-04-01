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
@Table(name = "Students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "UserId")
public class Student extends User {
	@Column(nullable = false, length = 10, unique = true)
	private String Album;

	@Column(nullable = false, length = 32)
	private String Field;

	@Column(nullable = false, length = 32)
	private String Specialization;

	@Column(nullable = false, length = 2)
	private int Semester;

	@Column(nullable = false, length = 8)
	private String Degree;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Grade> grades = new ArrayList<Grade>();
}