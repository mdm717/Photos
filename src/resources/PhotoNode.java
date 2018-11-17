package resources;

public class PhotoNode {
	public Photo data;
	public Photo next;
	public Photo prev;
	
	public PhotoNode(Photo data) {
		this.data=data;
		next=null;
	}
	public PhotoNode(Photo data, Photo prev, Photo next) {
		this.data=data;
		this.next=next;
	}
}
