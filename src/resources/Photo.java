package resources;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

/**
 * Photo.java - This class defines the Photo object 
 * @author Craig Sirota cms631
 * @author Matthew Marrazzo mdm289
 */

public class Photo implements Serializable {
	private Date date;
	private TagType tags; // 2D linked list, first linked list is type of tag, tags.next => the next type, tags.data => linked list of tags
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
	 * Sets the tags of the photo object
	 * @param tags		the tags associated with the photo
	 */
	public void setTags(TagType tags) {
		this.tags = tags;
	}
	
	/**
	 * Adds tags input by the user to the photo object
	 * @param type		the category of tag
	 * @param data		the actual tag
	 */
	
	public void addTag(String type, String data) {
		TagType ptr;
		if (tags == null) {
			ptr = new TagType(null, null, null);
		} else {
			ptr = tags;
		}
		
		while(ptr.next!=null) {
			if (ptr.getType().equals(type)) {
				Tag t = ptr.data;
				if (t == null) {
					t = new Tag(data,null);
					return;
				}
				while (t.next!=null) {
					if (t.getData().compareTo(data)==0) { //Tag already exists
						return;
					} else if (t.getData().compareTo(data)<0) {
						if (t.next!=null) {
							if (t.next.getData().compareTo(data)>0) {
								t.next= new Tag(data, t.next);
								return;
							}
						} else {
							t.next= new Tag(data, null);
							return;
						}
					} else if (t.getData().compareTo(data)>0) {
						t= new Tag(data, t);
						return;
					}
					t=t.next;
				}
				t.next= new Tag(data, null);
				return;
			}
			
			ptr=ptr.next;
		}
		ptr.next=new TagType(type, new Tag(data, null), null);
		return;
	}
	
	/**
	 * Deletes a specified tag attached to the photo object
	 * @param type		the category of the tag
	 * @param data		the actual tag
	 * @return		boolean value whether tag is deleted or not
	 */
	
	public boolean deleteTag(String type, String data) {
		TagType ptr = tags;
		Tag t;
		
		while (ptr.next!=null) {
			t = ptr.getData();
			while (ptr.getType().equals(type) && t.next!=null) {
				if (t.next.getData().equals(data)) {
					t.next = t.next.next;
					return true;
				}
				t=t.next;
			}
			ptr=ptr.next;
		}
		return false;
	}
	
	/**
	 * Searches the tags associated with photo objects
	 * @param type		the category of the tag
	 * @param data		the actual tag
	 * @return	the found tag being searched for
	 */

	public Tag searchTag(String type, String data) {
		TagType ptr = tags;
		Tag t;
		
		while (ptr.next!=null) {
			t = ptr.getData();
			while (ptr.getType().equals(type) && t.next!=null) {
				if (t.next.getData().equals(data)) {
					return t.next;
				}
				t=t.next;
			}
			ptr=ptr.next;
		}
		return null;
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
	
	/**
	 * Gets the tags stored in the photo object
	 * @return l		an ObseravableList of tags associated with the photo object
	 */
	public ObservableList<Tag> getTags(){
		ObservableList<Tag> l = FXCollections.observableArrayList();
		
		TagType ptr;
		if (tags == null) {
			ptr = new TagType(null, null, null);
		} else {
			ptr = tags;
		}
		
		Tag t;
		
		while (ptr.next!=null) {
			t = ptr.getData();
			while (t.next!=null) {
				l.add(t);
				t=t.next;
			}
			ptr=ptr.next;
		}
		return l;
	}
	

}
