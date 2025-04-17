package polsl.take.deansoffice.dtos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDto extends RepresentationModel<SubjectDto>{
	private int subjectId;
	private String name;
	private int teacheriD;
	//private List<TeacherSubjectDto> teachersSubjects = new ArrayList<TeacherSubjectDto>();
	//private List<GradeDto> grades = new ArrayList<GradeDto>();
}