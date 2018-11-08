package Controllers;

import resources.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

public class LoginHandler {

	@FXML
	private void Login(ActionEvent event) {
		if (LoginView.getText().
				equals("admin")) {
			(new AdminController()).start(LoginView.getMainStage());
			System.out.println("AAA");
		} else {
			for (int i = 0; i < LoginView.ac.data.size(); i++) {
				if (LoginView.ac.data.get(i).getName().equals(LoginView.getText())) {
                    LoginView.prompt("Username");
                    (new UserController()).start(LoginView.getMainStage());
				}
			}
//			
//			try {
//				System.out.println("BBB");
//				System.out.println(LoginView.getText());
//				search(LoginView.getText());
//			} catch (IOException e) {
//				System.out.println("CCC");}
		}
	}
//	
//	private static boolean search(String name) throws IOException {
//		String file = "src/resources/Users.txt";
//
//        try {System.out.println("DDD");
//            BufferedReader br = new BufferedReader(new FileReader(file));System.out.println("FFF");
//        	String line = br.readLine();
//        	System.out.println(line);
//            while (line != null) {
//            	if(line.equals(name)) {
//            		br.close();
//                    LoginView.prompt("Username");
//                    (new UserController()).start(LoginView.getMainStage());
//            		return true;
//            	}
//            	line = br.readLine();
//            }
//            br.close();
//        } catch (FileNotFoundException e) {
//        	e.printStackTrace();
//        }
//        
//        LoginView.prompt("Name Not Found");
//        
//		return false;
//	}
}
