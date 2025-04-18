package polsl.take.deansoffice.models;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;

	@Column(nullable = false, length = 64)
	private String name;

	@Column(nullable = false, length = 64)
	private String surname;

	@Column(nullable = false, length = 128)
	private String email;

	@Column(nullable = false, length = 16)
	private String phone;

	@Column(nullable = false, length = 32)
	private String country;

	@Column(nullable = false, length = 16)
	private String city;

	@Column(nullable = false, length = 16)
	private String postalCode;

	@Column(nullable = false, length = 32)
	private String street;

	@Column(nullable = false, length = 32)
	private String apartNumber;

	@Column(nullable = false)
	private LocalDate startDate;

	@Column(nullable = true)
	private LocalDate endDate;

	@Column(nullable = false)
	private boolean active = true;
	
	/*
	 * @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	 * private Student student;
	 */
}