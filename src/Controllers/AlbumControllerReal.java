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
	private static GridPane gp;
	private static int index = -1;
	
	private ArrayList<Photo> list = new ArrayList<Photo>();
	
	private Desktop desktop = Desktop.getDesktop();

	public static final String storeDir = "src/albums";
	public static final String storeFile = LoginHandler.name + "_" + UserController.albumName + ".dat";
	
	public int row = 0;
	public int col = 0;
	
	public int tempRow = 0;
	public int tempCol = 0;
	
	public int listDex = 0;
	
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
		if(row < 4) {
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
		else if (row < 4){
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Album Full");
			alert.setHeaderText("Album is full.  Please delete photos before adding more.");
			alert.showAndWait();
		}
	}
	
	@FXML
	public void delete(ActionEvent e) {
		//List<String> choices = new ArrayList<>();
		String[] arrayData = {"col: 1, row: 1", "col: 2, row: 1", "col: 3, row: 1", "col: 4, row: 1", "col: 5, row: 1", "col: 6, row: 1",
								"col: 1, row: 2", "col: 2, row: 2", "col: 3, row: 2", "col: 4, row: 2", "col: 5, row: 2", "col: 6, row: 2",
								"col: 1, row: 3", "col: 2, row: 3", "col: 3, row: 3", "col: 4, row: 3", "col: 5, row: 3", "col: 6, row: 3",
								"col: 1, row: 4", "col: 2, row: 4", "col: 3, row: 4", "col: 4, row: 4", "col: 5, row: 4", "col: 6, row: 4"};
		
		List<String> dialogData = Arrays.asList(arrayData);
		
		ChoiceDialog<String> dialog = new ChoiceDialog(dialogData.get(0), dialogData);
		dialog.setHeaderText("Delete an Image");
		dialog.setContentText("Select an Image to Delete: ");
		
		Optional<String> result = dialog.showAndWait();
		
		if(result.isPresent()) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirm Delete");
			alert.setHeaderText("Are You Sure You Wish to Delete This Album? All Pictures in the Album will be Lost.");
			Optional<ButtonType> con = alert.showAndWait();
			
			
			if(con.get() == ButtonType.OK) {
				if(result.get() == "col: 1, row: 1") { col=0; row=0; listDex = 0; }
				else if(result.get() == "col: 2, row: 1") { col=1; row=0; listDex = 1;}
				else if(result.get() == "col: 3, row: 1") { col=2; row=0; listDex = 2;}
				else if(result.get() == "col: 4, row: 1") { col=3; row=0; listDex = 3;}
				else if(result.get() == "col: 5, row: 1") { col=4; row=0; listDex = 4;}
				else if(result.get() == "col: 6, row: 1") { col=5; row=0; listDex = 5;}
				else if(result.get() == "col: 1, row: 2") { col=0; row=1; listDex = 6;}
				else if(result.get() == "col: 2, row: 2") { col=1; row=1; listDex = 7;}
				else if(result.get() == "col: 3, row: 2") { col=2; row=1; listDex = 8;}
				else if(result.get() == "col: 4, row: 2") { col=3; row=1; listDex = 9;}
				else if(result.get() == "col: 5, row: 2") { col=4; row=1; listDex = 10;}
				else if(result.get() == "col: 6, row: 2") { col=5; row=1; listDex = 11;}
				else if(result.get() == "col: 1, row: 3") { col=0; row=2; listDex = 12;}
				else if(result.get() == "col: 2, row: 3") { col=1; row=2; listDex = 13;}
				else if(result.get() == "col: 3, row: 3") { col=2; row=2; listDex = 14;}
				else if(result.get() == "col: 4, row: 3") { col=3; row=2; listDex = 15;}
				else if(result.get() == "col: 5, row: 3") { col=4; row=2; listDex = 16;}
				else if(result.get() == "col: 6, row: 3") { col=5; row=2; listDex = 17;}
				else if(result.get() == "col: 1, row: 4") { col=0; row=3; listDex = 18;}
				else if(result.get() == "col: 2, row: 4") { col=1; row=3; listDex = 19;}
				else if(result.get() == "col: 3, row: 4") { col=2; row=3; listDex = 20;}
				else if(result.get() == "col: 4, row: 4") { col=3; row=3; listDex = 21;}
				else if(result.get() == "col: 5, row: 4") { col=4; row=3; listDex = 22;}
				else if(result.get() == "col: 6, row: 4") { col=5; row=3; listDex = 23;}
				
				list.set(listDex, null);
				
			}
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