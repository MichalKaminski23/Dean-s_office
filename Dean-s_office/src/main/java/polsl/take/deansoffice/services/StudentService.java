package polsl.take.deansoffice.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import polsl.take.deansoffice.controllers.GradeController;
import polsl.take.deansoffice.controllers.StudentController;
import polsl.take.deansoffice.controllers.UserController;
import polsl.take.deansoffice.dtos.StudentDto;
import polsl.take.deansoffice.exceptions.ResourceConflictException;
import polsl.take.deansoffice.exceptions.ResourceNotFoundException;
import polsl.take.deansoffice.models.Student;
import polsl.take.deansoffice.models.User;
import polsl.take.deansoffice.repositories.StudentRepository;
import polsl.take.deansoffice.repositories.TeacherRepository;
import polsl.take.deansoffice.repositories.UserRepository;

@Service
public class StudentService {
	private final StudentRepository studentRepository;
	private final UserRepository userRepository;
	private final TeacherRepository teacherRepository;

	public StudentService(StudentRepository studentRepository, UserRepository userRepository,
			TeacherRepository teacherRepository) {
		this.studentRepository = studentRepository;
		this.userRepository = userRepository;
		this.teacherRepository = teacherRepository;
	}

	public CollectionModel<EntityModel<StudentDto>> getAllStudents() {
		List<EntityModel<StudentDto>> students = studentRepository.findAll().stream().map(this::toDto)
				.collect(Collectors.toList());

		/*
		 * if (students.size() == 0) { throw new
		 * ResourceNotFoundException("There are not any students yet."); }
		 */

		return CollectionModel.of(students, linkTo(methodOn(StudentController.class).getAllStudents()).withSelfRel());
	}

	public EntityModel<StudentDto> getStudentById(Integer id) {
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " not found"));
		return toDto(student);
	}

	@Transactional
	public EntityModel<StudentDto> createStudent(Integer id, StudentDto studentDto) {
		if (studentRepository.existsByAlbum(studentDto.getAlbum())) {
			throw new ResourceConflictException("Album already exists: " + studentDto.getAlbum());
		}

		if (studentRepository.existsByUserUserId(id)) {
			throw new ResourceConflictException("Student for user with id " + id + " already exists");
		}

		if (teacherRepository.existsById(id)) {
			throw new ResourceConflictException("This user with id " + id + " is a teacher");
		}

		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " not found"));

		if (user.isActive() == false) {
			throw new ResourceConflictException("User with id " + id + " is not active");
		}

		Student student = new Student();
		toEntity(student, studentDto);
		student.setUser(user);
		Student savedStudent = studentRepository.save(student);
		return toDto(savedStudent);
	}

	@Transactional
	public EntityModel<StudentDto> updateStudent(Integer id, StudentDto studentDto) {
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " not found"));

		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));

		student.setUser(user);

		if (user.isActive() == false) {
			throw new ResourceConflictException("User with id " + id + " is not active");
		}

		if (!student.getAlbum().equals(studentDto.getAlbum())
				&& studentRepository.existsByAlbumAndStudentIdNot(studentDto.getAlbum(), studentDto.getStudentId())) {
			throw new ResourceConflictException("Album already exists: " + studentDto.getAlbum());
		}

		toEntity(student, studentDto);
		Student updatedStudent = studentRepository.save(student);
		return toDto(updatedStudent);
	}

	private EntityModel<StudentDto> toDto(Student student) {
		StudentDto studentDto = new StudentDto();
		studentDto.setStudentId(student.getStudentId());
		studentDto.setAlbum(student.getAlbum());
		studentDto.setField(student.getField());
		studentDto.setSpecialization(student.getSpecialization());
		studentDto.setSemester(student.getSemester());
		studentDto.setDegree(student.getDegree());

		return EntityModel.of(studentDto,
				linkTo(methodOn(StudentController.class).getStudentById(student.getStudentId())).withSelfRel(),
				linkTo(methodOn(UserController.class).getUserById(student.getStudentId())).withRel("user"),
				linkTo(methodOn(GradeController.class).getAllGradesForStudent(student.getStudentId()))
						.withRel("grades"));

	}

	private Student toEntity(Student student, StudentDto studentDto) {
		student.setAlbum(studentDto.getAlbum());
		student.setField(studentDto.getField());
		student.setSpecialization(studentDto.getSpecialization());
		student.setSemester(studentDto.getSemester());
		student.setDegree(studentDto.getDegree());
		return student;
	}

}
