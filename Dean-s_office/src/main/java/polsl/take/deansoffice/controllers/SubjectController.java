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
import polsl.take.deansoffice.dtos.SubjectDto;
import polsl.take.deansoffice.services.SubjectService;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
	private final SubjectService subjectService;

	public SubjectController(SubjectService subjectService) {
		this.subjectService = subjectService;
	}

	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<SubjectDto>>> getAllSubjects() {
		return ResponseEntity.ok(subjectService.getAllSubjects());
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<SubjectDto>> getSubjectById(@Valid @PathVariable Integer id) {
		return ResponseEntity.ok(subjectService.getSubjectById(id));
	}

	@PostMapping
	public ResponseEntity<EntityModel<SubjectDto>> createSubject(@Valid @RequestBody SubjectDto subjectDto) {
		EntityModel<SubjectDto> model = subjectService.createSubject(subjectDto);
		URI self = URI.create(model.getRequiredLink("self").getHref());
		return ResponseEntity.created(self).body(model);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<EntityModel<SubjectDto>> updateSubject(@Valid @PathVariable Integer id,
			@Valid @RequestBody SubjectDto subjectDto) {
		EntityModel<SubjectDto> model = subjectService.updateSubject(id, subjectDto);
		return ResponseEntity.ok(model);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteSubject(@Valid @PathVariable Integer id) {
		subjectService.deleteSubject(id);
		return ResponseEntity.ok("Subject with id " + id + " was deleted successfully");
	}
}
