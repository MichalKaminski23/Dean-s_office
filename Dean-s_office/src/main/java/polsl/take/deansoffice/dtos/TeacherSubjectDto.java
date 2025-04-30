package polsl.take.deansoffice.dtos;

import org.springframework.hateoas.RepresentationModel;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherSubjectDto extends RepresentationModel<TeacherSubjectDto> {
	@NotNull(message = "Id can not be empty!")
	private Integer teacherSubjectId;

	@NotNull(message = "Teacher id can not be empty!")
	private Integer teacherId;

	@NotNull(message = "Subject id can not be empty!")
	private Integer subjectId;
}