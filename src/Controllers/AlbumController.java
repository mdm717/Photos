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
import resources.Tag;
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
	private static ListView tagList;
	public static int index = -1;
	private ObservableList<Tag> tList = FXCollections.observableArrayList();
	private ObservableList<Photo> list = FXCollections.observableArrayList();
	
	private Desktop desktop = Desktop.getDesktop();

	public static final String storeDir = "src" + File.separator + "albums";
	public static final String storeFile = LoginHandler.name + "_" + UserController.albumName + ".dat";
	
	public static Album album;
	
	/**
	 * This method loads the details of the selected image in an album
	 * @param primaryStage		the scene to be loaded
	 */
	
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Photo Library");
		primaryStage.setResizable(false);
		try {
			Parent root= FXMLLoader.load(getClass().getResource("PhotoView.fxml"));
			scene = new Scene(root);
			img = (ImageView) scene.lookup("#imgView");
			tagList = (ListView) scene.lookup("#tagView");
			
			index = AlbumControllerReal.lv.getSelectionModel().getSelectedIndex();
			tagList.setItems(tList);
			File imageFile = new File(AlbumControllerReal.album.list.get(index).getUrl());
			Image image = new Image(imageFile.toURI().toString());
			img.setImage(image);

			primaryStage.setOnCloseRequest(e -> {
				try {
					album.list.clear();
					album.list.addAll(list);
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
			File file = new File(AlbumControllerReal.album.list.get(index).getUrl());
			Image image = new Image(file.toURI().toString());
			if (index > -1)
				//img= new ImageView(image);
				img.setImage(image);
		} catch (ClassNotFoundException e) {	
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		primaryStage.setScene(scene);
		primaryStage.show();
		mainStage= primaryStage;
	}
	
	/**
	 * This method shows the previous image in album list
	 * @param e		a button press
	 */
	
	@FXML
	public void prev(ActionEvent e) {
			if (index>0) {
				index--;
				File file = new File(AlbumControllerReal.album.list.get(index).getUrl());
				Image image = new Image(file.toURI().toString());
				img.setImage(image);
				tList.clear();
				tList.addAll(AlbumControllerReal.album.list.get(index).tags);
				((Text) scene.lookup("#caption")).setText(album.list.get(index).getCaption());
				((Text) scene.lookup("#date")).setText("Date: "+album.list.get(index).getDate().toString());
				
			}
	}
	
	/**
	 * This method shows the next image in an album list
	 * @param e		a button press
	 */
	@FXML
	public void next(ActionEvent e) {
		if (index<AlbumControllerReal.album.list.size()-1) {
			index++;
			File file = new File(AlbumControllerReal.album.list.get(index).getUrl());
			Image image = new Image(file.toURI().toString());
			img.setImage(image);;
			tList.clear();
			tList.addAll(AlbumControllerReal.album.list.get(index).tags);
			((Text) scene.lookup("#caption")).setText(album.list.get(index).getCaption());
			((Text) scene.lookup("#date")).setText("Date: "+album.list.get(index).getDate().toString());
		}
	}
	
	/**
	 * This method allows the user to return to the album thumbnail page
	 * @param e		a button press
	 */
	
	@FXML
	public void back(ActionEvent e) {
		(new AlbumControllerReal()).start(mainStage);
	}
	
	/**
	 * This method allows a user to caption the photo they are viewing
	 * @param e		a button press
	 */
	
	@FXML
	public void caption(ActionEvent e) {
		TextInputDialog dialog = new TextInputDialog();		
		dialog.setTitle("Write Caption");
		dialog.setHeaderText("What Would you like to Caption this Photo?");
		dialog.showAndWait();
		
		String caption = dialog.getEditor().getText().toString();
		album.list.get(index).setCaption(caption);
		((Text) scene.lookup("#caption")).setText(caption);
		
	}
	
	/*
	
	@FXML
	public void addPhoto(ActionEvent e) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select a photo");
		File path = fileChooser.showOpenDialog(mainStage);
		if(path != null) {
			Photo picture = new Photo(path.toString());
			list.add(picture);
			Image image = new Image(path.toURI().toString());
			index = list.size()-1;
			img.setImage(image);
		
		}
	}*/
	
	/*
	@FXML
	public void deletePhoto(ActionEvent e) {
		File file = new File(AlbumControllerReal.album.list.get(index).getUrl());
		if(index>=0) {
			AlbumControllerReal.album.list.remove(AlbumControllerReal.album.list.get(index));
			index--;
			Image image = new Image(file.toURI().toString());
			img.setImage(image);
		}
		if(index>0) {
			index--;
			Image image = new Image(file.toURI().toString());
			img.setImage(image);
		}
	}
	*/
	
	/**
	 * This method allows a user to add tags to the photo they are viewing
	 * @param e		a button press
	 */
	
	@FXML
	public void addTag(ActionEvent e) {
		//Photo photo = album.list.get(index);
		AlbumControllerReal.album.list.get(index);
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Add Tag");
		dialog.setHeaderText("Type of Tag: ");
		dialog.showAndWait();
		String type = dialog.getEditor().getText();
		type = type.toUpperCase();
		TextInputDialog dialog2 = new TextInputDialog();
		dialog2.setTitle("Add Tag");
		dialog2.setHeaderText("Tag Content: ");
		dialog2.showAndWait();
		String data = dialog2.getEditor().getText();
		System.out.println(index +"\n"+type+"\n"+data);
		AlbumControllerReal.album.list.get(index).addTag(type, data);
		tList.clear();
		tList.addAll(AlbumControllerReal.album.list.get(index).tags);
		tagList.setItems(tList);
	}
	

	/**
	 * This method allows a user to delete tags to the photo they are viewing
	 * @param e		a button press
	 */
	@FXML
	public void deleteTag(ActionEvent e) {
		//Photo photo = album.list.get(index);
		int i = tagList.getSelectionModel().getSelectedIndex();
		AlbumControllerReal.album.list.get(index).tags.remove(i);
		tList.clear();
		tList.addAll(AlbumControllerReal.album.list.get(index).tags);	
		tagList.setItems(tList);
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