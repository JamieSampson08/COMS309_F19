package eventXpert.controllers;

import eventXpert.entities.User;
import eventXpert.errors.ResourceNotFoundException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Test for UserController
 */
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	@InjectMocks
	private UserController testUserController = new UserController();
	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private UserRepository userRepository;
	private User givenUser;
	private User returnedUser;
	private ArrayList<User> listOfUsers;
	private Integer expectedId;
	
	@Before
	public void setup() {
		expectedId = 123456;
		givenUser = new User("givenFirstName", "givenLastName", "givenEmail");
		returnedUser = new User("returnedFirstName", "returnedLastName", "returnedEmail");
		listOfUsers = new ArrayList<>();
		listOfUsers.add(givenUser);
		listOfUsers.add(returnedUser);
		
		when(userRepository.findAll()).thenReturn(listOfUsers);
		when(userRepository.findById(any())).thenReturn(Optional.of(returnedUser));
		when(userRepository.save(any())).thenReturn(returnedUser);
	}
	
	@Test
	public void testGetAllUsers() {
		Iterable<User> actualResponse = testUserController.getAllUsers(null);
		
		assertEquals(listOfUsers, actualResponse);
	}
	
	@Test
	public void testGetById() {
		when(userRepository.existsById(any())).thenReturn(true);
		
		User actualResponse = testUserController.getUserById(expectedId);
		
		assertEquals(returnedUser, actualResponse);
	}
	
	@Test
	public void testInvalidId() {
		when(userRepository.existsById(any())).thenReturn(false);
		expectedEx.expect(ResourceNotFoundException.class);
		testUserController.getUserById(expectedId);
	}
	
	@Test
	public void testAddUser() {
		User actualResponse = testUserController.addUser(givenUser);
		
		assertEquals(returnedUser, actualResponse);
	}
	
	@Test
	public void testDeleteUser() {
		when(userRepository.existsById(any())).thenReturn(true);
		
		String actualResponse = testUserController.deleteUser(expectedId);
		
		assertEquals("Successfully deleted user", actualResponse);
	}
	
	@Test
	public void testDeleteInvalidId() {
		when(userRepository.existsById(any())).thenReturn(false);
		expectedEx.expect(ResourceNotFoundException.class);
		testUserController.deleteUser(expectedId);
	}
	
	@Test
	public void testSave() {
		User savedUser = new User("savedFirstName", "givenLastName", "givenEmail");
		User givenUser = new User("newName", "givenLastName", "givenEmail");
		
		when(userRepository.findById(any())).thenReturn(Optional.of(savedUser));
		testUserController.addUser(savedUser);
		testUserController.saveUser(givenUser, givenUser.getId());
		
		assertEquals(savedUser.getFirstName(), "newName");
	}
}
