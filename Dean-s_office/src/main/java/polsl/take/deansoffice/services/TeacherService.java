package polsl.take.deansoffice.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import polsl.take.deansoffice.controllers.TeacherController;
import polsl.take.deansoffice.controllers.UserController;
import polsl.take.deansoffice.dtos.TeacherDto;
import polsl.take.deansoffice.models.Teacher;
import polsl.take.deansoffice.models.User;
import polsl.take.deansoffice.repositories.TeacherRepository;
import polsl.take.deansoffice.repositories.UserRepository;

@Service
public class TeacherService {

	private final TeacherRepository teacherRepository;
	private final UserRepository userRepository;

	public TeacherService(TeacherRepository teacherRepository, UserRepository userRepository) {
		this.teacherRepository = teacherRepository;
		this.userRepository = userRepository;
	}

	public CollectionModel<EntityModel<TeacherDto>> getAllTeachers() {
		List<EntityModel<TeacherDto>> teachers = teacherRepository.findAll().stream().map(this::toDto)
				.collect(Collectors.toList());

		return CollectionModel.of(teachers, linkTo(methodOn(TeacherController.class).getAllTeachers()).withSelfRel());

	}

	public EntityModel<TeacherDto> getTeacherById(Integer id) {
		Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new RuntimeException("Teacher not found"));
		return toDto(teacher);
	}

	public EntityModel<TeacherDto> createTeacher(Integer id, TeacherDto teacherDto) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
		Teacher teacher = toEntity(teacherDto);
		teacher.setUser(user);
		Teacher savedTeacher = teacherRepository.save(teacher);
		return toDto(savedTeacher);
	}

	public EntityModel<TeacherDto> updateTeacher(Integer id, Map<String, Object> updates) {
		Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));

		updates.forEach((field, value) -> {
			switch (field) {
			case "title":
				teacher.setTitle((String) value);
				break;
			case "department":
				teacher.setDepartment((String) value);
				break;
			case "room":
				teacher.setRoom((String) value);
				break;
			default:
				throw new IllegalArgumentException("Unknown field: " + field);
			}
		});

		Teacher updatedTeacher = teacherRepository.save(teacher);
		return toDto(updatedTeacher);
	}

	private EntityModel<TeacherDto> toDto(Teacher teacher) {
		TeacherDto teacherDto = new TeacherDto();
		teacherDto.setTeacherId(teacher.getTeacherId());
		teacherDto.setTitle(teacher.getTitle());
		teacherDto.setDepartment(teacher.getDepartment());
		teacherDto.setRoom(teacher.getRoom());

		return EntityModel.of(teacherDto,
				linkTo(methodOn(TeacherController.class).getTeacherById(teacher.getTeacherId())).withSelfRel(),
				linkTo(methodOn(UserController.class).getUserById(teacher.getTeacherId())).withRel("user"));
		// subjects!!!
	}

	private Teacher toEntity(TeacherDto teacherDto) {
		Teacher teacher = new Teacher();
		teacher.setTitle(teacherDto.getTitle());
		teacher.setDepartment(teacherDto.getDepartment());
		teacher.setRoom(teacherDto.getRoom());
		return teacher;
	}

}