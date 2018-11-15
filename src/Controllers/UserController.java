package Controllers;
	
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

import com.sun.glass.events.WindowEvent;

import resources.Album;

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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
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
	public static final ObservableList<Album> data = FXCollections.observableArrayList();
	private static ListView<Album> listView;
	
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
			
			listView = (ListView<Album>) scene.lookup("#list");
			listView.setItems(data);
	
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

	@FXML
	public void create(ActionEvent e) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Album Name Input");
		dialog.setHeaderText("Enter Album Name: ");
		dialog.showAndWait();
		if(dialog.getEditor().getText().equals(null)) {
			System.out.println("OH");
		}
		else {
			data.add(new Album(dialog.getEditor().getText().toString()));
			index++;
			//sort();
		}
		
	}
	
	public void delete(ActionEvent e) {
		int i = listView.getSelectionModel().getSelectedIndex();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm Delete");
		alert.setHeaderText("Really Delete This Album?");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			data.remove(listView.getSelectionModel().getSelectedIndex());
			try {
				listView.getSelectionModel().select(i-1);
			} catch(Exception e2) {}
		}
	}
	
	public void open(ActionEvent e) {
		
	}
	
	public void rename(ActionEvent e) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Rename Album");
		dialog.setHeaderText("New Album Name: ");
		Optional<String> result = dialog.showAndWait();
		if(result.isPresent()) {
			
		}
	}
	
}
