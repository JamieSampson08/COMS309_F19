package eventXpert.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for user entity
 */
public class UserTest {
	User testUser;
	User testUser2;
	
	@Before
	public void setUp() {
		testUser = new User("Crazy", "Bob", "crazy_bob@gmail.com");
		testUser2 = new User("Hiccup", "Berk", "hiccup_berk@gmail.com");
	}
	
	@Test
	public void testDefaultConstructor() {
		testUser = new User();
		
		assertNull(testUser.getId());
		assertNull(testUser.getFirstName());
		assertNull(testUser.getLastName());
		assertNull(testUser.getEmail());
	}
	
	@Test
	public void testGetUserData() {
		assertEquals(testUser.getFirstName(), "Crazy");
		assertEquals(testUser.getLastName(), "Bob");
		assertEquals(testUser.getEmail(), "crazy_bob@gmail.com");
	}
	
	@Test
	public void testSetUserData() {
		testUser2.setFirstName("Hiccup");
		testUser2.setLastName("Berk");
		
		assertEquals(testUser2.getFirstName(), "Hiccup");
		assertEquals(testUser2.getLastName(), "Berk");
	}
}
