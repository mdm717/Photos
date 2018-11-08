package resources;

import java.io.Serializable;
import java.util.Date;

public class Photo implements Serializable {
	private Date date;
	private TagType tags; // 2D linked list, first linked list is type of tag, tags.next => the next type, tags.data => linked list of tags
	private String url;
	private String caption;
	
	public Photo(String u) {url=u;}
	public Photo(String u, Date d) {url=u;date=d;}
	public Photo(String u, String s) {url=u;caption=s;}
	public Photo(String u, String s, Date d) {url=u;date=d;caption=s;}
	
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public TagType getTags() {
		return tags;
	}
	public void setTags(TagType tags) {
		this.tags = tags;
	}
	
	public void addTag(String type, String data) {
		TagType ptr = tags;
		
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
	

}
