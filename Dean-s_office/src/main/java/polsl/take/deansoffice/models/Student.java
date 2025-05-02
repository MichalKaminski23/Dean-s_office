package polsl.take.deansoffice.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
	@Id
	private Integer studentId;

	@MapsId
	@OneToOne(optional = false)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(nullable = false, length = 10, unique = true)
	private String album;

	@Column(nullable = false, length = 32)
	private String field;

	@Column(nullable = false, length = 32)
	private String specialization;

	@Column(nullable = false, length = 2)
	private int semester;

	@Column(nullable = false, length = 32)
	private String degree;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = false)
	private List<Grade> grades = new ArrayList<Grade>();
}