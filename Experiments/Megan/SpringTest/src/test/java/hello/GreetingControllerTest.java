package hello;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;

//
public class GreetingControllerTest extends AbstractTest {
	@Before
	@Override
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void getAllGreetings() throws Exception {
		String uri = "/greeting";
		MvcResult mvcResult =
				mvc.perform(MockMvcRequestBuilders
						.get(uri)
						.accept(MediaType.APPLICATION_JSON_VALUE))
						.andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Greeting[] greetings = super.mapFromJson(content, Greeting[].class);
		for (int i = 0; i < 10; i++) {
			assertEquals(i + 1, greetings[i].getId());
		}
	}
	
	@Test
	public void getSpecificGreeting() throws Exception {
		String uri = "/greeting/1";
		MvcResult mvcResult =
				mvc.perform(MockMvcRequestBuilders
						.get(uri)
						.accept(MediaType.APPLICATION_JSON_VALUE))
						.andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Greeting greeting = super.mapFromJson(content, Greeting.class);
		assertEquals(1, greeting.getId());
		assertEquals("Hello, ONE!", greeting.getContent());
	}
	
	@Test
	public void notFoundForBadIdGet() throws Exception {
		String uri = "/greeting/7000";
		MvcResult mvcResult =
				mvc.perform(MockMvcRequestBuilders
						.get(uri)
						.accept(MediaType.APPLICATION_JSON_VALUE))
						.andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(404, status);
	}
	
	@Test
	public void notFoundForBadIdPut() throws Exception {
		String uri = "/greeting/7000";
		User user = new User("name", -1);
		MvcResult mvcResult =
				mvc.perform(MockMvcRequestBuilders
						.put(uri)
						.content(asJsonString(user))
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE))
						.andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(404, status);
	}
	
	@Test
	public void addNewUser() throws Exception {
		String uri = "/greeting";
		MvcResult mvcResult =
				mvc.perform(MockMvcRequestBuilders
						.post(uri)
						.content("{\"name\":\"addUser\"}")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE))
						.andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Greeting greeting = super.mapFromJson(content, Greeting.class);
		assertEquals("Hello, addUser!", greeting.getContent());
	}
	
	@Test
	public void updateUser() throws Exception {
		String uri = "/greeting/11";
		MvcResult mvcResult =
				mvc.perform(MockMvcRequestBuilders
						.put(uri)
						.content("{\"name\":\"updateUser\"}")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE))
						.andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Greeting greeting = super.mapFromJson(content, Greeting.class);
		assertEquals(11, greeting.getId());
		assertEquals("Hello, updateUser!", greeting.getContent());
	}
	
	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}