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
public class SubjectDto extends RepresentationModel<SubjectDto> {
	@NotNull(message = "Id can not be empty!")
	private Integer subjectId;

	@NotBlank(message = "Name can not be empty!")
	@Size(max = 32, message = "The max size of name is 32!")
	@Pattern(regexp = "^[A-Za-z0-9\s]+$", message = "Name must have only letters and numbers!")
	private String name;

	@NotNull(message = "Id can not be empty!")
	private Integer teacherId;
}