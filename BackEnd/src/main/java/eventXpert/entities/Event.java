package eventXpert.entities;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import javax.persistence.*;

/**
 * Represents an Event
 */
@Entity
public class Event {
	@Id
	@Column(name = "event_id")
	@GeneratedValue(
			strategy = GenerationType.AUTO,
			generator = "native"
	)
	@GenericGenerator(
			name = "native",
			strategy = "native"
	)
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name = "location")
	private String location;
	@Column(name = "description")
	private String description;
	@Column(name = "start_date_time")
	private Date startDateTime;
	@Column(name = "end_date_time")
	private Date endDateTime;
	
	/**
	 * Default Constructor for Event
	 */
	public Event() {}
	
	/**
	 * Constructor for event
	 *
	 * @param name          the name of the event
	 * @param location      the location of the event
	 * @param description   the description of the event
	 * @param startDateTime the start date and time for the event
	 * @param endDateTime   the end date and time for the event
	 */
	public Event(String name, String location, String description, Date startDateTime, Date endDateTime) {
		this.name = name;
		this.location = location;
		this.description = description;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}
	
	/**
	 * Get id of event
	 *
	 * @return int id
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * Get name of event
	 *
	 * @return string name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set name of event
	 *
	 * @param name string new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get location of event
	 *
	 * @return string location
	 */
	public String getLocation() {
		return location;
	}
	
	/**
	 * Set location of event
	 *
	 * @param location string new location
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	/**
	 * Get event description
	 *
	 * @return string description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Set description of event
	 *
	 * @param description string new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Get start date and time
	 *
	 * @return Date object start date time
	 */
	public Date getStartDateTime() {
		return startDateTime;
	}
	
	/**
	 * Set start date time
	 *
	 * @param startDateTime Date new start date time
	 */
	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}
	
	/**
	 * Get end date and time
	 *
	 * @return Date object end date time
	 */
	public Date getEndDateTime() {
		return endDateTime;
	}
	
	/**
	 * Set end date time
	 *
	 * @param endDateTime Date new end date time
	 */
	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}
}
