package polsl.take.deansoffice.controllers;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorHandler {

	record ErrorResponse(Instant timestamp, int status, String error, String message, String path) {
	}

	private ErrorResponse body(HttpStatus status, String message, String path) {
		return new ErrorResponse(Instant.now(), status.value(), status.getReasonPhrase(), message, path);
	}
}
