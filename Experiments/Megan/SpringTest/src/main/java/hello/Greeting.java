package hello;

public class Greeting {
	private final long id;
	private final String content;
	private static final String template = "Hello, %s!";
	
	public Greeting() {
		this.id = -1;
		this.content = "";
	}
	
	public Greeting(User n) {
		this.id = n.getId();
		
		this.content = String.format(template, n.getName());
	}
	
	public Greeting(long id, String content) {
		this.id = id;
		this.content = content;
	}
	
	public long getId() {
		return id;
	}
	
	public String getContent() {
		return content;
	}
}