package polsl.take.deansoffice.dtos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDto extends RepresentationModel<TeacherDto> {
	private String title;
	private String department;
	private String room;
	//private List<TeacherSubjectDto> teachersSubjects = new ArrayList<TeacherSubjectDto>();
}