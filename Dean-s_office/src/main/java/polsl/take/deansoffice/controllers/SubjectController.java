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
	public ResponseEntity<EntityModel<SubjectDto>> getSubjectById(@PathVariable Integer id) {
		return ResponseEntity.ok(subjectService.getSubjectById(id));
	}

	@PostMapping
	public ResponseEntity<EntityModel<SubjectDto>> createSubject(@RequestBody SubjectDto dto) {
		EntityModel<SubjectDto> model = subjectService.createSubject(dto);
		URI self = URI.create(model.getRequiredLink("self").getHref());
		return ResponseEntity.created(self).body(model);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<SubjectDto>> updateSubject(@PathVariable Integer id,
			@RequestBody SubjectDto subjectDto) {
		return ResponseEntity.ok(subjectService.updateSubject(id, subjectDto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSubject(@PathVariable Integer id) {
		subjectService.deleteSubject(id);
		return ResponseEntity.noContent().build();
	}
}
