package polsl.take.deansoffice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
	@Column(nullable = false, length = 32)
	private String Country;

	@Column(nullable = false, length = 32)
	private String City;

	@Column(nullable = false, length = 16)
	private String PostalCode;

	@Column(nullable = false, length = 32)
	private String Street;

	@Column(nullable = false, length = 8)
	private String ApartNumber;
}