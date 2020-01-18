package eventXpert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import eventXpert.storage.StorageProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * This class is the git SpringBootApplication
 */
@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class Application {
	/**
	 * Runs the springboot application
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
