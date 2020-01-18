package eventXpert.controllers;

import eventXpert.entities.Event;
import eventXpert.errors.BadRequestException;
import eventXpert.repositories.EventRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Tests for EventController
 */
@RunWith(MockitoJUnitRunner.class)
public class EventControllerTest {
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	@InjectMocks
	private EventController testEventController = new EventController();
	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private EventRepository eventRepository;
	private static final String LONG_STRING = "01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
	private static final String REALLY_LONG_STRING = "01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678900123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789001234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678900123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789001234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678900123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789001234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
	private Iterable<Event> expectedReturnList;
	private Iterable<Event> expectedSortedReturnList;
	private Iterable<Event> expectedNameReturnList;
	private Iterable<Event> expectedSortedNameReturnList;
	private Event expectedEvent;
	private Date expectedStartTime;
	private Date expectedEndTime;
	
	@Before
	public void setup() {
		ArrayList<Event> listOfEvents = new ArrayList<>();
		expectedNameReturnList = new ArrayList<>();
		expectedSortedNameReturnList = new ArrayList<>();
		expectedSortedReturnList = new ArrayList<>();
		expectedStartTime = new Date(1234);
		expectedEndTime = new Date(2345);
		expectedEvent = new Event("returnName", "returnLocation", "returnDescription", expectedStartTime, expectedEndTime);
		listOfEvents.add(expectedEvent);
		
		expectedReturnList = listOfEvents;
		when(eventRepository.findAll()).thenReturn(expectedReturnList);
		when(eventRepository.findAll(any(Sort.class))).thenReturn(expectedSortedReturnList);
		when(eventRepository.findByName(any())).thenReturn(expectedNameReturnList);
		when(eventRepository.findByNameAndSort(any(), any())).thenReturn(expectedSortedNameReturnList);
		when(eventRepository.save(any())).thenReturn(expectedEvent);
	}
	
	@Test
	public void getAllEventsTest() {
		Iterable<Event> actualReturn = testEventController.getAllEvents(null, null);
		assertEquals(expectedReturnList, actualReturn);
	}
	
	@Test
	public void getEventsByName() {
		Iterable<Event> actualReturn = testEventController.getAllEvents("givenName", null);
		assertEquals(expectedNameReturnList, actualReturn);
	}
	
	@Test
	public void getAllEventsSortedTest() {
		Iterable<Event> actualReturn = testEventController.getAllEvents(null, "sortParam");
		assertEquals(expectedSortedReturnList, actualReturn);
	}
	
	@Test
	public void getAllEventsByNameSortedTest() {
		Iterable<Event> actualReturn = testEventController.getAllEvents("givenName", "givenSort");
		assertEquals(expectedSortedNameReturnList, actualReturn);
	}
	
	@Test
	public void addEventNoStartDateTime() {
		Event givenEvent = new Event("expectedName", "expectedLocation", "description", null, expectedEndTime);
		expectedEx.expect(BadRequestException.class);
		expectedEx.expectMessage("You must insert a start and end time");
		
		testEventController.addEvent(givenEvent);
	}
	
	@Test
	public void addEventNoEndDateTime() {
		Event givenEvent = new Event("expectedName", "expectedLocation", "description", expectedStartTime, null);
		expectedEx.expect(BadRequestException.class);
		expectedEx.expectMessage("You must insert a start and end time");
		
		testEventController.addEvent(givenEvent);
	}
	
	@Test
	public void addEventEndBeforeStart() {
		Event givenEvent = new Event("expectedName", "expectedLocation", "description", expectedEndTime, expectedStartTime);
		expectedEx.expect(BadRequestException.class);
		expectedEx.expectMessage("Your start time can not be after your end time");
		
		testEventController.addEvent(givenEvent);
	}
	
	@Test
	public void addEventNoName() {
		Event givenEvent = new Event(null, "expectedLocation", "description", expectedStartTime, expectedEndTime);
		expectedEx.expect(BadRequestException.class);
		expectedEx.expectMessage("You must have a name with a length of less than 100 characters");
		
		testEventController.addEvent(givenEvent);
	}
	
	@Test
	public void addEventNameTooLong() {
		Event givenEvent = new Event(LONG_STRING, "expectedLocation", "description", expectedStartTime, expectedEndTime);
		expectedEx.expect(BadRequestException.class);
		expectedEx.expectMessage("You must have a name with a length of less than 100 characters");
		
		testEventController.addEvent(givenEvent);
	}
	
	@Test
	public void addEventNoLocation() {
		Event givenEvent = new Event("expectedName", null, "description", expectedStartTime, expectedEndTime);
		expectedEx.expect(BadRequestException.class);
		expectedEx.expectMessage("You must have a location with a length of less than 100 characters");
		
		testEventController.addEvent(givenEvent);
	}
	
	@Test
	public void addEventLocationTooLong() {
		Event givenEvent = new Event("expectedName", LONG_STRING, "description", expectedStartTime, expectedEndTime);
		expectedEx.expect(BadRequestException.class);
		expectedEx.expectMessage("You must have a location with a length of less than 100 characters");
		
		testEventController.addEvent(givenEvent);
	}
	
	@Test
	public void addEventNoDescription() {
		Event givenEvent = new Event("expectedName", "givenLocation", null, expectedStartTime, expectedEndTime);
		expectedEx.expect(BadRequestException.class);
		expectedEx.expectMessage("You must have a description with a length of less than 1000 characters");
		
		testEventController.addEvent(givenEvent);
	}
	
	@Test
	public void addEventDescriptionTooLong() {
		Event givenEvent = new Event("expectedName", "givenLocation", REALLY_LONG_STRING, expectedStartTime, expectedEndTime);
		expectedEx.expect(BadRequestException.class);
		expectedEx.expectMessage("You must have a description with a length of less than 1000 characters");
		
		testEventController.addEvent(givenEvent);
	}
	
	@Test
	public void addEventSuccessful() {
		Event givenEvent = new Event("expectedName", "expectedLocation", "expectedDescription", expectedStartTime, expectedEndTime);
		Event actualEvent = testEventController.addEvent(givenEvent);
		
		assertEquals(expectedEvent, actualEvent);
	}
	
	@Test
	public void saveEventSuccessful() {
		Event savedEvent = new Event("savedName", "expectedLocation", "expectedDescription", expectedStartTime, expectedEndTime);
		Event givenEvent = new Event("newName", "expectedLocation", "expectedDescription", expectedStartTime, expectedEndTime);
		when(eventRepository.findById(any())).thenReturn(Optional.of(savedEvent));
		
		testEventController.addEvent(savedEvent);
		testEventController.saveEvent(givenEvent, givenEvent.getId());
		
		assertEquals(savedEvent.getName(), "newName");
	}
}
