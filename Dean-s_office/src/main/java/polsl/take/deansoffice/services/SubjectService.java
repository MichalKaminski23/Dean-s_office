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
import polsl.take.deansoffice.dtos.SubjectDto;
import polsl.take.deansoffice.exceptions.ResourceConflictException;
import polsl.take.deansoffice.exceptions.ResourceNotFoundException;
import polsl.take.deansoffice.models.Subject;
import polsl.take.deansoffice.models.Teacher;
import polsl.take.deansoffice.models.User;
import polsl.take.deansoffice.repositories.SubjectRepository;
import polsl.take.deansoffice.repositories.TeacherRepository;
import polsl.take.deansoffice.repositories.UserRepository;

@Service
public class SubjectService {

	private final SubjectRepository subjectRepository;
	private final TeacherRepository teacherRepository;
	private final UserRepository userRepository;

	public SubjectService(SubjectRepository subjectRepository, TeacherRepository teacherRepository,
			UserRepository userRepository) {
		this.subjectRepository = subjectRepository;
		this.teacherRepository = teacherRepository;
		this.userRepository = userRepository;
	}

	public CollectionModel<EntityModel<SubjectDto>> getAllSubjects() {
		List<EntityModel<SubjectDto>> subjects = subjectRepository.findAll().stream().map(this::toDto)
				.collect(Collectors.toList());

		if (subjects.size() == 0) {
			throw new ResourceNotFoundException("There are not any subjects yet.");
		}

		return CollectionModel.of(subjects, linkTo(methodOn(SubjectController.class).getAllSubjects()).withSelfRel());
	}

	public EntityModel<SubjectDto> getSubjectById(Integer id) {
		Subject subject = subjectRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Subject with id " + id + " not found"));
		return toDto(subject);
	}

	@Transactional
	public EntityModel<SubjectDto> createSubject(Integer teacherId, SubjectDto subjectDto) {
		Teacher teacher = teacherRepository.findById(teacherId)
				.orElseThrow(() -> new ResourceNotFoundException("Teacher with id " + teacherId + " not found"));

		User user = userRepository.findById(teacherId)
				.orElseThrow(() -> new ResourceNotFoundException("User with id " + teacherId + " not found"));

		teacher.setUser(user);

		if (user.isActive() == false) {
			throw new ResourceConflictException("User with id " + teacherId + " is not active");
		}

		if (subjectRepository.existsByName(subjectDto.getName())) {
			throw new ResourceConflictException("Subject with name " + subjectDto.getName() + " already exists");
		}

		if (subjectRepository.existsByTeacherTeacherId(teacherId)) {
			throw new ResourceConflictException("Only one teacher id: (" + teacherId + ") can coordinate one subject");
		}

		Subject subject = new Subject();
		toEntity(subject, subjectDto);
		subject.setTeacher(teacher);
		Subject savedSubject = subjectRepository.save(subject);
		return toDto(savedSubject);
	}

	@Transactional
	public EntityModel<SubjectDto> updateSubject(Integer id, Integer teacherId, SubjectDto subjectDto) {
		Subject subject = subjectRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Subject with id " + id + " not found"));

		Teacher teacher = teacherRepository.findById(teacherId)
				.orElseThrow(() -> new ResourceNotFoundException("Teacher with id " + teacherId + " not found"));

		User user = userRepository.findById(teacherId)
				.orElseThrow(() -> new ResourceNotFoundException("User with id " + teacherId + " not found"));

		if (subjectRepository.existsByName(subjectDto.getName())) {
			throw new ResourceConflictException("Subject with name " + subjectDto.getName() + " already exists");
		}

		teacher.setUser(user);

		if (user.isActive() == false) {
			throw new ResourceConflictException("User with id " + teacherId + " is not active");
		}

		subject.setTeacher(teacher);
		toEntity(subject, subjectDto);
		Subject updatedSubject = subjectRepository.save(subject);
		return toDto(updatedSubject);
	}

	@Transactional
	public void deleteSubject(Integer id) {
		Subject subject = subjectRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Subject with id " + id + " not found"));
		if (!subject.getGrades().isEmpty()) {
			throw new ResourceConflictException("Cannot delete subject " + id + " — it has related grades");
		}
		subjectRepository.deleteById(id);
	}

	private EntityModel<SubjectDto> toDto(Subject subject) {
		SubjectDto subjectDto = new SubjectDto();
		subjectDto.setSubjectId(subject.getSubjectId());
		subjectDto.setName(subject.getName());
		subjectDto.setTeacherId(subject.getTeacher().getTeacherId());

		return EntityModel.of(subjectDto,
				linkTo(methodOn(SubjectController.class).getSubjectById(subjectDto.getSubjectId())).withSelfRel(),
				linkTo(methodOn(TeacherController.class).getTeacherById(subjectDto.getTeacherId()))
						.withRel("supervisor"));
	}

	private Subject toEntity(Subject subject, SubjectDto subjectDto) {
		subject.setName(subjectDto.getName());
		return subject;
	}
}
