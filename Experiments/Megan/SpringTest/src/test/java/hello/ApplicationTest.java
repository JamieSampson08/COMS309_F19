package hello;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.times;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SpringApplication.class})
public class ApplicationTest {
	@Before
	public void setUp(){
		String[] expectedArgs = {};
		PowerMockito.mockStatic(SpringApplication.class);
		Application.main(expectedArgs);
	}
	@Test
	public void runCalled(){
		String[] expectedArgs = {};
		PowerMockito.verifyStatic(times(1));
		SpringApplication.run(Application.class, expectedArgs);
	}
}