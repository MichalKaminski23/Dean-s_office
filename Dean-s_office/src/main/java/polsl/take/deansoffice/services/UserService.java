package polsl.take.deansoffice.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import polsl.take.deansoffice.controllers.UserController;
import polsl.take.deansoffice.dtos.UserDto;
import polsl.take.deansoffice.exceptions.ResourceConflictException;
import polsl.take.deansoffice.exceptions.ResourceNotFoundException;
import polsl.take.deansoffice.models.User;
import polsl.take.deansoffice.repositories.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public CollectionModel<EntityModel<UserDto>> getAllUsers(Boolean active) {
		List<User> list = (active == null) ? userRepository.findAll() : userRepository.findByActive(active);

		List<EntityModel<UserDto>> users = list.stream().map(this::toDto).collect(Collectors.toList());

		if (users.size() == 0) {
			throw new ResourceNotFoundException("There are not any users yet.");
		}

		return CollectionModel.of(users, linkTo(methodOn(UserController.class).getAllUsers(active)).withSelfRel());
	}

	public EntityModel<UserDto> getUserById(Integer id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
		return toDto(user);
	}

	@Transactional
	public EntityModel<UserDto> createUser(UserDto userDto) {
		if (userRepository.existsByEmail(userDto.getEmail())) {
			throw new ResourceConflictException("Email already exists: " + userDto.getEmail());
		}

		if (userRepository.existsByPhone(userDto.getPhone())) {
			throw new ResourceConflictException("Phone already exists: " + userDto.getPhone());
		}

		User user = new User();
		toEntity(user, userDto);
		User savedUser = userRepository.save(user);
		return toDto(savedUser);
	}

	@Transactional
	public EntityModel<UserDto> updateUser(Integer id, UserDto userDto) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));

		if (!user.getEmail().equals(userDto.getEmail())
				&& userRepository.existsByEmailAndUserIdNot(userDto.getEmail(), userDto.getUserId())) {
			throw new ResourceConflictException("Email already exists: " + userDto.getEmail());
		}

		if (!user.getPhone().equals(userDto.getPhone())
				&& userRepository.existsByPhoneAndUserIdNot(userDto.getPhone(), userDto.getUserId())) {
			throw new ResourceConflictException("Phone already exists: " + userDto.getPhone());
		}

		toEntity(user, userDto);
		User updatedUser = userRepository.save(user);
		return toDto(updatedUser);
	}

	@Transactional
	public void softDeleteUser(Integer id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));

		user.setActive(false);
		userRepository.save(user);
	}

	private EntityModel<UserDto> toDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setUserId(user.getUserId());
		userDto.setName(user.getName());
		userDto.setSurname(user.getSurname());
		userDto.setEmail(user.getEmail());
		userDto.setPhone(user.getPhone());
		userDto.setCountry(user.getCountry());
		userDto.setCity(user.getCity());
		userDto.setPostalCode(user.getPostalCode());
		userDto.setStreet(user.getStreet());
		userDto.setApartNumber(user.getApartNumber());
		userDto.setStartDate(user.getStartDate());
		userDto.setEndDate(user.getEndDate());
		userDto.setActive(user.isActive());
		return EntityModel.of(userDto,
				linkTo(methodOn(UserController.class).getUserById(user.getUserId())).withSelfRel());
	}

	private User toEntity(User user, UserDto userDto) {
		// user.setUserId(userDto.getUserId());
		user.setName(userDto.getName());
		user.setSurname(userDto.getSurname());
		user.setEmail(userDto.getEmail());
		user.setPhone(userDto.getPhone());
		user.setCountry(userDto.getCountry());
		user.setCity(userDto.getCity());
		user.setPostalCode(userDto.getPostalCode());
		user.setStreet(userDto.getStreet());
		user.setApartNumber(userDto.getApartNumber());
		user.setStartDate(userDto.getStartDate());
		user.setEndDate(userDto.getEndDate());
		user.setActive(userDto.isActive());
		return user;
	}

}