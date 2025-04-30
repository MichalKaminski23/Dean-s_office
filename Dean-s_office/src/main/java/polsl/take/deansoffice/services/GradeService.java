package polsl.take.deansoffice.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

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

	public EntityModel<GradeDto> createGrade(Integer studentId, Integer subjectId, GradeDto gradeDto) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Student with id " + studentId + " not found"));
		
		Subject subject = subjectRepository.findById(subjectId)
				.orElseThrow(() -> new ResourceNotFoundException("Subject with id " + subjectId + " not found"));
		
		User user = userRepository.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("User with id " + studentId + " not found"));

		student.setUser(user);

		if (user.isActive() == false) {
			throw new ResourceConflictException("User with id " + studentId + " is not active");
		}

		Grade grade = toEntity(gradeDto);
		grade.setStudent(student);
		grade.setSubject(subject);
		Grade savedGrade = gradeRepository.save(grade);
		return toDto(savedGrade);
	}

	public EntityModel<GradeDto> updateGrade(Integer id, Integer studentId, Integer subjectId,
			Map<String, Object> updates) {
		Grade grade = gradeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Grade with id " + id + " not found"));

		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Student with id " + studentId + " not found"));

		Subject subject = subjectRepository.findById(subjectId)
				.orElseThrow(() -> new ResourceNotFoundException("Subject with id " + subjectId + " not found"));

		User user = userRepository.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("User with id " + studentId + " not found"));

		student.setUser(user);

		if (user.isActive() == false) {
			throw new ResourceConflictException("User with id " + id + " is not active");
		}
		
		grade.setStudent(student);
		grade.setSubject(subject);

		updates.forEach((field, value) -> {
			switch (field) {
			case "finalGrade":
				grade.setFinalGrade((Integer) value);
				break;
			default:
				throw new ResourceConflictException("Unknown field: " + field);
			}
		});

		Grade updatedGrade = gradeRepository.save(grade);
		return toDto(updatedGrade);
	}

	public void deleteGrade(Integer id) {
		Grade grade = gradeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Grade with id " + id + " not found"));
		gradeRepository.deleteById(id);
	}
	
	public CollectionModel<EntityModel<GradeDto>> getAllGradesForStudent(Integer studentId) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Student with id " + studentId + " not found"));
		
	    List<EntityModel<GradeDto>> grades = gradeRepository.findByStudentStudentId(studentId).stream()
	            .map(this::toDto)
	            .collect(Collectors.toList());
	    
	    if(grades.size() == 0) {
	    	throw new ResourceNotFoundException("Student with id " + studentId + " doesn't have any grades");
	    }

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

	private Grade toEntity(GradeDto gradeDto) {
		Grade grade = new Grade();
		grade.setFinalGrade(gradeDto.getFinalGrade());
		return grade;
	}
}