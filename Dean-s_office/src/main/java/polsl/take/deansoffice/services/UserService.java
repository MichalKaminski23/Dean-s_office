package polsl.take.deansoffice.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import polsl.take.deansoffice.models.User;
import polsl.take.deansoffice.dtos.UserDto;
import polsl.take.deansoffice.repositories.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<UserDto> getAllUsers() {
		return userRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
	}

	public UserDto getUserById(Integer id) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
		return toDto(user);
	}

	public UserDto createUser(UserDto dto) {
		User user = toEntity(dto);
		User saved = userRepository.save(user);
		return toDto(saved);
	}

	public UserDto updateUser(Integer id, UserDto dto) {
		User existing = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
		existing.setName(dto.getName());
		existing.setSurname(dto.getSurname());
		existing.setEmail(dto.getEmail());
		existing.setPhone(dto.getPhone());
		existing.setCountry(dto.getCountry());
		existing.setCity(dto.getCity());
		existing.setPostalCode(dto.getPostalCode());
		existing.setStreet(dto.getStreet());
		existing.setApartNumber(dto.getApartNumber());
		existing.setStartDate(dto.getStartDate());
		existing.setEndDate(dto.getEndDate());
		existing.setActive(dto.isActive());
		User updated = userRepository.save(existing);
		return toDto(updated);
	}

	public void deleteUser(Integer id) {
		userRepository.deleteById(id);
	}

	private UserDto toDto(User user) {
		UserDto dto = new UserDto();
		dto.setUserId(user.getUserId());
		dto.setName(user.getName());
		dto.setSurname(user.getSurname());
		dto.setEmail(user.getEmail());
		dto.setPhone(user.getPhone());
		dto.setCountry(user.getCountry());
		dto.setCity(user.getCity());
		dto.setPostalCode(user.getPostalCode());
		dto.setStreet(user.getStreet());
		dto.setApartNumber(user.getApartNumber());
		dto.setStartDate(user.getStartDate());
		dto.setEndDate(user.getEndDate());
		dto.setActive(user.isActive());
		return dto;
	}

	private User toEntity(UserDto dto) {
		User user = new User();
		user.setName(dto.getName());
		user.setSurname(dto.getSurname());
		user.setEmail(dto.getEmail());
		user.setPhone(dto.getPhone());
		user.setCountry(dto.getCountry());
		user.setCity(dto.getCity());
		user.setPostalCode(dto.getPostalCode());
		user.setStreet(dto.getStreet());
		user.setApartNumber(dto.getApartNumber());
		user.setStartDate(dto.getStartDate());
		user.setEndDate(dto.getEndDate());
		user.setActive(dto.isActive());
		return user;
	}
}
