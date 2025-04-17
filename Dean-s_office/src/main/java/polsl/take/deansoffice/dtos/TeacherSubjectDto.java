package polsl.take.deansoffice.dtos;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherSubjectDto extends RepresentationModel<TeacherSubjectDto>{
	private int teacherSubjectId;
	private int teacherId;
	private int subjectId;
}