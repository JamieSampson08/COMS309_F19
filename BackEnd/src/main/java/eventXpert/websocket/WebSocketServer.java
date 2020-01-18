package eventXpert.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/websocket/{userId}")
@Component
public class WebSocketServer {
	private static Map<Session, Integer> sessionUserIdMap = new HashMap<>();
	private static Map<Integer, Session> userIdSessionMap = new HashMap<>();
	private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
	
	/**
	 * Function called when client connects to server
	 *
	 * @param session of client
	 * @param userId  of client
	 * @throws IOException handles issues getting/sending info
	 */
	@OnOpen
	public void onOpen(
			Session session,
			@PathParam("userId") Integer userId) throws IOException {
		
		logger.info("Entered into Open");
		if (sessionUserIdMap.containsValue(userId)) {
			String message = "User already connected";
			broadcast(message);
			// uncomment after user sessions are implemented
//            return;
		}
		
		sessionUserIdMap.put(session, userId);
		userIdSessionMap.put(userId, session);
		
		String message = "UserId " + userId + " has logged on.";
		broadcast(message);
	}
	
	/**
	 * Function called when recieving message from client
	 *
	 * @param session   of client
	 * @param eventName that was added
	 * @throws IOException
	 */
	@OnMessage
	public void onMessage(Session session, String eventName) throws IOException {
		// Handle new messages
		logger.info("Entered into Message: Got eventName:" + eventName);
		Integer userId = sessionUserIdMap.get(session);
		
		broadcast("Event `" + eventName + "` has been created by userId: " + userId);
	}
	
	/**
	 * Function that handles a client disconnecting from server
	 *
	 * @param session of client
	 * @throws IOException handles issues getting/sending info
	 */
	@OnClose
	public void onClose(Session session) throws IOException {
		logger.info("Entered into Close");
		
		Integer userId = sessionUserIdMap.get(session);
		sessionUserIdMap.remove(session);
		userIdSessionMap.remove(userId);
		
		String message = userId + " has logged off.";
		broadcast(message);
	}
	
	/**
	 * Function to handle errors
	 *
	 * @param session   of client
	 * @param throwable the error that occurred
	 */
	@OnError
	public void onError(Session session, Throwable throwable) {
		logger.info("An error occurred: " + Arrays.toString(throwable.getStackTrace()));
	}
	
	/**
	 * Sends message to all clients connected
	 *
	 * @param message to send to clients
	 * @throws IOException handles issues getting/sending info
	 */
	private static void broadcast(String message) throws IOException {
		sessionUserIdMap.forEach((session, username) -> {
			synchronized (session) {
				try {
					session.getBasicRemote().sendText(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
}

