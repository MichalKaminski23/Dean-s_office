package polsl.take.deansoffice.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import polsl.take.deansoffice.controllers.StudentController;
import polsl.take.deansoffice.controllers.UserController;
import polsl.take.deansoffice.dtos.StudentDto;
import polsl.take.deansoffice.models.Student;
import polsl.take.deansoffice.repositories.StudentRepository;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class StudentService {

	private final StudentRepository studentRepository;

	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	public CollectionModel<EntityModel<StudentDto>> getAllStudents() {
		List<EntityModel<StudentDto>> students = studentRepository.findAll().stream().map(this::toDto)
				.collect(Collectors.toList());

		return CollectionModel.of(students, linkTo(methodOn(StudentController.class).getAllStudents()).withSelfRel());
	}

	public EntityModel<StudentDto> getStudentById(Integer id) {
		Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
		return toDto(student);
	}

	private EntityModel<StudentDto> toDto(Student student) {
		StudentDto dto = new StudentDto();
		dto.setUserId(student.getUserId());
		dto.setAlbum(student.getAlbum());
		dto.setField(student.getField());
		dto.setSpecialization(student.getSpecialization());
		dto.setSemester(student.getSemester());
		dto.setDegree(student.getDegree());

		return EntityModel.of(dto,
				linkTo(methodOn(StudentController.class).getStudentById(student.getUserId())).withSelfRel(),
				linkTo(methodOn(UserController.class).getUserById(student.getUserId())).withRel("user"));
	}
}
