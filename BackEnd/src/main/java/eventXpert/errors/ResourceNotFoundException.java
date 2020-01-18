package eventXpert.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An exception to be used when a resource is not found.
 * It returns a 404 (NOT FOUND)
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
	/**
	 * Default constructor
	 */
	public ResourceNotFoundException() { }
	
	/**
	 * Resource Not Found Exception - can't find something
	 *
	 * @param message to be throw with error
	 */
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
