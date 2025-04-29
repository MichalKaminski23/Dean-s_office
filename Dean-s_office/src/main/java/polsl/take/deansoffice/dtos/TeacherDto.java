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
public class TeacherDto extends RepresentationModel<TeacherDto> {
	@NotNull(message = "Id can not be empty!")
	private Integer teacherId;

	@NotBlank(message = "Title can not be empty!")
	@Size(max = 10, message = "The max size of title is 8!")
	private String title;

	@NotBlank(message = "Department can not be empty!")
	@Size(max = 32, message = "The max size of department is 32!")
	private String department;

	@NotBlank(message = "Room can not be empty!")
	@Size(max = 8, message = "The max size of room is 8!")
	private String room;
}