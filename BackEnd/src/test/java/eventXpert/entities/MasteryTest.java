package eventXpert.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for user entity
 */
public class MasteryTest {
	Mastery testMastery;
	
	@Before
	public void setUp() {
		testMastery = new Mastery(25, 15, 3);
	}
	
	@Test
	public void testDefaultConstructor() {
		assertEquals(25, testMastery.getRegisterPoints());
		assertEquals(15, testMastery.getCheckInPoints());
		assertEquals(3, testMastery.getCreateEventPoints());
	}
}
