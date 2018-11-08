package resources;

public class PhotoNode {
	public Photo data;
	public Photo next;
	
	public PhotoNode(Photo data) {
		this.data=data;
		next=null;
	}
	public PhotoNode(Photo data, Photo next) {
		this.data=data;
		this.next=next;
	}
}
