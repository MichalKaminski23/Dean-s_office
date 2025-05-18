package polsl.take.deansoffice.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import polsl.take.deansoffice.controllers.SubjectController;
import polsl.take.deansoffice.controllers.TeacherController;
import polsl.take.deansoffice.controllers.TeacherSubjectController;
import polsl.take.deansoffice.dtos.TeacherSubjectDto;
import polsl.take.deansoffice.exceptions.ResourceConflictException;
import polsl.take.deansoffice.exceptions.ResourceNotFoundException;
import polsl.take.deansoffice.models.Subject;
import polsl.take.deansoffice.models.Teacher;
import polsl.take.deansoffice.models.TeacherSubject;
import polsl.take.deansoffice.models.User;
import polsl.take.deansoffice.repositories.SubjectRepository;
import polsl.take.deansoffice.repositories.TeacherRepository;
import polsl.take.deansoffice.repositories.TeacherSubjectRepository;
import polsl.take.deansoffice.repositories.UserRepository;

@Service
public class TeacherSubjectService {

	private final TeacherSubjectRepository teacherSubjectRepository;
	private final TeacherRepository teacherRepository;
	private final SubjectRepository subjectRepository;
	private final UserRepository userRepository;

	public TeacherSubjectService(TeacherSubjectRepository teacherSubjectRepository, TeacherRepository teacherRepository,
			SubjectRepository subjectRepository, UserRepository userRepository) {
		this.teacherSubjectRepository = teacherSubjectRepository;
		this.teacherRepository = teacherRepository;
		this.subjectRepository = subjectRepository;
		this.userRepository = userRepository;
	}

	public EntityModel<TeacherSubjectDto> getTeacherSubjectById(Integer id) {
		TeacherSubject teacherSubject = teacherSubjectRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Teacher's subject with id " + id + " not found"));
		return toDto(teacherSubject);
	}

	@Transactional
	public EntityModel<TeacherSubjectDto> createTeacherSubject(TeacherSubjectDto teacherSubjectDto) {
		Teacher teacher = teacherRepository.findById(teacherSubjectDto.getTeacherId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Teacher with id " + teacherSubjectDto.getTeacherId() + " not found"));
		Subject subject = subjectRepository.findById(teacherSubjectDto.getSubjectId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Subject with id " + teacherSubjectDto.getSubjectId() + " not found"));
		User user = userRepository.findById(teacherSubjectDto.getTeacherId()).orElseThrow(
				() -> new ResourceNotFoundException("User with id " + teacherSubjectDto.getTeacherId() + " not found"));

		teacher.setUser(user);

		if (user.isActive() == false) {
			throw new ResourceConflictException("User with id " + teacherSubjectDto.getTeacherId() + " is not active");
		}

		if (teacherSubjectRepository.existsByTeacherTeacherIdAndSubjectSubjectId(teacherSubjectDto.getTeacherId(),
				teacherSubjectDto.getSubjectId())) {
			throw new ResourceConflictException("Teacher with id " + teacherSubjectDto.getTeacherId()
					+ " already teaches subject with id " + teacherSubjectDto.getSubjectId());
		}

		TeacherSubject teacherSubject = new TeacherSubject();
		teacherSubject.setTeacher(teacher);
		teacherSubject.setSubject(subject);

		TeacherSubject savedTeacherSubject = teacherSubjectRepository.save(teacherSubject);
		return toDto(savedTeacherSubject);
	}

	@Transactional
	public EntityModel<TeacherSubjectDto> updateTeacherSubject(Integer id, TeacherSubjectDto teacherSubjectDto) {
		TeacherSubject teacherSubject = teacherSubjectRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Teacher's subject with id " + id + " not found"));

		Teacher teacher = teacherRepository.findById(teacherSubjectDto.getTeacherId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Teacher with id " + teacherSubjectDto.getTeacherId() + " not found"));

		Subject subject = subjectRepository.findById(teacherSubjectDto.getSubjectId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Subject with id " + teacherSubjectDto.getSubjectId() + " not found"));

		User user = userRepository.findById(teacherSubjectDto.getTeacherId()).orElseThrow(
				() -> new ResourceNotFoundException("User with id " + teacherSubjectDto.getTeacherId() + " not found"));

		teacher.setUser(user);

		if (user.isActive() == false) {
			throw new ResourceConflictException("User with id " + teacherSubjectDto.getTeacherId() + " is not active");
		}

		if (teacherSubjectRepository.existsByTeacherTeacherIdAndSubjectSubjectId(teacherSubjectDto.getTeacherId(),
				teacherSubjectDto.getSubjectId())) {
			throw new ResourceConflictException("Teacher with id " + teacherSubjectDto.getTeacherId()
					+ " already teaches subject with id " + teacherSubjectDto.getSubjectId());
		}

		teacherSubject.setTeacher(teacher);
		teacherSubject.setSubject(subject);

		TeacherSubject updatedTeacherSubject = teacherSubjectRepository.save(teacherSubject);
		return toDto(updatedTeacherSubject);
	}

	@Transactional
	public void deleteTeacherSubject(Integer id) {
		TeacherSubject teacherSubject = teacherSubjectRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Teacher's subject with id " + id + " not found"));
		teacherSubjectRepository.deleteById(id);
	}

	public CollectionModel<EntityModel<TeacherSubjectDto>> getAllSubjectsForTeacher(Integer teacherId) {
		Teacher teacher = teacherRepository.findById(teacherId)
				.orElseThrow(() -> new ResourceNotFoundException("Teacher with id " + teacherId + " not found"));

		List<EntityModel<TeacherSubjectDto>> subjects = teacherSubjectRepository.findByTeacherTeacherId(teacherId)
				.stream().map(this::toDto).collect(Collectors.toList());

		return CollectionModel.of(subjects,
				linkTo(methodOn(TeacherSubjectController.class).getAllSubjectsForTeacher(teacherId)).withSelfRel());
	}

	private EntityModel<TeacherSubjectDto> toDto(TeacherSubject teacherSubject) {
		TeacherSubjectDto teacherSubjectDto = new TeacherSubjectDto();
		teacherSubjectDto.setTeacherSubjectId(teacherSubject.getTeacherSubjectId());
		teacherSubjectDto.setTeacherId(teacherSubject.getTeacher().getTeacherId());
		teacherSubjectDto.setSubjectId(teacherSubject.getSubject().getSubjectId());

		return EntityModel.of(teacherSubjectDto,
				linkTo(methodOn(TeacherSubjectController.class)
						.getTeacherSubjectById(teacherSubjectDto.getTeacherSubjectId())).withSelfRel(),
				linkTo(methodOn(TeacherController.class).getTeacherById(teacherSubjectDto.getTeacherId()))
						.withRel("teacher"),
				linkTo(methodOn(SubjectController.class).getSubjectById(teacherSubjectDto.getSubjectId()))
						.withRel("subject"));
	}
}
