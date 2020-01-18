package hello;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GreetingTest {
	@Test
	public void defaultConstructor() {
		Greeting defaultGreeting = new Greeting();
		assertEquals(-1, defaultGreeting.getId());
		assertEquals("", defaultGreeting.getContent());
	}
	
	@Test
	public void specializedConstructor() {
		Greeting specializedGreeting = new Greeting(123456, "expectedContent");
		assertEquals(123456, specializedGreeting.getId());
		assertEquals("expectedContent", specializedGreeting.getContent());
	}
	
	@Test
	public void userConstructor() {
		User user = new User("name", 123);
		Greeting userGreeting = new Greeting(user);
		assertEquals(123, userGreeting.getId());
		assertEquals("Hello, name!", userGreeting.getContent());
		
	}
}