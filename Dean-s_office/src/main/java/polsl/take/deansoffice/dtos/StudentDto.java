package polsl.take.deansoffice.dtos;

import org.springframework.hateoas.RepresentationModel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
	private String album;

	@NotBlank(message = "Field can not be empty!")
	@Size(max = 32, message = "The max size of field is 32!")
	private String field;

	@NotBlank(message = "Specialization can not be empty!")
	@Size(max = 32, message = "The max size of specialization is 32!")
	private String specialization;

	@NotBlank(message = "Semester can not be empty!")
	@Size(max = 2, message = "The max size of semester is 2!")
	private int semester;

	@NotBlank(message = "Degree can not be empty!")
	@Size(max = 8, message = "The max size of degree is 8!")
	private String degree;
}