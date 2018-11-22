package resources;

import java.io.Serializable;

/**
 * Tag.java - 
 * @author cms631
 * @author mdm289
 */

public class Tag implements Serializable{
	public String type;
	private String data;
	
	/**
	 * 
	 * @param type	the type of tag
	 * @param data	the actual tag
	 */
	public Tag(String type, String data) {
		this.type=type;
		this.data=data;
	}
	
	public Tag() {}

	public void setData(String data) {
		this.data = data;
	}
	
	public String toString() {
		return type+"="+data;
	}
	
}
