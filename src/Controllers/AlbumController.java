package Controllers;

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
import javafx.stage.Stage;
import resources.User;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
 * AlbumController.java - This class controls Photos in an Album
 * @author Matthew Marrazzo mdm289
 * @author Craig Sirota cms631
 */

public class AlbumController extends Application implements Serializable{

	private static Stage mainStage;
	private static Scene scene;
	private static ImageView img;

	public static final String storeDir = "src/albums";
	public static final String storeFile = LoginHandler.name + "_" + UserController.albumName + ".dat";
	
	public static Album album;
	private PhotoNode pn;
	
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Photo Library");
		primaryStage.setResizable(false);
		try {
			Parent root= FXMLLoader.load(getClass().getResource("PhotoView.fxml"));
			scene = new Scene(root);
			img = (ImageView) scene.lookup("#imgView");
			writeApp(new Album());
			primaryStage.setOnCloseRequest(e -> {
				try {
					writeApp(album);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        Platform.exit();
		        System.exit(0);
		    });
			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			readApp();
			
			if (album.startNode.isEmpty()) {
			//	img.setImage(new Image(null));
				
			}else {
				pn = album.startNode;
				img.setImage(new Image(pn.data.getUrl()));
			}
			
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		primaryStage.setScene(scene);
		primaryStage.show();
		mainStage= primaryStage;
	}
	
	@FXML
	public void prev(ActionEvent e) {
		
	}
	
	@FXML
	public void next(ActionEvent e) {
		
	}
	
	/**
	 * This method writes a list of current users out to a file for later use
	 * @param album
	 * @throws IOException
	 */
	
	public static void writeApp(Album album) throws IOException {
			ObjectOutputStream oos = new ObjectOutputStream(
			new FileOutputStream(storeDir + File.separator + storeFile));
//			oos.writeObject(album);
			Album test = new Album("AllTheSmallThings");
			test.startNode=new PhotoNode(new Photo("/Users/craigsirota/Downloads/Qoikos.jpg"));
			oos.writeObject(test);
	}
	
	/**
	 * This method reads the list of current users from a file
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	
	public static void readApp() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(
		new FileInputStream(storeDir + File.separator + storeFile));
		album = (Album) ois.readObject();
		
//		System.out.println(album.toString());
	} 
	
}