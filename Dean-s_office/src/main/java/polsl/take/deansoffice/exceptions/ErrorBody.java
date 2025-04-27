package polsl.take.deansoffice.exceptions;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorBody {
	private int status;
	private String message;
	private List<String> errors;

	public ErrorBody(int status, String message) {
		this.status = status;
		this.message = message;
	}
}
