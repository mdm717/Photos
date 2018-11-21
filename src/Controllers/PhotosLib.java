package Controllers;

import java.io.Serializable;

import javafx.application.Application;

/**
 * PhotosLib.java - This class starts the application
 * @author Craig Sirota cms631
 * @author Matthew Marrazzo mdm289
 *
 */

public class PhotosLib  implements Serializable{

	/**
	 * This method initially launches the application
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch(LoginView.class, args);
	}

}
