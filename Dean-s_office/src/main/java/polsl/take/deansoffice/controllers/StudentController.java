package polsl.take.deansoffice.controllers;

import java.net.URI;
import java.util.Map;

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
	public ResponseEntity<EntityModel<StudentDto>> getStudentById(@PathVariable Integer id) {
		return ResponseEntity.ok(studentService.getStudentById(id));
	}

	@PostMapping("/{id}")
	public ResponseEntity<EntityModel<StudentDto>> createStudent(@PathVariable Integer id,
			@RequestBody StudentDto studentDto) {
		EntityModel<StudentDto> createdStudent = studentService.createStudent(id, studentDto);
		URI self = URI.create(createdStudent.getRequiredLink("self").getHref());
		return ResponseEntity.created(self).body(createdStudent);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<EntityModel<StudentDto>> updateStudent(@PathVariable Integer id,
			@RequestBody Map<String, Object> updates) {
		return ResponseEntity.ok(studentService.updateStudent(id, updates));
	}
}
