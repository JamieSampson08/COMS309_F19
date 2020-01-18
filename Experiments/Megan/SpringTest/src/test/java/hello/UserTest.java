package hello;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {
	@Test
	public void defaultConstructor(){
		User defaultUser = new User();
		assertEquals(-1, (long)defaultUser.getId());
		assertEquals("", defaultUser.getName());
	}
	
	@Test
	public void specializedConstructor(){
		User defaultUser = new User("expectedName", 1234);
		assertEquals(1234, (long)defaultUser.getId());
		assertEquals("expectedName", defaultUser.getName());
	}
}
