package eventXpert.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Represents a Mastery
 */
@Entity
@Table(name = "mastery")
public class Mastery {
	@Id
	@Column(name = "mastery_id")
	@GeneratedValue(
			strategy = GenerationType.AUTO,
			generator = "native"
	)
	@GenericGenerator(
			name = "native",
			strategy = "native"
	)
	private Integer id;
	@Column(name = "mastery_name")
	private String name;
	@Column(name = "points_needed")
	private int pointsNeeded;
	@Column(name = "max_points")
	private int maxPoints;
	@Column(name = "register_points")
	private int registerPoints;
	@Column(name = "check_in_points")
	private int checkInPoints;
	@Column(name = "create_event_points")
	private int createEventPoints;
	
	/**
	 * Default constructor
	 */
	public Mastery() {}
	
	public Mastery(int registerPoints, int checkInPoints, int createEventPoints) {
		this.registerPoints = registerPoints;
		this.checkInPoints = checkInPoints;
		this.createEventPoints = createEventPoints;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public int getPointsNeeded() {
		return pointsNeeded;
	}
	
	public int getMaxPoints() {
		return maxPoints;
	}
	
	public int getRegisterPoints() {
		return registerPoints;
	}
	
	public int getCheckInPoints() {
		return checkInPoints;
	}
	
	public int getCreateEventPoints() {
		return createEventPoints;
	}
}
