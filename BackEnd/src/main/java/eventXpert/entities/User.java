package eventXpert.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Represents a User
 */
@Entity
@Table(name = "user")
public class User {
	@Id
	@Column(name = "user_id")
	@GeneratedValue(
			strategy = GenerationType.AUTO,
			generator = "native"
	)
	@GenericGenerator(
			name = "native",
			strategy = "native"
	)
	private Integer id;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "email")
	private String email;
	@Column(name = "profile_file_name")
	private String profileFileName;
	@Column(name = "points")
	private int points;
	
	/**
	 * Default constructor
	 */
	public User() {}
	
	/**
	 * Constructor for User
	 *
	 * @param firstName first name of user
	 * @param lastName  last name of user
	 * @param email     email of user
	 */
	public User(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.profileFileName = "cat_avatar.png";
		this.points = 0;
	}
	
	/**
	 * Return's user's id
	 *
	 * @return user's id
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * Returns user's first name
	 *
	 * @return first name of user
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Change first name of user
	 *
	 * @param firstName to change to
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Returns user's last name
	 *
	 * @return last name of user
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Change last name of user
	 *
	 * @param lastName to change to
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Return's user's email
	 *
	 * @return email of user
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Returns the location of the user's profile picture on the server
	 *
	 * @return string of picture location
	 */
	public String getProfileFileName() {
		return profileFileName;
	}
	
	/**
	 * Change the location of the user's profile picture on the server
	 *
	 * @param profileFileName to update to
	 */
	public void setProfileFileName(String profileFileName) {
		this.profileFileName = profileFileName;
	}
	
	/**
	 * Returns the number of points the user currently has
	 *
	 * @return the user's points
	 */
	public int getPoints() {
		return points;
	}
	
	/**
	 * Sets the points for the current user
	 *
	 * @param points the new number of points for the user
	 */
	public void setPoints(int points) {
		this.points = points;
	}
}
