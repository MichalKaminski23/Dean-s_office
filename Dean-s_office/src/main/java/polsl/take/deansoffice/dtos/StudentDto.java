package polsl.take.deansoffice.dtos;

import org.springframework.hateoas.RepresentationModel;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto extends RepresentationModel<StudentDto> {
	@NotNull(message = "Id can not be empty!")
	private Integer studentId;

	@NotBlank(message = "Album can not be empty!")
	@Size(max = 10, message = "The max size of album is 10!")
    @Pattern(regexp = "^[A-Za-z]{2}\\d{6}$", message = "Album must start with two letters followed by six digits")
	private String album;

	@NotBlank(message = "Field can not be empty!")
	@Size(max = 32, message = "The max size of field is 32!")
	@Pattern(regexp = "^[A-Za-z\s]+$", message = "Field must have only letters!")
	private String field;

	@NotBlank(message = "Specialization can not be empty!")
	@Size(max = 32, message = "The max size of specialization is 32!")
	@Pattern(regexp = "^[A-Za-z\s]+$", message = "Field must have only letters!")
	private String specialization;

	@NotBlank(message = "Semester can not be empty!")
	@Size(max = 2, message = "The max size of semester is 2!")
    @Min(value = 1, message = "Semester must be at least 1!")
    @Max(value = 10, message = "Semester must be less than or equal to 10!")
	private int semester;

	@NotBlank(message = "Degree can not be empty!")
	@Size(max = 32, message = "The max size of degree is 32!")
	@Pattern(regexp = "^[A-Za-z.\s]+$", message = "Degree must have only letters and '.'!")
	private String degree;
}