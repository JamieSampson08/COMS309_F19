package eventXpert.errors;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Tests for StorageExceptionTest
 */
public class StorageExceptionTest {
	@Test
	public void shouldBeStorageError() {
		try {
			throw new StorageException("Bad Things");
		} catch (StorageException ex) {
			assertThat(ex, instanceOf(StorageException.class));
			assertEquals(ex.getMessage(), "Bad Things");
		}
	}
}
