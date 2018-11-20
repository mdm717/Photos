package resources;

import java.io.Serializable;

/**
 * PhotoNode.java - This class creates a linked list of photos
 * @author Craig Sitora cms631
 * @author Matthew Marrazzo mdm289
 */

public class PhotoNode implements Serializable {
	public Photo data;
	public PhotoNode next;
	public PhotoNode prev;
	
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
	
	public PhotoNode(Photo data, PhotoNode prev, PhotoNode next) {
		this.data=data;
		this.next=next;
		this.prev=prev;
	}
	
	public boolean isEmpty() {
		if (data.equals(null)) {
			return true;
		}
		return false;
	}
	
	/*public void add(PhotoNode newNode) {
		PhotoNode ptr = this;
		while (ptr.next != null) {
			ptr = ptr.next;
		}
		ptr.next=newNode;
		ptr.next.prev=ptr;
	}*/
	
	public void delete() {
		if (prev == null && next == null) {
			data = null;
			return;
		} else if (prev == null) {
			data = next.data;
			next.next.prev = this;
			next = next.next;
			return;
		} else if (next == null) {
			data = null;
			prev.next = null;
			return;
		} else if (prev != null && next != null) {
			prev.next = next;
			next.prev = prev;
			return;
		}
	}
}
