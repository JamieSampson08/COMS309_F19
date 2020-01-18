package eventXpert.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An exception to be used when a bad request is sent.
 * It returns a 400 (BAD REQUEST)
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
	/**
	 * Default constructor
	 */
	public BadRequestException() { }
	
	/**
	 * Bad Request Exception - bad information
	 *
	 * @param message to throw with error
	 */
	public BadRequestException(String message) {
		super(message);
	}
}