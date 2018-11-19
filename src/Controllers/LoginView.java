package Controllers;

import java.awt.Button;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * LoginView.java - This class loads the 
 * @author Craig Sirota cms631
 * @author Matthew Marrazzo mdm289
 */


public class LoginView extends Application implements Serializable{

	private static Stage mainStage;
	private static Scene scene;
	private static TextField tf;

	public static final String storeDir = "src/resources";
	public static final String storeFile = "Users.dat";
	public static AdminController ac;
	
	
	/**
	 * 
	 * @param primaryStage
	 * @throws Exception
	 */
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Log In");
		primaryStage.setResizable(false);

		try {
			AdminController.readApp();
		} catch (ClassNotFoundException e2) {
			 //TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (Exception e2) {

			e2.printStackTrace();
		}
		
		try {
			Parent root= FXMLLoader.load(getClass().getResource("Login.fxml"));
			scene = new Scene(root);
			
			tf = (TextField) scene.lookup("#text");
			
			primaryStage.setOnCloseRequest(e -> {

				try {
					AdminController.writeApp(ac);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        Platform.exit();
		        System.exit(0);
		    });
			primaryStage.setScene(scene);
			primaryStage.show();
			mainStage=primaryStage;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 * @return
	 */
	
	public static String getText() {
		return tf.getText();
	}
	
	/**
	 * 
	 * @param str
	 */
	
	public static void prompt(String str) {
		tf.setText(null);
		tf.setPromptText(str);
	}
	
	/**
	 * 
	 * @return 
	 */
	
	public static Stage getMainStage() {
		return mainStage;
	}
}
