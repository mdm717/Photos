package resources;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import resources.PhotoNode;

/**
 * Album.java - This class defines the Album object
 * @author Craig Sirota cms631
 * @author Matthew Marrazzo mdm289
 */

public class Album implements Serializable{
	
	private String albumName;
	public ArrayList<Photo> list = new ArrayList<Photo>();
	
	public Album() {
		list.add(new Photo("test"));
	}
	
	/**
	 * This method sets the album objects equal to the parameter
	 * @param albumName		string variable of the album name
	 */
	public Album(String albumName) {
		this.albumName = albumName;
	}
	
	/**
	 * This method returns the name of the album
	 * @return albumName	string variable of the album name
	 */
	public String getAlbumName() {return albumName;}
	
	/**
	 * This method sets the name of the album
	 * @param albumName		string variable of the album name
	 */
	public void setAlbumName(String albumName) {this.albumName = albumName;}
	
	/**
	 * This method converts the albumName to a string
	 * @return albumName	the name of the album
	 */
	
	public String toString() {
		return albumName;
	}
}