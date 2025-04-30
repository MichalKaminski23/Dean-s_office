package polsl.take.deansoffice.dtos;

import org.springframework.hateoas.RepresentationModel;

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
public class TeacherDto extends RepresentationModel<TeacherDto> {
	@NotNull(message = "Id can not be empty!")
	private Integer teacherId;

	@NotBlank(message = "Title can not be empty!")
	@Size(max = 10, message = "The max size of title is 10!")
	@Pattern(regexp = "^[A-Za-z.\s]+$", message = "Title must have only letters and '.'!")
	private String title;

	@NotBlank(message = "Department can not be empty!")
	@Size(max = 32, message = "The max size of department is 32!")
	@Pattern(regexp = "^[A-Za-z\s]+$", message = "Department must have only letters!")
	private String department;

	@NotBlank(message = "Room can not be empty!")
	@Size(max = 8, message = "The max size of room is 8!")
	@Pattern(regexp = "^[A-Za-z0-9\s]+$", message = "Room must have only letters and numbers!")
	private String room;
}