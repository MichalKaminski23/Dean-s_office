package polsl.take.deansoffice.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import polsl.take.deansoffice.models.User;
import polsl.take.deansoffice.controllers.UserController;
import polsl.take.deansoffice.dtos.UserDto;
import polsl.take.deansoffice.exceptions.MyException;
import polsl.take.deansoffice.repositories.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public CollectionModel<EntityModel<UserDto>> getAllUsers() {
		List<EntityModel<UserDto>> users = userRepository.findAll().stream().map(this::toDto)
				.collect(Collectors.toList());

		return CollectionModel.of(users, linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
	}

	public EntityModel<UserDto> getUserById(Integer id) {
		User user = userRepository.findById(id).orElseThrow(() -> new MyException("User not found"));
		return toDto(user);
	}

	public EntityModel<UserDto> createUser(UserDto userDto) {

		User user = toEntity(userDto);
		User savedUser = userRepository.save(user);
		return toDto(savedUser);

	}

	public EntityModel<UserDto> updateUser(Integer id, Map<String, Object> updates) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

		updates.forEach((field, value) -> {
			switch (field) {
			case "name":
				user.setName((String) value);
				break;
			case "surname":
				user.setSurname((String) value);
				break;
			case "email":
				user.setEmail((String) value);
				break;
			case "phone":
				user.setPhone((String) value);
				break;
			case "country":
				user.setCountry((String) value);
				break;
			case "city":
				user.setCity((String) value);
				break;
			case "postalCode":
				user.setPostalCode((String) value);
				break;
			case "street":
				user.setStreet((String) value);
				break;
			case "apartNumber":
				user.setApartNumber((String) value);
				break;
			case "startDate":
				user.setStartDate(LocalDate.parse(value.toString()));
				break;
			case "endDate":
				user.setEndDate(value == null ? null : LocalDate.parse(value.toString()));
				break;
			case "active":
				user.setActive((Boolean) value);
				break;
			default:
				throw new IllegalArgumentException("Unknown field: " + field);
			}
		});

		User updatedUser = userRepository.save(user);
		return toDto(updatedUser);
	}

//	public void deleteUser(Integer id) {
//		userRepository.deleteById(id);
//	}

	public void softDeleteUser(Integer id) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
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

		if (userDto.getName().isBlank() || userDto.getName().isEmpty()) {
			throw new MyException("Error");
		} else {
			return EntityModel.of(userDto,
					linkTo(methodOn(UserController.class).getUserById(user.getUserId())).withSelfRel());
		}
	}

	private User toEntity(UserDto userDto) {
		User user = new User();
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
