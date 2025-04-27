package polsl.take.deansoffice.services;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import polsl.take.deansoffice.controllers.GradeController;
import polsl.take.deansoffice.controllers.StudentController;
import polsl.take.deansoffice.controllers.SubjectController;
import polsl.take.deansoffice.dtos.GradeDto;
import polsl.take.deansoffice.models.Grade;
import polsl.take.deansoffice.models.Student;
import polsl.take.deansoffice.models.Subject;
import polsl.take.deansoffice.repositories.GradeRepository;
import polsl.take.deansoffice.repositories.StudentRepository;
import polsl.take.deansoffice.repositories.SubjectRepository;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradeService {

	private final GradeRepository gradeRepository;
	private final StudentRepository studentRepository;
	private final SubjectRepository subjectRepository;

	public GradeService(GradeRepository gradeRepository, StudentRepository studentRepository,
			SubjectRepository subjectRepository) {
		this.gradeRepository = gradeRepository;
		this.studentRepository = studentRepository;
		this.subjectRepository = subjectRepository;
	}

	public CollectionModel<EntityModel<GradeDto>> getAllGrades() {
		List<EntityModel<GradeDto>> grades = gradeRepository.findAll().stream().map(this::toDto)
				.collect(Collectors.toList());

		return CollectionModel.of(grades, linkTo(methodOn(GradeController.class).getAllGrades()).withSelfRel());
	}

	public EntityModel<GradeDto> getGradeById(Integer id) {
		Grade grade = gradeRepository.findById(id).orElseThrow(() -> new RuntimeException("Grade not found"));
		return toDto(grade);
	}

	public EntityModel<GradeDto> createGrade(Integer studentId, Integer subjectId, GradeDto gradeDto) {
		Grade grade = new Grade();
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new RuntimeException("Student not found"));
		Subject subject = subjectRepository.findById(subjectId)
				.orElseThrow(() -> new RuntimeException("Subject not found"));

		grade.setStudent(student);
		grade.setSubject(subject);
		grade.setFinalGrade(gradeDto.getFinalGrade());

		Grade saved = gradeRepository.save(grade);
		return toDto(saved);
	}

	public EntityModel<GradeDto> updateGrade(Integer id, GradeDto gradeDto) {
		Grade existingGrade = gradeRepository.findById(id).orElseThrow(() -> new RuntimeException("Grade not found"));

		// optionally update relationships if they changed
		if (!existingGrade.getStudent().getStudentId().equals(gradeDto.getStudentId())) {
			Student student = studentRepository.findById(gradeDto.getStudentId())
					.orElseThrow(() -> new RuntimeException("Student not found"));
			existingGrade.setStudent(student);
		}
		if (!existingGrade.getSubject().getSubjectId().equals(gradeDto.getSubjectId())) {
			Subject subject = subjectRepository.findById(gradeDto.getSubjectId())
					.orElseThrow(() -> new RuntimeException("Subject not found"));
			existingGrade.setSubject(subject);
		}
		existingGrade.setFinalGrade(gradeDto.getFinalGrade());

		Grade updatedGrade = gradeRepository.save(existingGrade);
		return toDto(updatedGrade);
	}

	public void deleteGrade(Integer id) {
		gradeRepository.deleteById(id);
	}

	private EntityModel<GradeDto> toDto(Grade grade) {
		GradeDto gradeDto = new GradeDto();
		gradeDto.setGradeId(grade.getGradeId());
		gradeDto.setStudentId(grade.getStudent().getStudentId());
		gradeDto.setSubjectId(grade.getSubject().getSubjectId());
		gradeDto.setFinalGrade(grade.getFinalGrade());

		return EntityModel.of(gradeDto,
				linkTo(methodOn(GradeController.class).getGradeById(grade.getGradeId())).withSelfRel(),
				linkTo(methodOn(StudentController.class).getStudentById(grade.getStudent().getStudentId()))
						.withRel("student"),
				linkTo(methodOn(SubjectController.class).getSubjectById(grade.getSubject().getSubjectId()))
						.withRel("subject"));
	}

}
