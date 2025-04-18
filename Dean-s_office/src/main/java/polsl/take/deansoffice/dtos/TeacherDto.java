package polsl.take.deansoffice.dtos;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDto extends RepresentationModel<TeacherDto> {
	private Integer teacherId;
	private String title;
	private String department;
	private String room;
}