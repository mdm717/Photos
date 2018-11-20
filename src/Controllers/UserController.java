package Controllers;
	
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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
	private static String file = "src/albums";
	private static String ext = ".dat";
	private static String separator = "/";

	private static int count; //counts items in list
	private static int action = -1;
	private static int index = -1;
	
	public static String albumName;
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Photo Library");
		primaryStage.setResizable(false);
		try {
			Parent root= FXMLLoader.load(getClass().getResource("PhotoLibSub.fxml"));
			scene = new Scene(root);
			
			File[] fileList = new File(file).listFiles(new FilenameFilter() {
			    public boolean accept(File dir, String name) {
			        return name.startsWith(LoginHandler.name) && name.endsWith(".dat");
			    }
			});
			
			data.clear();
			
			/*for (int i = 0; i < fileList.length; i++) {
				int j;
				for (j = fileList[i].toString().length()-1; fileList[i].toString().charAt(j) != '/'; j--);
				String newFile = fileList[i].toString().substring(j+1+LoginHandler.name.length()+1, fileList[i].toString().length()-4);
				System.out.println(newFile);
				data.add(new Album(newFile));
			}*/
			
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
		boolean exists = false;
		TextInputDialog dialog = new TextInputDialog();		
		dialog.setTitle("Album Name Input");
		dialog.setHeaderText("Enter Album Name: ");
		dialog.showAndWait();
		if(dialog.getEditor().getText().equals(null)) { }
		else {
			String name = dialog.getEditor().getText().toString();
			Album a = new Album(name);//dialog.getEditor().getText().toString());
			
			for(int i=0; i<data.size(); i++) {
				if(a.getAlbumName().equals(data.get(i).getAlbumName())) {
					exists = true;
					Alert error = new Alert(AlertType.INFORMATION);
					error.setTitle("Creation Error");
					error.setHeaderText(null);
					error.setContentText("Sorry, an Album with this Name Already Exists.  Please Choose a Different Name.");
					error.showAndWait();
				}
			}
			if(!exists) {

				try {
					if (name.contains("/")) {
						Alert error = new Alert(AlertType.INFORMATION);
						error.setTitle("Creation Error");
						error.setHeaderText(null);
						error.setContentText("Contains Illegal Character. Please Do Not Use '/' In The Album Name.");
						error.showAndWait();
					} else {
						(new File(file + separator + LoginHandler.name + "_" + name + ext)).createNewFile();
						data.add(new Album(name));
						index++;
					}
				} catch (IOException e1) {
					Alert error = new Alert(AlertType.INFORMATION);
					error.setTitle("Creation Error");
					error.setHeaderText(null);
					error.setContentText("Unexpected Error. Album Might Already Exist. Please Try A Different Name.");
					error.showAndWait();
				}
			}
		}
		
	}
	
	@FXML
	public void delete(ActionEvent e) {
		int i = listView.getSelectionModel().getSelectedIndex();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm Delete");
		alert.setHeaderText("Are You Sure You Wish to Delete This Album? All Pictures in the Album will be Lost.");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			String albumName = listView.getSelectionModel().getSelectedItem().toString();
			File delFile = new File (file + separator + LoginHandler.name + "_" + albumName + ext);
			delFile.delete();
			data.remove(listView.getSelectionModel().getSelectedIndex());
			try {
				listView.getSelectionModel().select(i-1);
			} catch(Exception e2) {}
		}
	}
	
	@FXML
	public void open(ActionEvent e) {
		try {
			//writeApp(this); 
			albumName = listView.getSelectionModel().getSelectedItem().getAlbumName();
			(new AlbumController()).start(mainStage);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	@FXML
	public void rename(ActionEvent e) {
		index = listView.getSelectionModel().getSelectedIndex();
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Rename Album");
		dialog.setHeaderText("New Album Name: ");
		dialog.showAndWait();
		
		if(dialog.getEditor().getText().equals(null)) { }
		else {
			
			if (dialog.getEditor().getText().contains("/")) {
				Alert error = new Alert(AlertType.INFORMATION);
				error.setTitle("Creation Error");
				error.setHeaderText(null);
				error.setContentText("Contains Illegal Character. Please Do Not Use '/' In The Album Name.");
				error.showAndWait();
			} else {
				boolean newFile = true;
				for (int i = 0; i < data.size(); i++) {
					if (data.get(i).toString().equals(dialog.getEditor().getText())) {
						newFile = false;
						break;
					}
				}
				
				if (newFile) {
					File of = new File(file + separator + LoginHandler.name + "_" + data.get(index).toString() + ext);
					File nf = new File(file + separator + LoginHandler.name + "_" + dialog.getEditor().getText() + ext);
					
					of.renameTo(nf);
					
					data.set(index, new Album(dialog.getEditor().getText()));
				}
			}
		}
		
	}
	
	@FXML
	public void logout(ActionEvent e) {
		try {
			//writeApp(this); 
			(new LoginView()).start(mainStage);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
	}
	
	
}