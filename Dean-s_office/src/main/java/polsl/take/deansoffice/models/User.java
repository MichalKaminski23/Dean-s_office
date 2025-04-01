package polsl.take.deansoffice.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int UserId;

	@Column(nullable = false, length = 64)
	private String Name;

	@Column(nullable = false, length = 64)
	private String Surname;

	@Column(nullable = false, length = 128)
	private String Email;

	@Column(nullable = false, length = 16)
	private String Phone;

	@Embedded
	@Column(nullable = false)
	private Address Address;

	@Column(nullable = false)
	private LocalDate StartDate;

	@Column(nullable = true)
	private LocalDate EndDate;

	@Column(nullable = false)
	private boolean IsActive;
}