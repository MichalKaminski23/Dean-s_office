package polsl.take.deansoffice.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import polsl.take.deansoffice.controllers.SubjectController;
import polsl.take.deansoffice.controllers.TeacherController;
import polsl.take.deansoffice.dtos.SubjectDto;
import polsl.take.deansoffice.models.Subject;
import polsl.take.deansoffice.models.Teacher;
import polsl.take.deansoffice.repositories.SubjectRepository;
import polsl.take.deansoffice.repositories.TeacherRepository;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class SubjectService {

	private final SubjectRepository subjectRepository;
	private final TeacherRepository teacherRepository;

	public SubjectService(SubjectRepository subjectRepository, TeacherRepository teacherRepository) {
		this.subjectRepository = subjectRepository;
		this.teacherRepository = teacherRepository;
	}

	public CollectionModel<EntityModel<SubjectDto>> getAllSubjects() {
		List<EntityModel<SubjectDto>> subjects = subjectRepository.findAll().stream().map(this::toDto)
				.collect(Collectors.toList());

		return CollectionModel.of(subjects, linkTo(methodOn(SubjectController.class).getAllSubjects()).withSelfRel());
	}

	public EntityModel<SubjectDto> getSubjectById(Integer id) {
		Subject subject = subjectRepository.findById(id).orElseThrow(() -> new RuntimeException("Subject not found"));
		return toDto(subject);
	}

	public EntityModel<SubjectDto> createSubject(SubjectDto dto) {
		Subject subject = new Subject();
		subject.setName(dto.getName());
		Teacher teacher = teacherRepository.findById(dto.getTeacheriD())
				.orElseThrow(() -> new RuntimeException("Teacher not found"));
		subject.setTeacher(teacher);

		Subject savedSubject = subjectRepository.save(subject);
		return toDto(savedSubject);
	}

	public EntityModel<SubjectDto> updateSubject(Integer id, SubjectDto subjectDto) {
		Subject existingSubject = subjectRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Subject not found"));

		existingSubject.setName(subjectDto.getName());
		if (!existingSubject.getTeacher().getTeacherId().equals(subjectDto.getTeacheriD())) {
			Teacher teacher = teacherRepository.findById(subjectDto.getTeacheriD())
					.orElseThrow(() -> new RuntimeException("Teacher not found"));
			existingSubject.setTeacher(teacher);
		}

		Subject updatedSubject = subjectRepository.save(existingSubject);
		return toDto(updatedSubject);
	}

	public void deleteSubject(Integer id) {
		subjectRepository.deleteById(id);
	}

	private EntityModel<SubjectDto> toDto(Subject subject) {
		SubjectDto subjectDto = new SubjectDto();
		subjectDto.setSubjectId(subject.getSubjectId());
		subjectDto.setName(subject.getName());
		subjectDto.setTeacheriD(subject.getTeacher().getTeacherId());

		return EntityModel.of(subjectDto,
				linkTo(methodOn(SubjectController.class).getSubjectById(subjectDto.getSubjectId())).withSelfRel(),
				linkTo(methodOn(TeacherController.class).getTeacherById(subjectDto.getTeacheriD()))
						.withRel("supervisor"));
	}
}
