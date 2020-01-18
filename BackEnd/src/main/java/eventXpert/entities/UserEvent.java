package eventXpert.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Represents the relationship between a User and a Event
 */
@Entity
@Table(name = "user_event")
public class UserEvent {
	@Id
	@Column(name = "user_event_id")
	@GeneratedValue(
			strategy = GenerationType.AUTO,
			generator = "native"
	)
	@GenericGenerator(
			name = "native",
			strategy = "native"
	)
	private Integer userEventId;
	@JoinColumn(name = "user_id")
	private Integer userId;
	@JoinColumn(name = "event_id")
	private Integer eventId;
	@Column(name = "is_checked_in")
	private boolean isCheckedIn;
	@Column(name = "is_admin")
	private boolean isAdmin;
	
	/**
	 * Default constructor
	 */
	public UserEvent() {}
	
	/**
	 * Constructor for user event relationship
	 *
	 * @param userId
	 * @param eventId
	 */
	public UserEvent(Integer userId, Integer eventId) {
		this.userId = userId;
		this.eventId = eventId;
		this.isAdmin = false;
		this.isCheckedIn = false;
	}
	
	/**
	 * Get userEventId
	 *
	 * @return int id
	 */
	public Integer getUserEventId() {
		return userEventId;
	}
	
	/**
	 * Get userId
	 *
	 * @return int id
	 */
	public Integer getUserId() {
		return userId;
	}
	
	/**
	 * Get eventId
	 *
	 * @return int id
	 */
	public Integer getEventId() {
		return eventId;
	}
	
	/**
	 * Get isAdmin
	 *
	 * @return boolean
	 */
	public boolean getIsAdmin() {
		return isAdmin;
	}
	
	/**
	 * Set isAdmin
	 *
	 * @param isAdmin boolean
	 */
	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	/**
	 * Return isCheckedIn
	 *
	 * @return boolean
	 */
	public boolean getIsCheckedIn() {
		return isCheckedIn;
	}
	
	/**
	 * Set isCheckIn
	 *
	 * @param isCheckedIn boolean
	 */
	public void setCheckedIn(boolean isCheckedIn) {
		this.isCheckedIn = isCheckedIn;
	}
}
