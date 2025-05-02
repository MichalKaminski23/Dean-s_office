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
import org.springframework.web.bind.annotation.RequestParam;
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
	public ResponseEntity<CollectionModel<EntityModel<UserDto>>> getAllUsers(
			@RequestParam(required = false) Boolean active) {
		return ResponseEntity.ok(userService.getAllUsers(active));
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<UserDto>> getUserById(@Valid @PathVariable Integer id) {
		return ResponseEntity.ok(userService.getUserById(id));
	}

	@PostMapping
	public ResponseEntity<EntityModel<UserDto>> createUser(@Valid @RequestBody UserDto userDto) {
		EntityModel<UserDto> createdUser = userService.createUser(userDto);
		URI self = URI.create(createdUser.getRequiredLink("self").getHref());
		return ResponseEntity.created(self).body(createdUser);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<EntityModel<UserDto>> updateUser(@Valid @PathVariable Integer id,
			@Valid @RequestBody UserDto userDto) {
		return ResponseEntity.ok(userService.updateUser(id, userDto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> softDeleteUser(@Valid @PathVariable Integer id) {
		userService.softDeleteUser(id);
		return ResponseEntity.ok("User with id " + id + " was deactivated successfully");
	}

}
