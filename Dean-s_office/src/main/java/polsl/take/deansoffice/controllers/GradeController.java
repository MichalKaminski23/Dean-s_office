package polsl.take.deansoffice.controllers;

import java.net.URI;
import java.util.Map;

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
import polsl.take.deansoffice.dtos.GradeDto;
import polsl.take.deansoffice.services.GradeService;

@RestController
@RequestMapping("/api/grades")
public class GradeController {
	private final GradeService gradeService;

	public GradeController(GradeService gradeService) {
		this.gradeService = gradeService;
	}

	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<GradeDto>>> getAllGrades() {
		return ResponseEntity.ok(gradeService.getAllGrades());
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<GradeDto>> getGradeById(@PathVariable Integer id) {
		return ResponseEntity.ok(gradeService.getGradeById(id));
	}

	@PostMapping("/students/{studentId}/subjects/{subjectId}")
	public ResponseEntity<EntityModel<GradeDto>> createGrade(@Valid @PathVariable Integer studentId,
			@PathVariable Integer subjectId, @RequestBody GradeDto gradeDto) {
		EntityModel<GradeDto> model = gradeService.createGrade(studentId, subjectId, gradeDto);
		URI self = URI.create(model.getRequiredLink("self").getHref());
		return ResponseEntity.created(self).body(model);
	}

	@PatchMapping("/{id}/students/{studentId}/subjects/{subjectId}")
	public ResponseEntity<EntityModel<GradeDto>> updateGrade(@Valid @PathVariable Integer id,
			@PathVariable Integer studentId, @PathVariable Integer subjectId,
			@RequestBody Map<String, Object> updates) {
		EntityModel<GradeDto> model = gradeService.updateGrade(id, studentId, subjectId, updates);
		return ResponseEntity.ok(model);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteGrade(@PathVariable Integer id) {
		gradeService.deleteGrade(id);
		return ResponseEntity.ok("Grade with id " + id + " was deleted successfully");
	}
}
