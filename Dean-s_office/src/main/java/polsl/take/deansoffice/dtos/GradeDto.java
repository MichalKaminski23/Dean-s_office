package polsl.take.deansoffice.dtos;

import org.springframework.hateoas.RepresentationModel;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeDto extends RepresentationModel<GradeDto> {
	@NotNull(message = "Grade id can not be empty!")
	private Integer gradeId;

	@NotNull(message = "Student id can not be empty!")
	private Integer studentId;

	@NotNull(message = "Subject id can not be empty!")
	private Integer subjectId;

	@NotNull(message = "Final grade can not be empty!")
	@Min(value = 1, message = "Final grade must be at least 2 (1 is for subject condition!)")
	@Max(value = 5, message = "Final grade must be less than or equal to 5!")
	private float finalGrade;
}