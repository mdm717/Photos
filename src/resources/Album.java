package resources;

import java.io.Serializable;

public class Album implements Serializable{
	/*public static String albumName;
	
	public Album(String str) {
		albumName = str;
	}*/
	
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