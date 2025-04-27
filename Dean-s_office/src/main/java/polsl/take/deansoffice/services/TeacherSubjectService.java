package polsl.take.deansoffice.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import polsl.take.deansoffice.controllers.SubjectController;
import polsl.take.deansoffice.controllers.TeacherController;
import polsl.take.deansoffice.controllers.TeacherSubjectController;
import polsl.take.deansoffice.dtos.TeacherSubjectDto;
import polsl.take.deansoffice.models.Subject;
import polsl.take.deansoffice.models.Teacher;
import polsl.take.deansoffice.models.TeacherSubject;
import polsl.take.deansoffice.repositories.SubjectRepository;
import polsl.take.deansoffice.repositories.TeacherRepository;
import polsl.take.deansoffice.repositories.TeacherSubjectRepository;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class TeacherSubjectService {

	private final TeacherSubjectRepository teacherSubjectRepository;
	private final TeacherRepository teacherRepository;
	private final SubjectRepository subjectRepository;

	public TeacherSubjectService(TeacherSubjectRepository teacherSubjectRepository, TeacherRepository teacherRepository,
			SubjectRepository subjectRepository) {
		this.teacherSubjectRepository = teacherSubjectRepository;
		this.teacherRepository = teacherRepository;
		this.subjectRepository = subjectRepository;
	}

	public CollectionModel<EntityModel<TeacherSubjectDto>> getAllTeacherSubjects() {
		List<EntityModel<TeacherSubjectDto>> teacherSubjects = teacherSubjectRepository.findAll().stream()
				.map(this::toDto).collect(Collectors.toList());

		return CollectionModel.of(teacherSubjects,
				linkTo(methodOn(TeacherSubjectController.class).getAllTeacherSubjects()).withSelfRel());
	}

	public EntityModel<TeacherSubjectDto> getTeacherSubjectById(Integer id) {
		TeacherSubject teacherSubject = teacherSubjectRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("TeacherSubject not found"));
		return toDto(teacherSubject);
	}

	public EntityModel<TeacherSubjectDto> createTeacherSubject(Integer teacherId, Integer subjectId) {
		Teacher teacher = teacherRepository.findById(teacherId)
				.orElseThrow(() -> new RuntimeException("Teacher not found"));
		Subject subject = subjectRepository.findById(subjectId)
				.orElseThrow(() -> new RuntimeException("Subject not found"));

		TeacherSubject teacherSubject = new TeacherSubject();
		teacherSubject.setTeacher(teacher);
		teacherSubject.setSubject(subject);

		TeacherSubject saved = teacherSubjectRepository.save(teacherSubject);
		return toDto(saved);
	}

	public EntityModel<TeacherSubjectDto> updateTeacherSubject(Integer id, Integer teacherId, Integer subjectId) {
		TeacherSubject teacherSubject = teacherSubjectRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("TeacherSubject not found"));
		if (!teacherSubject.getTeacher().getTeacherId().equals(teacherId)) {
			Teacher teacher = teacherRepository.findById(teacherId)
					.orElseThrow(() -> new RuntimeException("Teacher not found"));
			teacherSubject.setTeacher(teacher);
		}
		if (!teacherSubject.getSubject().getSubjectId().equals(subjectId)) {
			Subject subject = subjectRepository.findById(subjectId)
					.orElseThrow(() -> new RuntimeException("Subject not found"));
			teacherSubject.setSubject(subject);
		}
		TeacherSubject updated = teacherSubjectRepository.save(teacherSubject);
		return toDto(updated);
	}

	public void deleteTeacherSubject(Integer id) {
		teacherSubjectRepository.deleteById(id);
	}

	private EntityModel<TeacherSubjectDto> toDto(TeacherSubject teacherSubject) {
		TeacherSubjectDto teacherSubjectDto = new TeacherSubjectDto(teacherSubject.getTeacherSubjectId(),
				teacherSubject.getTeacher().getTeacherId(), teacherSubject.getSubject().getSubjectId());

		return EntityModel.of(teacherSubjectDto,
				linkTo(methodOn(TeacherSubjectController.class)
						.getTeacherSubjectById(teacherSubjectDto.getTeacherSubjectId())).withSelfRel(),
				linkTo(methodOn(TeacherController.class).getTeacherById(teacherSubjectDto.getTeacherId()))
						.withRel("teacher"),
				linkTo(methodOn(SubjectController.class).getSubjectById(teacherSubjectDto.getSubjectId()))
						.withRel("subject"));
	}
}
