package Controllers;
	
import java.io.*;
import java.nio.charset.Charset;
import java.util.Collection;
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

/**
 * AdminController.java - This class controls the Admin page
 * @author Craig Sirota cms631
 * @author Matthew Marrazzo mdm289
 */

public class AdminController extends Application implements Serializable {
	public static ObservableList<User> data = FXCollections.observableArrayList();
	private static ListView<User> listView;
	private static Stage mainStage;
	private static Scene scene;

	private static TextField name;

	private static int count; //counts items in list
	private static int action = -1;
	private static int index = -1;
	
	public static final String storeDir = "src/resources";
	public static final String storeFile = "Users.dat";
	
	public AdminController() {}
	public AdminController(ObservableList<User> d) {
		data = d;
	}
	
	/**
	 * This method loads the home page after the Admin logs in
	 * @param primaryStage		the scene to be loaded	 
	 */
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Users");
		primaryStage.setResizable(false);
		
		try {
			Parent root= FXMLLoader.load(getClass().getResource(File.separator + "Controllers" + File.separator + "AdminSub.fxml"));
			scene = new Scene(root);
			listView = (ListView<User>) scene.lookup("#listView");
			listView.setItems(data);
			name = (TextField) scene.lookup("#UserIn");

			try {
				AdminController.readApp();
			} catch (ClassNotFoundException e2) {
				 //TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (Exception e2) {

				e2.printStackTrace();
			}
			sort();
			
			primaryStage.setOnCloseRequest(e -> {
				
				try {
					writeApp(new AdminController(data));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				removeDups();
				
		        Platform.exit();
		        System.exit(0);
		    });
			
//			String file = "src/resources/Users.txt";
/*
            BufferedReader br = new BufferedReader(new FileReader(file));System.out.println("FFF");
        	String line = br.readLine();
        	System.out.println(line);
            while (line != null) {
            	data.add(new User(line));
            	line = br.readLine();
            }
            br.close();*/
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mainStage= primaryStage;
		if (data.isEmpty()) {
			count = 0;
		} else if (count == 1) {
		}
		
		handler(action);
		
//		listView.getSelectionModel().selectedIndexProperty().addListener( (obs, oldVal, newVal) -> showInfo(newVal));
	}
	
	/**
	 * This method writes a list of current users out to a file for later use
	 * @param ac
	 * @throws IOException
	 */
	
	public static void writeApp(AdminController ac) throws IOException {
			ObjectOutputStream oos = new ObjectOutputStream(
			new FileOutputStream(storeDir + File.separator + storeFile));
			oos.writeObject(ac.data.toArray(new User[0]));
	}
	
	/**
	 * This method reads the list of current users from a file
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	
	public static void readApp() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(
		new FileInputStream(storeDir + File.separator + storeFile));
		AdminController ac = new AdminController();
		User[] users = (User[]) ois.readObject();
		
		for (int i = 0; i < users.length; i++) {
			ac.data.add(users[i]);
		}
		
		sort();
		
		System.out.println(ac.toString());
	} 
	
	/**
	 * 
	 * @param a
	 */
	
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
	
	/**
	 * This method is called when the Add button is pressed to add a new user
	 * @param e		a button press
	 */
	
	@FXML
	private void add(ActionEvent e) {
		action=0;
		Text text = (Text) scene.lookup("#op");
		text.setText("Enter Information For New Name and Confirm");
	}
	
	/**
	 * This method is called when the Logout button is pressed to return to Login Page
	 * @param e		a button press
	 */
	@FXML
	private void logOut(ActionEvent e) {
		try {
			writeApp(this); 
			(new LoginView()).start(mainStage);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/**
	 * This method is called when the Edit button is pressed to edit an existing user
	 * @param e		a button press
	 */
	
	@FXML
	private void edit(ActionEvent e) {
		if (!(data.isEmpty())) {
			action=1;
			Text text = (Text) scene.lookup("#op");
			text.setText("Change Information For Edited Name and Confirm");
			index = listView.getSelectionModel().getSelectedIndex();
			name.setText(data.get(index).getName());
		}
	}
	
	/**
	 * This method is called when the Delete button is pressed to delete a user
	 * @param e		a button press
	 */
	@FXML
	private void delete(ActionEvent e) {
		if (!(data.isEmpty())) {
			action=2;

			Text text = (Text) scene.lookup("#op");
			index = listView.getSelectionModel().getSelectedIndex();
			String username = ((User)data.get(index)).getName();
			text.setText("Confirm To Delete " + username);
			
			name.setText(data.get(index).getName());
			String file = "src/albums";
			String ext = ".dat";
			String separator = File.separator;
			File[] fileList = new File(file).listFiles(new FilenameFilter() {
			    public boolean accept(File dir, String name) {
			        return name.startsWith(username) && name.endsWith(".dat");
			    }
			});
			for (File f:fileList) {
				f.delete();
			}
		}
	}
	
	/**
	 * This method is called when the Confirm button is pressed and executes the proper action
	 * number that corresponds to Add, Edit, or Delete
	 * @param e		a button press
	 */
	
	@FXML
	private void confrim(ActionEvent e) {
		User s =new User(name.getText());
		switch (action) {
		case -1:
			break;
		case 0:
			boolean failed=false;
			boolean newName=true;
			
			for (int i = 0; i < data.size(); i++) {
				if (s.getName().equals(((User)data.get(i)).getName())) {		
					newName=false;
				}
			}
			if ( s.getName().equals("")) {
				missingInfo(mainStage);
			}
			else if (newName) {
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
			newName=true;
			failed=false;
			
			for (int i = 0; i < data.size(); i++) {
				if (s.getName().equals(((User)data.get(i)).getName())) {		
					newName=false;
				}
			}
			if (index==-1) {index++;}
			else {
				index = listView.getSelectionModel().getSelectedIndex();
			}
			for (int i = 0; i < data.size(); i++) {
				if (s.equals(data.get(i)) && i != index) {		
					newName=false;
				}
			}
			if (s.getName().equals(null)) {
				missingInfo(mainStage);
				failed=true;
				
			}
			else if (newName) {
				data.remove(index);
				data.add(new User(name.getText()));
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
		case 2:
			int i = listView.getSelectionModel().getSelectedIndex();
			data.remove(listView.getSelectionModel().getSelectedIndex());
			try{
				listView.getSelectionModel().select(i-1);
			}catch(Exception e2) {}
			showInfo(data.size()-1);
			sort();
			Text text = (Text) scene.lookup("#op");
			text.setText("Please Select An Operation");
		default:
			break;
		}
		listView.getSelectionModel().selectFirst();
	}
	
	/**
	 * This method shows the names of each user
	 * @param newVal
	 */
	
	public static void showInfo(Number newVal) {
		if (!(data.isEmpty())) {
			name.setText(data.get((int) newVal).getName());
		}
	}
	
	
	/**
	 * This method sorts the listview to have the users in alphabetical order
	 */
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
		removeDups();
	}
	
	/**
	 * This method removes duplicate usernames which are created in the editing process
	 */
	
	private static void removeDups() {
		if(data.size()>1) {
			for (int i = 1; i < data.size();i++) {
				if (data.get(i).getName().equals(data.get(i-1).getName())) {
					data.remove(i);
					i--;
				}
			}
		}
	}
	
	/**
	 * This method clears the
	 */
	
	private static void clear() {
		name.setText("");
	}

	/**
	 * This method informs the Admin that they have invalid input when creating or editing a username
	 * @param mainStage
	 */
	private static void missingInfo(Stage mainStage) {
		 Alert message = new Alert(AlertType.INFORMATION);
		 message.initOwner(mainStage);
		 message.setTitle("List Item");
		 message.setHeaderText("Missing Info");
		 String content = "Please Add A Valid Name"; 
		 message.setContentText(content);
		 message.showAndWait();
	} 
	
	/**
	 * This method informs the Admin if they are trying to create a duplicate username
	 * @param mainStage
	 */
	private static void showAlert(Stage mainStage) {
		 Alert message = new Alert(AlertType.INFORMATION);
		 message.initOwner(mainStage);
		 message.setTitle("List Item");
		 message.setHeaderText("Name Already Exists");
		 String content = "THIS NAME IS ALREADY TAKEN."; 
		 message.setContentText(content);
		 message.showAndWait();
	} 
	
	/**
	 * This converts the usernames to string variables
	 * @return str		the string variable for the username
	 */
	
	public String toString() {
		String str = "names";
		
		for (int i = 0; i < data.size(); i++) {
			str += "\n"+data.get(i).getName();
		}
		
		return str;
	}
}