package Controllers;

import java.awt.Button;
import java.io.IOException;

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

public class LoginView extends Application{

	private static Stage mainStage;
	private static Scene scene;
	private static TextField tf;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Log In");
		primaryStage.setResizable(false);
		try {
			Parent root= FXMLLoader.load(getClass().getResource("Login.fxml"));
			scene = new Scene(root);
			
			tf = (TextField) scene.lookup("#text");
			
			primaryStage.setOnCloseRequest(e -> {
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
	
	public static String getText() {
		return tf.getText();
	}
	
	public static void prompt(String str) {
		tf.setText(null);
		tf.setPromptText(str);
	}
	
	public static Stage getMainStage() {
		return mainStage;
	}
}
