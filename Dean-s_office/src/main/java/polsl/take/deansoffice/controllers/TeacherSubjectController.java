package polsl.take.deansoffice.controllers;

import java.net.URI;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import polsl.take.deansoffice.dtos.TeacherSubjectDto;
import polsl.take.deansoffice.services.TeacherSubjectService;

@RestController
@RequestMapping("/api/teacher-subjects")
public class TeacherSubjectController {
	private final TeacherSubjectService teacherSubjectService;

	public TeacherSubjectController(TeacherSubjectService teacherSubjectService) {
		this.teacherSubjectService = teacherSubjectService;
	}

	/*
	 * @GetMapping public
	 * ResponseEntity<CollectionModel<EntityModel<TeacherSubjectDto>>>
	 * getAllTeacherSubjects() { return
	 * ResponseEntity.ok(teacherSubjectService.getAllTeacherSubjects()); }
	 */

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<TeacherSubjectDto>> getTeacherSubjectById(@Valid @PathVariable Integer id) {
		return ResponseEntity.ok(teacherSubjectService.getTeacherSubjectById(id));
	}

	@PostMapping
	public ResponseEntity<EntityModel<TeacherSubjectDto>> createTeacherSubject(
			@Valid @RequestBody TeacherSubjectDto teacherSubjectDto) {
		EntityModel<TeacherSubjectDto> model = teacherSubjectService.createTeacherSubject(teacherSubjectDto);
		URI self = URI.create(model.getRequiredLink("self").getHref());
		return ResponseEntity.created(self).body(model);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<EntityModel<TeacherSubjectDto>> updateTeacherSubject(@Valid @PathVariable Integer id,
			@Valid @RequestBody TeacherSubjectDto teacherSubjectDto) {
		return ResponseEntity.ok(teacherSubjectService.updateTeacherSubject(id, teacherSubjectDto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTeacherSubject(@Valid @PathVariable Integer id) {
		teacherSubjectService.deleteTeacherSubject(id);
		return ResponseEntity.ok("Teacher's subject with id " + id + " was deleted successfully");
	}

	@GetMapping("/teachers/{teacherId}/subjects")
	public ResponseEntity<CollectionModel<EntityModel<TeacherSubjectDto>>> getAllSubjectsForTeacher(
			@Valid @PathVariable Integer teacherId) {
		return ResponseEntity.ok(teacherSubjectService.getAllSubjectsForTeacher(teacherId));
	}
}
