package eventXpert.controllers;

import eventXpert.entities.Mastery;
import eventXpert.errors.ResourceNotFoundException;
import eventXpert.repositories.MasteryRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MasteryControllerTest {
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	@InjectMocks
	private MasteryController testMasteryController = new MasteryController();
	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private MasteryRepository masteryRepository;
	private Iterable<Mastery> expectedMasteries;
	private Iterable<Mastery> expectedMasteriesByPoints;
	private Mastery expectedMastery;
	
	@Before
	public void setup() {
		ArrayList<Mastery> listOfMasteries = new ArrayList<>();
		
		expectedMastery = new Mastery();
		expectedMasteriesByPoints = listOfMasteries;
		
		listOfMasteries.add(expectedMastery);
		expectedMasteries = listOfMasteries;
		
		when(masteryRepository.findAll()).thenReturn(expectedMasteries);
		when(masteryRepository.findMasteryByPoints(anyInt())).thenReturn(expectedMasteriesByPoints);
		when(masteryRepository.findById(any())).thenReturn(Optional.of(expectedMastery));
		when(masteryRepository.existsById(any())).thenReturn(true);
	}
	
	@Test
	public void getAllMasteriesTest() {
		Iterable<Mastery> actualReturn = testMasteryController.getAllMasteries(null);
		assertEquals(expectedMasteries, actualReturn);
	}
	
	@Test
	public void getMasteriesByPoints() {
		Iterable<Mastery> actualReturn = testMasteryController.getAllMasteries("12");
		assertEquals(expectedMasteriesByPoints, actualReturn);
	}
	
	@Test
	public void getMasteryById() {
		Mastery actualReturn = testMasteryController.getMastery(12);
		assertEquals(expectedMastery, actualReturn);
	}
	
	@Test
	public void getMasteryBadId() {
		when(masteryRepository.existsById(any())).thenReturn(false);
		expectedEx.expect(ResourceNotFoundException.class);
		
		testMasteryController.getMastery(13);
	}
}
