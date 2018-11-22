package Controllers;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.sun.glass.events.WindowEvent;

import resources.Album;
import resources.Photo;
import resources.PhotoNode;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import resources.User;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

/**
 * AlbumController.java - This class controls Photos in an Album
 * @author Matthew Marrazzo mdm289
 * @author Craig Sirota cms631
 */

public class AlbumControllerReal extends Application implements Serializable{

	
	private static Stage mainStage;
	private static Scene scene;
	public static ListView<ImageView> lv;
	
	private static ObservableList<ImageView> il = FXCollections.observableArrayList();
	
	private static int index = -1;
	
	private static ArrayList<Photo> list = new ArrayList<Photo>();
	
	private Desktop desktop = Desktop.getDesktop();

	public static String storeDir = "src/albums";
	public static String storeFile = LoginHandler.name + "_" + UserController.albumName + ".dat";
	
	private static Photo copy;
	
	
	public static Album album = new Album();
	public void start(Stage primaryStage) {
		il.clear();
		list.clear();
		album.list.clear();
		primaryStage.setTitle("Photo Library");
		primaryStage.setResizable(false);
		
		primaryStage.setOnCloseRequest(e -> {
			try {
				writeApp(this);
				il.clear();
				list.clear();
				album.list.clear();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("Write Failed");
				e1.printStackTrace();
			}
	        Platform.exit();
	        System.exit(0);
	    });
		
		try {
			Parent root= FXMLLoader.load(getClass().getResource("AlbumView.fxml"));
			scene = new Scene(root);
			lv = (ListView<ImageView>) scene.lookup("#listView");
			Button bu = (Button) scene.lookup("#paste");
			if (copy == null)
				bu.setVisible(false);
			lv.setItems(il);
			
			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			readApp();
			int size = album.list.size();
	//		System.out.println(album.toString());
			for (int i = 0; i < size; i++) {
				File path = new File(album.list.get(i).getUrl());
				if(path != null) {
					Photo picture = new Photo(path.toString());
					
					//list.add(picture);
					Image im = new Image(path.toURI().toString(), 100, 100, false, false);
					il.add(new ImageView(im));
				}
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
	//		e.printStackTrace();
		}

		
		
		primaryStage.setScene(scene);
		primaryStage.show();
		mainStage= primaryStage;
	}
	
	@FXML
	public void add(ActionEvent e) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select a photo");
			File path = fileChooser.showOpenDialog(mainStage);
			if(path != null) {
				index++;
				Date d = new Date(path.lastModified());
				Photo picture = new Photo(path.toString(),d);
				//list.add(picture);
				Image image = new Image(path.toURI().toString(), 100, 100, false, false);	
				il.add(new ImageView(image));
				album.list.add(picture);
			}
	}

	@FXML
	public void open (ActionEvent e) {
		try {
			writeApp(this);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        (new AlbumController()).start(mainStage);
	}
	
	@FXML
	public void back(ActionEvent e) {
		try {
			writeApp(this);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        (new UserController()).start(mainStage);
	}

	@FXML
	public void copy(ActionEvent e) {
		int i = lv.getSelectionModel().getSelectedIndex();
		copy = album.list.get(i);
		System.out.println("Copy");
		Button bu = (Button) scene.lookup("#paste");
		bu.setVisible(true);
	}
	
	@FXML
	public void move(ActionEvent e) {
		int i = lv.getSelectionModel().getSelectedIndex();
		copy = album.list.get(i);
		
		int index = lv.getSelectionModel().getSelectedIndex();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm Move");
		alert.setHeaderText("Are You Sure You Wish to Move This Image?\nMoving An Item Without Pasting Can Result In A Loss of Data\nOk Will Remove The Image And Allow You To Paste\nCancel Will Allow You To Paste Without Removing The Image From Its Original Album");
		Optional<ButtonType> con = alert.showAndWait();
		
		
		if(con.get() == ButtonType.OK) {
			album.list.remove(index);
			il.remove(index);
		}
		
		System.out.println("Move");
		Button bu = (Button) scene.lookup("#paste");
		bu.setVisible(true);
	}
	
	@FXML
	public void paste(ActionEvent e) {
		storeDir = "src/albums";
		storeFile = LoginHandler.name + "_" + UserController.albumName + ".dat";
		
		album.list.add(copy);
		Image image = new Image((new File(copy.getUrl())).toURI().toString(), 100, 100, false, false);
		il.add(new ImageView(image));
		System.out.println("Paste");
	}
	
	@FXML
	public void delete(ActionEvent e) {
		//List<String> choices = new ArrayList<>();
		int index = lv.getSelectionModel().getSelectedIndex();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm Delete");
		alert.setHeaderText("Are You Sure You Wish to Delete This Image?");
		Optional<ButtonType> con = alert.showAndWait();
		
		
		if(con.get() == ButtonType.OK) {
			album.list.remove(index);
			il.remove(index);
		}
		
	}
	
	/**
	 * This method writes a list of current users out to a file for later use
	 * @param album
	 * @throws IOException
	 */
	
	public static void writeApp(AlbumControllerReal acr) throws IOException {

		storeDir = "src/albums";
		storeFile = LoginHandler.name + "_" + UserController.albumName + ".dat";
		ObjectOutputStream oos = new ObjectOutputStream(
		new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(album);
		//System.out.println(acr.pl.toArray(new Photo[0]).toString());
		System.out.println("Write Successful");
	}
	
	/**
	 * This method reads the list of current users from a file
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	
	public static void readApp() throws IOException, ClassNotFoundException {

		storeDir = "src/albums";
		storeFile = LoginHandler.name + "_" + UserController.albumName + ".dat";
		
		ObjectInputStream ois = new ObjectInputStream(
		new FileInputStream(storeDir + File.separator + storeFile));
		Album acr = (Album) ois.readObject();
		album.list = acr.list;
		for (int i = 0; i < album.list.size(); i++) {System.out.println(album.list.get(i).getUrl());
			
//			Image image = new Image((new File(album.list.get(i).getUrl())).toURI().toString(), 100, 100, false, false);	
//			il.add(new ImageView(image));
		}
//		System.out.println(album.toString());
	} 
	
}