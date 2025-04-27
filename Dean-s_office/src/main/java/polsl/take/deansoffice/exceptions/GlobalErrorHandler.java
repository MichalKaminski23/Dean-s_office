package polsl.take.deansoffice.exceptions;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalErrorHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorBody handleValidation(MethodArgumentNotValidException ex) {
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage)
				.collect(Collectors.toList());
		return new ErrorBody(400, "Validation failed", errors);
	}

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ErrorBody> handleResponseStatus(ResponseStatusException ex) {
		ErrorBody errorBody = new ErrorBody(ex.getStatusCode().value(), ex.getReason());
		return new ResponseEntity<>(errorBody, ex.getStatusCode());
	}

	@ExceptionHandler(ServerErrorException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorBody handleServerError(ServerErrorException ex) {
		return new ErrorBody(500, "Server error");
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorBody handleResourceNotFound(ResourceNotFoundException ex) {
		return new ErrorBody(404, ex.getMessage());
	}

	@ExceptionHandler(ResourceConflictException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ErrorBody handleResourceConflict(ResourceConflictException ex) {
		return new ErrorBody(409, ex.getMessage());
	}

}
