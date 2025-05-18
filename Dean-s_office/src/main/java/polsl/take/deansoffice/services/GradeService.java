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
import polsl.take.deansoffice.controllers.SubjectController;
import polsl.take.deansoffice.dtos.GradeDto;
import polsl.take.deansoffice.exceptions.ResourceConflictException;
import polsl.take.deansoffice.exceptions.ResourceNotFoundException;
import polsl.take.deansoffice.models.Grade;
import polsl.take.deansoffice.models.Student;
import polsl.take.deansoffice.models.Subject;
import polsl.take.deansoffice.models.User;
import polsl.take.deansoffice.repositories.GradeRepository;
import polsl.take.deansoffice.repositories.StudentRepository;
import polsl.take.deansoffice.repositories.SubjectRepository;
import polsl.take.deansoffice.repositories.UserRepository;

@Service
public class GradeService {

	private final GradeRepository gradeRepository;
	private final StudentRepository studentRepository;
	private final SubjectRepository subjectRepository;
	private final UserRepository userRepository;

	public GradeService(GradeRepository gradeRepository, StudentRepository studentRepository,
			SubjectRepository subjectRepository, UserRepository userRepository) {
		this.gradeRepository = gradeRepository;
		this.studentRepository = studentRepository;
		this.subjectRepository = subjectRepository;
		this.userRepository = userRepository;
	}

	public CollectionModel<EntityModel<GradeDto>> getAllGrades() {
		List<EntityModel<GradeDto>> grades = gradeRepository.findAll().stream().map(this::toDto)
				.collect(Collectors.toList());

		return CollectionModel.of(grades, linkTo(methodOn(GradeController.class).getAllGrades()).withSelfRel());
	}

	public EntityModel<GradeDto> getGradeById(Integer id) {
		Grade grade = gradeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Grade with id " + id + " not found"));
		return toDto(grade);
	}

	@Transactional
	public EntityModel<GradeDto> createGrade(GradeDto gradeDto) {
		Student student = studentRepository.findById(gradeDto.getStudentId()).orElseThrow(
				() -> new ResourceNotFoundException("Student with id " + gradeDto.getStudentId() + " not found"));

		Subject subject = subjectRepository.findById(gradeDto.getSubjectId()).orElseThrow(
				() -> new ResourceNotFoundException("Subject with id " + gradeDto.getSubjectId() + " not found"));

		User user = userRepository.findById(gradeDto.getStudentId()).orElseThrow(
				() -> new ResourceNotFoundException("User with id " + gradeDto.getStudentId() + " not found"));

		if (gradeRepository.existsByStudentStudentIdAndSubjectSubjectId(gradeDto.getStudentId(),
				gradeDto.getSubjectId())) {
			throw new ResourceConflictException("Student with id " + gradeDto.getStudentId()
					+ " has a grade from subject with id " + gradeDto.getSubjectId());
		}

		student.setUser(user);

		if (user.isActive() == false) {
			throw new ResourceConflictException("User with id " + gradeDto.getStudentId() + " is not active");
		}

		Grade grade = new Grade();
		toEntity(grade, gradeDto);
		grade.setStudent(student);
		grade.setSubject(subject);
		Grade savedGrade = gradeRepository.save(grade);
		return toDto(savedGrade);
	}

	@Transactional
	public EntityModel<GradeDto> updateGrade(Integer id, GradeDto gradeDto) {
		Grade grade = gradeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Grade with id " + id + " not found"));

		Student student = studentRepository.findById(gradeDto.getStudentId()).orElseThrow(
				() -> new ResourceNotFoundException("Student with id " + gradeDto.getStudentId() + " not found"));

		Subject subject = subjectRepository.findById(gradeDto.getSubjectId()).orElseThrow(
				() -> new ResourceNotFoundException("Subject with id " + gradeDto.getSubjectId() + " not found"));

		User user = userRepository.findById(gradeDto.getStudentId()).orElseThrow(
				() -> new ResourceNotFoundException("User with id " + gradeDto.getStudentId() + " not found"));

		student.setUser(user);

		if (user.isActive() == false) {
			throw new ResourceConflictException("User with id " + id + " is not active");
		}

		grade.setStudent(student);
		grade.setSubject(subject);
		toEntity(grade, gradeDto);
		Grade updatedGrade = gradeRepository.save(grade);
		return toDto(updatedGrade);
	}

	@Transactional
	public void deleteGrade(Integer id) {
		Grade grade = gradeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Grade with id " + id + " not found"));
		gradeRepository.deleteById(id);
	}

	public CollectionModel<EntityModel<GradeDto>> getAllGradesForStudent(Integer studentId) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Student with id " + studentId + " not found"));

		List<EntityModel<GradeDto>> grades = gradeRepository.findByStudentStudentId(studentId).stream().map(this::toDto)
				.collect(Collectors.toList());

		return CollectionModel.of(grades,
				linkTo(methodOn(GradeController.class).getAllGradesForStudent(studentId)).withSelfRel());
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

	private Grade toEntity(Grade grade, GradeDto gradeDto) {
		grade.setFinalGrade(gradeDto.getFinalGrade());
		return grade;
	}
}