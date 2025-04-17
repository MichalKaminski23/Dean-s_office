package polsl.take.deansoffice.controllers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

}
