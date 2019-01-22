package com.H446.Themis;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Stage;
import com.controller.*;

//Starting point for the program
public class Main extends Application{

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/view/HomePage.fxml"));
		
		
		HomePage homeController = new HomePage();
		loader.setController(homeController);
		
		Parent root = loader.load();
		
		primaryStage.setTitle("Themis");
		primaryStage.setScene(new Scene(root, 1280, 800));
		primaryStage.show();
	}
	
	
	
	
	public static void main(String[] args){
		launch(args);
	}

}
