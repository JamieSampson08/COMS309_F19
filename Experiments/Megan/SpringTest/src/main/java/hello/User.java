package hello;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="test_id")
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	public User() {
		this.name = "";
		this.id = -1;
	}
	
	public User(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}