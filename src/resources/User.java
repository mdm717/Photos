package resources;

import java.io.Serializable;

public class User implements Serializable{
	private String name;
	
	public User(String nm) {
		name = nm;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
	
}
