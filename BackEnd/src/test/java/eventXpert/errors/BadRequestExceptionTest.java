package eventXpert.errors;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Tests for ResourceNotFoundException
 */
public class BadRequestExceptionTest {
	@Test
	/**
	 Test that BadRequestException is a RuntimeException
	 */
	public void shouldBeARuntimeError() {
		try {
			throw new BadRequestException("expectedMessage");
		} catch (BadRequestException ex) {
			assertThat(ex, instanceOf(RuntimeException.class));
			assertEquals(ex.getMessage(), "expectedMessage");
		}
	}
}
