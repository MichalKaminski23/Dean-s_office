package polsl.take.deansoffice.dtos;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeDto extends RepresentationModel<GradeDto> {
	private int gradeId;
	private int studentId;
	private int subjectId;
	private int finalGrade;
}