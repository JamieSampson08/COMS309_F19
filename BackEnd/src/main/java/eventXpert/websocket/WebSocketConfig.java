package eventXpert.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * Configuration for WebSocket
 */
@Configuration
public class WebSocketConfig {
	/**
	 * Enables the server to have endpoints that a client can hit
	 *
	 * @return ServerEndpointExporter
	 */
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
}

