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
	private static GridPane gp;
	private static int index = -1;
	
	private ArrayList<Photo> list = new ArrayList<Photo>();
	
	private Desktop desktop = Desktop.getDesktop();

	public static final String storeDir = "src/albums";
	public static final String storeFile = LoginHandler.name + "_" + UserController.albumName + ".dat";
	
	public int row = 0;
	public int col = 0;
	
	public static Album album;
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Photo Library");
		primaryStage.setResizable(false);
		try {
			Parent root= FXMLLoader.load(getClass().getResource("AlbumView.fxml"));
			scene = new Scene(root);
			gp = (GridPane) scene.lookup("#gpView");
			
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
		
		/*try {
			readApp();
		
			System.out.println(list.get(index).getUrl()+'\n'+list.get(index+1).getUrl());
			img= new ImageView(list.get(index).getUrl());
			
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		
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
			Photo picture = new Photo(path.toString());
			list.add(picture);
			Image image = new Image(path.toURI().toString(), 100, 100, false, false);	
			if(col == 6) {
				col = 0;
				row+=1;
			}
			gp.add(new ImageView(image), col, row);
			col++;
		}
	}
	
	@FXML
	public void delete(ActionEvent e) {
		if(index>=0) {
			list.remove(list.get(index));
		}
		if(index>0) {
			index--;
			Image image = new Image(list.get(index).getUrl());
			//img.setImage(image);
		}
	}
	
	/**
	 * This method writes a list of current users out to a file for later use
	 * @param album
	 * @throws IOException
	 */
	
	public static void writeApp(Album album) throws IOException {
			ObjectOutputStream oos = new ObjectOutputStream(
			new FileOutputStream(storeDir + File.separator + storeFile));
			oos.writeObject(album);
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