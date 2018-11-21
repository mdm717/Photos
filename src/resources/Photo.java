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
	
	
	public Photo(Image image, String url) { 
		//image = new Image(path.toURI().toString(), 100, 100, false, false);
		this.url = url;
		this.image = image;
	}
	public Photo(String url) {this.url=url;}
	public Photo(String url, Date date) {this.url=url;this.date=date;}
	public Photo(String url, String caption) {this.url=url;this.caption=caption;}
	public Photo(String url, String caption, Date date) {this.url=url;this.date=date;this.caption=caption;}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void setTags(TagType tags) {
		this.tags = tags;
	}
	
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
	
	public Image getImage() {
		return image;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
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
