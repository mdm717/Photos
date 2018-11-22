package resources;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Photo.java - This class defines the Photo object 
 * @author Craig Sirota cms631
 * @author Matthew Marrazzo mdm289
 */

public class Photo implements Serializable {
	private Date date;
	public ArrayList<Tag> tags = new ArrayList<Tag>();
	private String url;
	private String caption;
	private Image image;
	
	
	/**
	 * 
	 * @param image		the image variable of the selected file
	 * @param url		the string path of the image
	 */
	
	public Photo(Image image, String url) { 
		//image = new Image(path.toURI().toString(), 100, 100, false, false);
		this.url = url;
		this.image = image;
	}
	public Photo(String url) {this.url=url;}
	public Photo(String url, Date date) {this.url=url;this.date=date;}
	public Photo(String url, String caption) {this.url=url;this.caption=caption;}
	public Photo(String url, String caption, Date date) {this.url=url;this.date=date;this.caption=caption;}
	
	
	/**
	 * Gets the date of the photo object
	 * @return date		the date the photo was last modified
	 */
	
	public Date getDate() {
		return date;
	}
	
	/**
	 * Sets the date of the photo object
	 * @param date		the date the photo was last modified
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * Adds tags input by the user to the photo object
	 * @param type		the category of tag
	 * @param data		the actual tag
	 */
	
	public void addTag(String type, String data) {
		tags.add(new Tag(type, data));
		return;
	}
	
	/**
	 * Deletes a specified tag attached to the photo object
	 * @param type		the category of the tag
	 * @param data		the actual tag
	 * @return		boolean value whether tag is deleted or not
	 */
	
	public boolean deleteTag(String type, String data) {
		for (int i = 0; i < tags.size(); i++) {
			if (tags.get(i).toString().equals(type+"="+data)) {
				tags.remove(i);
			}
		}
		return false;
	}
	
	/**
	 * Searches the tags associated with photo objects
	 * @param data		the actual tag
	 * @return	the true if the tag being searched for is found
	 */

	public boolean searchTag(String data) {
		for (int i = 0; i < tags.size(); i++) {
			if (tags.get(i).toString().contains(data)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets the image stored in the photo object
	 * @return image		the image of the photo object
	 */
	
	public Image getImage() {
		return image;
	}
	
	/**
	 * Gets the url stored in the photo object
	 * @return url		the url stored in the photo object
	 */
	
	public String getUrl() {
		return url;
	}
	
	/**
	 * Stores the url in the photo object
	 * @param url		the string variable url to be stored
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * Gets the caption stored in the photo object
	 * @return caption		the caption stored in the photo object
	 */
	public String getCaption() {
		return caption;
	}
	
	/**
	 * Stores the caption in the photo object
	 * @param caption		the string variable caption to be stored
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}
	

}
