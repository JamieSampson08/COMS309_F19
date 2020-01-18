package eventXpert.controllers;

import eventXpert.entities.Mastery;
import eventXpert.entities.User;
import eventXpert.entities.UserEvent;
import eventXpert.errors.BadRequestException;
import eventXpert.errors.ResourceNotFoundException;
import eventXpert.repositories.EventRepository;
import eventXpert.repositories.MasteryRepository;
import eventXpert.repositories.UserEventRepository;
import eventXpert.repositories.UserRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * Tests for UserEventController
 */
@RunWith(MockitoJUnitRunner.class)
public class UserEventControllerTest {
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	@InjectMocks
	private UserEventController testUserEventController = new UserEventController();
	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private UserRepository userRepository;
	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private EventRepository eventRepository;
	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private UserEventRepository userEventRepository;
	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private MasteryRepository masteryRepository;
	private ArrayList<UserEvent> expectedUserEvents;
	private UserEvent expectedUserEvent;
	private Integer expectedUserId;
	private Integer expectedEventId;
	private User expectedUser;
	private Mastery expectedMastery;
	
	@Before
	public void setup() {
		expectedUserId = 1234;
		expectedEventId = 5678;
		expectedUserEvent = new UserEvent(expectedUserId, expectedEventId);
		expectedUserEvents = new ArrayList<>();
		expectedUserEvents.add(expectedUserEvent);
		expectedUser = new User("Test", "User", "testUser@gmail.com");
		expectedMastery = new Mastery(10, 20, 40);
		ArrayList<Mastery> listOfMasteries = new ArrayList<>();
		listOfMasteries.add(expectedMastery);
		
		when(userEventRepository.findAll()).thenReturn(expectedUserEvents);
		when(userEventRepository.save(any())).thenReturn(expectedUserEvent);
		when(userEventRepository.findById(any())).thenReturn(Optional.of(expectedUserEvent));
		when(userRepository.existsById(any())).thenReturn(true);
		when(eventRepository.existsById(any())).thenReturn(true);
		when(userRepository.findById(any())).thenReturn(Optional.of(expectedUser));
		when(masteryRepository.findMasteryByPoints(anyInt())).thenReturn(listOfMasteries);
	}
	
	@Test
	public void testGetAllUserEvents() {
		Iterable<UserEvent> actualReturn = testUserEventController.getAllUserEvents();
		
		assertEquals(actualReturn, expectedUserEvents);
	}
	
	@Test
	public void getUsersByEventEventNotFound() {
		when(eventRepository.existsById(any())).thenReturn(false);
		
		expectedEx.expect(ResourceNotFoundException.class);
		expectedEx.expectMessage("Event not found by Id");
		
		testUserEventController.getUsersByEvent(4);
	}
	
	@Test
	public void getUsersByEventTest() {
		ArrayList<Integer> listOfIds = new ArrayList<>();
		listOfIds.add(1);
		listOfIds.add(2);
		User expectedUser = new User();
		ArrayList<User> expectedResult = new ArrayList<>();
		expectedResult.add(expectedUser);
		expectedResult.add(expectedUser);
		
		when(userEventRepository.findUsersByEventId(any())).thenReturn(listOfIds);
		when(userRepository.findById(any())).thenReturn(Optional.of(expectedUser));
		
		Iterable<User> actualResult = testUserEventController.getUsersByEvent(3);
		
		assertEquals(actualResult, expectedResult);
	}
	
	@Test
	public void testAdd() {
		expectedUserEvent.setIsAdmin(true);
		UserEvent actualResult = testUserEventController.addUserEvent(1, 2, true);
		assertTrue(actualResult.getIsAdmin());
		assertEquals(actualResult, expectedUserEvent);
		
		int initialPoints = expectedUser.getPoints();
		int registerPoints = expectedMastery.getRegisterPoints();
		int adminPoints = expectedMastery.getCreateEventPoints();
		
		int newPoints = initialPoints + adminPoints;
		expectedUser.setPoints(newPoints);
		verify(userRepository, atLeastOnce()).save(expectedUser);
		
		newPoints += registerPoints;
		expectedUser.setPoints(newPoints);
		
		verify(userRepository, atLeastOnce()).save(expectedUser);
	}
	
	@Test
	public void testCheckParamsNullUser() {
		expectedEx.expect(BadRequestException.class);
		expectedEx.expectMessage("User id is null");
		
		testUserEventController.getUserEvent(null, expectedEventId);
	}
	
	@Test
	public void testCheckParamsNullEvent() {
		expectedEx.expect(BadRequestException.class);
		expectedEx.expectMessage("Event id is null");
		
		testUserEventController.getUserEvent(expectedUserId, null);
	}
	
	@Test
	public void testCheckParamsNotFoundUser() {
		when(userRepository.existsById(any())).thenReturn(false);
		expectedEx.expect(ResourceNotFoundException.class);
		expectedEx.expectMessage("User not found by id");
		
		testUserEventController.addUserEvent(expectedUserId, expectedEventId, false);
	}
	
	@Test
	public void testCheckParamsNotFoundEvent() {
		when(eventRepository.existsById(any())).thenReturn(false);
		expectedEx.expect(ResourceNotFoundException.class);
		expectedEx.expectMessage("Event not found by id");
		
		testUserEventController.addUserEvent(expectedUserId, expectedEventId, false);
	}
	
	@Test
	public void testGet() {
		UserEvent actualResult = testUserEventController.getUserEvent(expectedUserId, expectedEventId);
		
		assertEquals(actualResult, expectedUserEvent);
	}
	
	@Test
	public void testSave() {
		UserEvent modifiedEvent = new UserEvent(expectedUserId, expectedEventId);
		modifiedEvent.setCheckedIn(true);
		modifiedEvent.setIsAdmin(true);
		UserEvent updatedEvent = testUserEventController.saveUserEvent(modifiedEvent, modifiedEvent.getEventId());
		assertTrue(updatedEvent.getIsCheckedIn());
		assertTrue(updatedEvent.getIsAdmin());
		
		int initialPoints = expectedUser.getPoints();
		int isCheckedInPoints = expectedMastery.getCheckInPoints();
		
		int newPoints = initialPoints + isCheckedInPoints;
		expectedUser.setPoints(newPoints);
		verify(userRepository, atLeastOnce()).save(expectedUser);
	}
}
