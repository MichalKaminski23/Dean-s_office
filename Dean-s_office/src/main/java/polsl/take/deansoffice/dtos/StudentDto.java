package polsl.take.deansoffice.dtos;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto extends RepresentationModel<StudentDto> {
	private int userId;
	private String album;
	private String field;
	private String specialization;
	private int semester;
	private String degree;
}