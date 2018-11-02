/* 
 * ListController Class
 * 
 * 		This class creates the main UI for the user to add, edit, and
 * 		delete songs from a list
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

public class AdminController extends Application {
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
		primaryStage.setTitle("Song Library");
		primaryStage.setResizable(false);
		try {
			Parent root= FXMLLoader.load(getClass().getResource("AdminSub.fxml"));
			scene = new Scene(root);
			listView = (ListView<User>) scene.lookup("#listView");
			listView.setItems(data);
			name = (TextField) scene.lookup("#UserIn");
			
			sort();
			
			primaryStage.setOnCloseRequest(e -> {
		        Platform.exit();
		        System.exit(0);
		    });
			
			try {System.out.println("DDD");
			
			String file = "src/resources/Users.txt";

            BufferedReader br = new BufferedReader(new FileReader(file));System.out.println("FFF");
        	String line = br.readLine();
        	System.out.println(line);
            while (line != null) {
            	data.add(new User(line));
            	line = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        }
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mainStage= primaryStage;
/* (data.isEmpty()) {
			count = 0;
		} else if (count == 1) {
		}
		
		handler(action);
		
//		listView.getSelectionModel().selectedIndexProperty().addListener( (obs, oldVal, newVal) -> showInfo(newVal));
*/	}
	
	public void handler(int a) {
		switch (a) {
			case -1:
				break;
			case 0:
				listView.getSelectionModel().select(data.size()-1);
				showInfo(data.size()-1);
				sort();
				action=-1;
				break;
			case 1:
				if (index==-1) {index++;}
				showInfo(data.size()-1);
				sort();
				action=-1;
				break;	
			case 2:
	
				listView.getSelectionModel().select(index-1);
				showInfo(data.size()-1);
				sort();
				action=-1;
				break;
			default:
				break;
		}
	}
	
	@FXML
	private void add(ActionEvent e) {
		action=0;
		Text text = (Text) scene.lookup("#op");
		text.setText("Enter Information For New Song");
		
		clear();
	}
	
	@FXML
	private void logOut(ActionEvent e) {
		try {
			(new LoginView()).start(mainStage);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	@FXML
	private void edit(ActionEvent e) {
		if (!(data.isEmpty())) {
			action=1;
			Text text = (Text) scene.lookup("#op");
			text.setText("Change Information For Editted Song");
			clear();
			index = listView.getSelectionModel().getSelectedIndex();
			name.setText(data.get(index).getName());
		}
	}
	
	@FXML
	private void delete(ActionEvent e) {
		if (!(data.isEmpty())) {
			action=2;
			data.remove(listView.getSelectionModel().getSelectedIndex());
			
		}
	}
	
	@FXML
	private void confrim(ActionEvent e) {
		User s =new User(name.getText());
		switch (action) {
		case -1:
			break;
		case 0:
			boolean failed=false;
			boolean newSong=true;
			
			for (int i = 0; i < data.size(); i++) {
				if (s.equals(data.get(i))) {		
					//If either the song title or artist is missing, do nothing
					newSong=false;
				}
			}
			if ( s.getName().equals("")) {
				missingInfo(mainStage);
			}
			else if (newSong) {//if at least title and artist are provided add the song to the end of the list
				data.add(s);
				index++;
			}
			else {
				showAlert(mainStage);
			}
			listView.getSelectionModel().select(data.size()-1);
			showInfo(data.size()-1);
			sort();
			if (!failed) {
				action=-1;

				Text text = (Text) scene.lookup("#op");
				text.setText("Please Select An Operation");
			}
			break;
		case 1:
			newSong=true;
			failed=false;
			
			for (int i = 0; i < data.size(); i++) {
				if (s.equals(data.get(i))) {		
					//If either the song title or artist is missing, do nothing
					newSong=false;
				}
			}
			if (index==-1) {index++;}
			else {
				index = listView.getSelectionModel().getSelectedIndex();
			}
			for (int i = 0; i < data.size(); i++) {
				if (s.equals(data.get(i)) && i != index) {		
					//If either the song title or artist is missing, do nothing
					newSong=false;
				}
			}
			if (s.getName().equals(null)) {
				missingInfo(mainStage);
				failed=true;
				
			}
			else if (newSong) {//if at least title and artist are provided add the song to the end of the list
				data.remove(index);
				data.add(new User(name.getText()));
				listView.getSelectionModel().select(data.size()-1);
			}
			else {
				showAlert(mainStage);
				failed=true;
			}
			
			sort();
			if (!failed) {
				action=-1;

				Text text = (Text) scene.lookup("#op");
				text.setText("Please Select An Operation");
			}
			break;
		default:
			break;
		}
	}
	
	public static void showInfo(Number newVal) {
		if (!(data.isEmpty())) {
			name.setText(data.get((int) newVal).getName());
		}
	}
	
	private static void sort() {
		
		 for (int i = 0; i < data.size(); i++) 
	        {
	            for (int j = i + 1; j < data.size(); j++) 
	            {
	                if (data.get(i).toString().compareTo(data.get(j).toString())>0) 
	                {
	                    User s = new User(data.get(i).getName());
	                    data.set(i, data.get(j));
	                    data.set(j, s);
	                }
	            }
	        }
	}
	
	private static void clear() {
		name.setText("");
	}

	private static void missingInfo(Stage mainStage) {
		 Alert message = new Alert(AlertType.INFORMATION);
		 message.initOwner(mainStage);
		 message.setTitle("List Item");
		 message.setHeaderText("Missing Info");
		 String content = "Please Add A Valid Name"; 
		 message.setContentText(content);
		 message.showAndWait();
	} 
	private static void showAlert(Stage mainStage) {
		 Alert message = new Alert(AlertType.INFORMATION);
		 message.initOwner(mainStage);
		 message.setTitle("List Item");
		 message.setHeaderText("Song Already Exists");
		 String content = "THIS NAME IS ALREADY TAKEN."; 
		 message.setContentText(content);
		 message.showAndWait();
	} 
}
