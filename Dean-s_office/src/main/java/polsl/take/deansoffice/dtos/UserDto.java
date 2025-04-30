package polsl.take.deansoffice.dtos;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends RepresentationModel<UserDto> {
	@NotNull(message = "Id can not be empty!")
	private Integer userId;

	@NotBlank(message = "Name can not be empty!")
	@Size(max = 64, message = "The max size of name is 64!")
    @Pattern(regexp = "^[A-Za-z\s]+$", message = "Name must have only letters!")
	private String name;

	@NotBlank(message = "Surname can not be empty!")
	@Size(max = 64, message = "The max size of surname is 64!")
	@Pattern(regexp = "^[A-Za-z\s]+$", message = "Surname must have only letters!")
	private String surname;

	@NotBlank(message = "Email can not be empty!")
	@Size(max = 128, message = "The max size of email is 128!")
	@Email(message = "Incorrect format email!")
	private String email;

	@NotBlank(message = "Phone can not be empty!")
	@Size(max = 64, message = "The max size of phone is 16!")
	@Pattern(regexp = "\\+?\\d{9,15}", message = "Incorrect phone number!")
	private String phone;

	@NotBlank(message = "Country can not be empty!")
	@Size(max = 32, message = "The max size of country is 32!")
	@Pattern(regexp = "^[A-Za-z\s]+$", message = "Country must have only letters!")
	private String country;

	@NotBlank(message = "City can not be empty!")
	@Size(max = 32, message = "The max size of city is 32!")
	@Pattern(regexp = "^[A-Za-z\s]+$", message = "City must have only letters!")
	private String city;

	@NotBlank(message = "Postal code can not be empty!")
	@Size(max = 16, message = "The max size of postal code is 16!")
	@Pattern(regexp = "\\d{2}-\\d{3}")
	private String postalCode;

	@NotBlank(message = "Street can not be empty!")
	@Size(max = 32, message = "The max size of Street is 32!")
	private String street;

	@NotBlank(message = "Apart number can not be empty!")
	@Size(max = 32, message = "The max size of apart number is 32!")
	private String apartNumber;

	@PastOrPresent(message = "Start date can not be from the future!")
	private LocalDate startDate;

	@FutureOrPresent(message = "End date can not be from the past!")
	private LocalDate endDate;

	@NotNull(message = "Enter if the user is active or not!")
	private boolean active;
}