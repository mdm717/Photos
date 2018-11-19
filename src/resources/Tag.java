package resources;

/**
 * Tag.java - 
 * @author cms631
 * @author mdm289
 */

public class Tag {
	public Tag next;
	private String data;
	
	public Tag(String data, Tag next) {
		this.next=next;
		this.data=data;
	}
	
	public Tag() {}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public String toString() {
		return data;
	}
	
}
