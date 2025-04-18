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

import polsl.take.deansoffice.dtos.TeacherDto;
import polsl.take.deansoffice.services.TeacherService;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
	
	private final TeacherService teacherService;

	public TeacherController(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<TeacherDto>>> getAllTeachers() {
		return ResponseEntity.ok(teacherService.getAllTeachers());
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<TeacherDto>> getTeacherById(@PathVariable Integer id) {
		return ResponseEntity.ok(teacherService.getTeacherById(id));
	}

	@PostMapping("/{id}")
	public ResponseEntity<EntityModel<TeacherDto>> createTeacher(@PathVariable Integer id,
			@RequestBody TeacherDto teacherDto) {
		EntityModel<TeacherDto> createdTeacher = teacherService.createTeacher(id, teacherDto);
		URI self = URI.create(createdTeacher.getRequiredLink("self").getHref());
		return ResponseEntity.created(self).body(createdTeacher);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<EntityModel<TeacherDto>> updateTeacher(@PathVariable Integer id,
			@RequestBody Map<String, Object> updates) {
		return ResponseEntity.ok(teacherService.updateTeacher(id, updates));
	}
}
