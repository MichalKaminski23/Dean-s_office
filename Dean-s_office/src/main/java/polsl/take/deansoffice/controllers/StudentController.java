package polsl.take.deansoffice.controllers;

import java.net.URI;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import polsl.take.deansoffice.dtos.StudentDto;
import polsl.take.deansoffice.services.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {

	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<StudentDto>>> getAllStudents() {
		return ResponseEntity.ok(studentService.getAllStudents());
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<StudentDto>> getStudentById(@Valid @PathVariable Integer id) {
		return ResponseEntity.ok(studentService.getStudentById(id));
	}

	@PostMapping("/{id}")
	public ResponseEntity<EntityModel<StudentDto>> createStudent(@Valid @PathVariable Integer id,
			@Valid @RequestBody StudentDto studentDto) {
		EntityModel<StudentDto> createdStudent = studentService.createStudent(id, studentDto);
		URI self = URI.create(createdStudent.getRequiredLink("self").getHref());
		return ResponseEntity.created(self).body(createdStudent);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<EntityModel<StudentDto>> updateStudent(@Valid @PathVariable Integer id,
			@Valid @RequestBody StudentDto studentDto) {
		return ResponseEntity.ok(studentService.updateStudent(id, studentDto));
	}
}
