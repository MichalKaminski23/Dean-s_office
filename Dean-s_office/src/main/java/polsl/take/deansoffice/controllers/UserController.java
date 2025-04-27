package polsl.take.deansoffice.controllers;

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
import polsl.take.deansoffice.dtos.UserDto;
import polsl.take.deansoffice.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<UserDto>>> getAllUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<UserDto>> getUserById(@PathVariable Integer id) {
		return ResponseEntity.ok(userService.getUserById(id));
	}

	@PostMapping
	public ResponseEntity<EntityModel<UserDto>> createUser(@Valid @RequestBody UserDto userDto) {
		return ResponseEntity.ok(userService.createUser(userDto));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<EntityModel<UserDto>> updateUser(@Valid @PathVariable Integer id,
			@RequestBody Map<String, Object> updates) {
		return ResponseEntity.ok(userService.updateUser(id, updates));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> softDeleteUser(@PathVariable Integer id) {
		return ResponseEntity.noContent().build();
	}

}
