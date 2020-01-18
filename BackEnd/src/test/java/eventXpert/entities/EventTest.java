package eventXpert.entities;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests for Event entity
 */
public class EventTest {
	private Event testEvent;
	
	@Before
	public void setUp() {
		Date startDateTime = new Date(12345678);
		Date endDateTime = new Date(123456789);
		testEvent = new Event("myEvent", "location", "description", startDateTime, endDateTime);
	}
	
	@Test
	public void testDefaultConstructor() {
		Event testEvent = new Event();
		
		assertNull(testEvent.getId());
		assertNull(testEvent.getName());
		assertNull(testEvent.getLocation());
		assertNull(testEvent.getDescription());
		assertNull(testEvent.getStartDateTime());
		assertNull(testEvent.getEndDateTime());
	}
	
	@Test
	public void eventTestGetters() {
		Date startDateTime = new Date(12345678);
		Date endDateTime = new Date(123456789);
		
		assertEquals("myEvent", testEvent.getName());
		assertEquals("location", testEvent.getLocation());
		assertEquals("description", testEvent.getDescription());
		assertEquals(startDateTime, testEvent.getStartDateTime());
		assertEquals(endDateTime, testEvent.getEndDateTime());
	}
	
	@Test
	public void eventTestSetters() {
		Date newStartDateTime = new Date(1234);
		Date newEndDateTime = new Date(12345);
		
		testEvent.setName("newName");
		testEvent.setLocation("newLocation");
		testEvent.setDescription("newDescription");
		testEvent.setStartDateTime(newStartDateTime);
		testEvent.setEndDateTime(newEndDateTime);
		
		assertEquals("newName", testEvent.getName());
		assertEquals("newLocation", testEvent.getLocation());
		assertEquals("newDescription", testEvent.getDescription());
		assertEquals(newStartDateTime, testEvent.getStartDateTime());
		assertEquals(newEndDateTime, testEvent.getEndDateTime());
	}
}
