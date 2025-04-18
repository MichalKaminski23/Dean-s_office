package polsl.take.deansoffice.dtos;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends RepresentationModel<UserDto> {
	private int userId;
	private String name;
	private String surname;
	private String email;
	private String phone;
	private String country;
	private String city;
	private String postalCode;
	private String street;
	private String apartNumber;
	private LocalDate startDate;
	private LocalDate endDate;
	private boolean active;
}