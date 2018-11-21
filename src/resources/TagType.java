package resources;

import java.io.Serializable;

/**
 * TagType.java - 
 * @author Craig Sirota cms631
 * @author Matthew Marrazzo mdm289
 */

public class TagType  implements Serializable{

	public String type;
	public Tag data;
	public TagType next;
	
	public TagType(String type, Tag data, TagType next ) {
		this.type=type;
		this.data=data;
		this.next=next;
	}

	public Tag getData() {
		return data;
	}

	public void setData(Tag data) {
		this.data = data;
	}

	public TagType getNext() {
		return next;
	}

	public void setNext(TagType next) {
		this.next = next;
	}

	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}
	
	public String toString() {
		return type;
	}
	
	
}
