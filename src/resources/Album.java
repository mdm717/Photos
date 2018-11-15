package resources;

import java.io.Serializable;

public class Album implements Serializable{
	private String albumName;
	
	public Album(String str) {
		albumName = str;
	}
	
	public String toString() {
		return albumName;
	}
}