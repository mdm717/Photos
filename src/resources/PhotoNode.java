package resources;

/**
 * PhotoNode.java - This class creates a linked list of photos
 * @author Craig Sitora cms631
 * @author Matthew Marrazzo mdm289
 */

public class PhotoNode {
	public Photo data;
	public Photo next;
	public Photo prev;
	
	/**
	 * This method creates the first photo node in the linked list
	 * @param data		the photo object
	 */
	
	public PhotoNode(Photo data) {
		this.data=data;
		next=null;
	}
	
	/**
	 * This method adds a new photo into a currently existing linked list
	 * @param data		the photo object
	 * @param prev		previous photo object in the linked list
	 * @param next		next photo object in the linked list
	 */
	
	public PhotoNode(Photo data, Photo prev, Photo next) {
		this.data=data;
		this.next=next;
	}
}
