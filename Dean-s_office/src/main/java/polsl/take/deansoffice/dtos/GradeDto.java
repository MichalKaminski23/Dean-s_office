package polsl.take.deansoffice.dtos;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeDto extends RepresentationModel<GradeDto> {
	private Integer gradeId;
	private Integer studentId;
	private Integer subjectId;
	private int finalGrade;
}