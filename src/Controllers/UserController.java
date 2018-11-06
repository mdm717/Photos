/* 
 * AdminController Class
 * 
 * 		This class creates the main UI for the user to add, edit, and
 * 		delete Users from a list
 * 
 */
package Controllers;
	
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

import com.sun.glass.events.WindowEvent;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import resources.User;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class UserController extends Application {
	public static final ObservableList<User> data = FXCollections.observableArrayList();
	private static ListView<User> listView;
	private static Stage mainStage;
	private static Scene scene;

	private static TextField name;

	private static int count; //counts items in list
	private static int action = -1;
	private static int index = -1;
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Photo Library");
		primaryStage.setResizable(false);
		try {
			Parent root= FXMLLoader.load(getClass().getResource("PhotoLibSub.fxml"));
			scene = new Scene(root);
			
			primaryStage.setOnCloseRequest(e -> {
		        Platform.exit();
		        System.exit(0);
		    });
			
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
		primaryStage.setScene(scene);
		primaryStage.show();
		mainStage= primaryStage;
	}
}
