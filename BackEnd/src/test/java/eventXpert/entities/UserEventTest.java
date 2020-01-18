package eventXpert.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserEventTest {
	private UserEvent userEvent;
	private Integer expectedUserId;
	private Integer expectedEventId;
	
	@Before
	public void setup() {
		expectedUserId = 1234;
		expectedEventId = 2345;
		userEvent = new UserEvent(expectedUserId, expectedEventId);
	}
	
	@Test
	public void testDefaultConstructor() {
		userEvent = new UserEvent();
		
		assertNull(userEvent.getUserEventId());
		assertNull(userEvent.getUserId());
		assertNull(userEvent.getEventId());
		assertFalse(userEvent.getIsCheckedIn());
		assertFalse(userEvent.getIsAdmin());
	}
	
	@Test
	public void testUserEventGetters() {
		assertEquals(userEvent.getUserId(), expectedUserId);
		assertEquals(userEvent.getEventId(), expectedEventId);
		assertFalse(userEvent.getIsAdmin());
		assertFalse(userEvent.getIsCheckedIn());
	}
	
	@Test
	public void testUserEventSetters() {
		userEvent.setIsAdmin(true);
		userEvent.setCheckedIn(true);
		
		assertTrue(userEvent.getIsAdmin());
		assertTrue(userEvent.getIsCheckedIn());
	}
}

