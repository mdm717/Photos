package resources;

import java.io.Serializable;
import resources.PhotoNode;

public class Album implements Serializable{
	
	
	private String albumName;
	
	public Album() {}
	
	public Album(String albumName) {
		this.albumName = albumName;
	}
	
	public String getAlbumName() {return albumName;}
	public void setAlbumName(String albumName) {this.albumName = albumName;}
	
	public String toString() {
		return albumName;
	}
}