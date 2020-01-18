package eventXpert.errors;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * Tests for ResourceNotFoundException
 */
public class ResourceNotFoundExceptionTest {
	@Test
	/**
	 * Test that ResourceNotFoundException is a RuntimeException
	 */
	public void shouldBeARuntimeError() {
		try {
			throw new ResourceNotFoundException();
		} catch (ResourceNotFoundException ex) {
			assertThat(ex, instanceOf(RuntimeException.class));
		}
	}
}
