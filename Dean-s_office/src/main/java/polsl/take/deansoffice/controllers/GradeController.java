package polsl.take.deansoffice.controllers;

import java.net.URI;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@PostMapping("/student/{studentId}/subject/{subjectId}")
	public ResponseEntity<EntityModel<GradeDto>> createGrade(@PathVariable Integer studentId,
			@PathVariable Integer subjectId, @RequestBody GradeDto gradeDto) {
		EntityModel<GradeDto> model = gradeService.createGrade(studentId, subjectId, gradeDto);
		URI self = URI.create(model.getRequiredLink("self").getHref());
		return ResponseEntity.created(self).body(model);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<GradeDto>> updateGrade(@PathVariable Integer id, @RequestBody GradeDto gradeDto) {
		EntityModel<GradeDto> model = gradeService.updateGrade(id, gradeDto);
		return ResponseEntity.ok(model);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteGrade(@PathVariable Integer id) {
		gradeService.deleteGrade(id);
		return ResponseEntity.noContent().build();
	}
}
