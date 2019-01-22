package com.controller;

import com.model.DialogBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomePage implements Initializable {

	  @FXML
	 private AnchorPane layout;

	 @FXML
	 private  JFXComboBox<String> properties;
	 
	 @FXML  
	 private JFXButton searchBtn;
	 
	 @FXML
	 private JFXTextField valueProperty;
      
	 
	 @FXML
	 private Label validationLabel;
	 
      
       
	
	public void initialize(URL location, ResourceBundle resources) {
		
        searchBtn.setStyle("-fx-background-color: #00AA8D");
		properties.getItems().addAll("Name", "Symbol","Atomic Number", "Atomic Mass","Electron Configuration","Group Name","Group Number","Boiling Point","Melting Point");
		properties.getItems().addAll("Triple Point Temperature", "Triple Point Pressure");
		
	

	}
	
	//Defines what happens when button is clicked 
	@FXML
	public void onSearchButton(ActionEvent event) throws IOException{
		
		String propertySelected = properties.getValue(); //Getting the value of the property selected in the drop down
		String enteredValue = valueProperty.getText(); //Made reference to the users input into Text field
		//Text Field validation
		boolean isValid = Validation.isvalueValid(enteredValue, propertySelected);
		
		if(isValid){ 
			
			loadResultPage(propertySelected,enteredValue);
		}

	
	}
	
	//LoadsResultPage
	public void loadResultPage(String propertySelected, String propertyValue) throws IOException{
		
		propertySelected = propertySelected.replaceAll("\\s+",""); //This removes all white spaces and makes this the proper format for the search method
		System.out.println(propertySelected);
		FXMLLoader loader =  new FXMLLoader(getClass().getResource("/com/view/ResultPage.fxml")); //Load the fxml file
		ResultPage controller = new ResultPage(); // Creating a ResultPage a controller object
		loader.setController(controller); //Setting the controller to fxml file
		
		Parent root;
		((ResultPage) loader.getController()).initData(propertySelected, propertyValue); //Passes propertySelected and the value inputed to the Result Page controller
		
		root = loader.load(); 
		
		layout.getChildren().setAll(root); //Making layout display fxml file
		
		
		
		
		 
		
	}

}
