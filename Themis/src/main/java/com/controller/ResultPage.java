package com.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.model.Element;
import com.model.SeacrhQuery;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class ResultPage implements  Initializable {

	@FXML
	private AnchorPane layout;
	
	@FXML
	private MenuBar menuBar;
	
	@FXML
	private  JFXListView<Element> resultView;
	
	@FXML
	private MenuItem tHome;
	
	static String property;
	static String value;
	@Override
	
	public void initialize(URL location, ResourceBundle resources) {
	
		List<Element> results = getResults();
		
		//If no result found displays this message to user
		if(results.isEmpty()){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Element doesn't exist");
			alert.setContentText("There is no element with the "+property+" of "+value);
			alert.showAndWait();
		}
		System.out.println(results.size()); //For testing purposes only
		System.out.println(results.get(0).getGroupProperties()); //Test only //To see if we get description
		
		ObservableList<Element> elementsList = FXCollections.observableArrayList(results);
		resultView.setItems(elementsList);
		
		
			
		//Creates Cells that will display elements name in Listview using lambada format
		resultView.setCellFactory(param -> new ListCell<Element>(){
			
			@Override
			public void updateItem(Element item, boolean empty){
				super.updateItem(item,empty);
				
				if(empty || item == null ){
					setText(null);
				}else{
					setText(item.getElementName());
				}
			}
		});
		
		//Define what happens  when one of the cells are clicked 
		
		resultView.getSelectionModel().selectedItemProperty().addListener((changed,oldVal,newVal) -> {
			
			//We need to load the new ElementPage fxml file, set it's controller and then parse the value of the string property 
			FXMLLoader loader =  new FXMLLoader(getClass().getResource("/com/view/ElementPage.fxml"));
			ElementPage controller = new ElementPage();
			loader.setController(controller);
			
			
			Parent root;
			((ElementPage) loader.getController()).getElement(newVal);
			System.out.println(newVal.getElementName()); //Testing purposes only
			
			try {
				root = loader.load();
				layout.getChildren().setAll(root);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		onTHome();
	
		}
		
		
	
	
	public void initData(String property, String propertyValue){
		this.property = property;
		this.value = propertyValue;
		
	}
	
 
		
	//Gets the results from the users search 
	private static List<Element> getResults(){
		List<Element> results; //Remove null value letter
		SeacrhQuery search = new SeacrhQuery(property, value);
		
		results = search.Search();

		return results;
	}

	//Defines what occurs when the Home menu item is clicked 
	 private void onTHome(){
		  //Re-directs user to the home page
		  tHome.setOnAction(e -> {
			  
			  FXMLLoader loader =  new FXMLLoader(getClass().getResource("/com/view/HomePage.fxml")); 
			  HomePage homeController = new HomePage(); //Creating Homepage controller object 
			  loader.setController(homeController); //Setting the Homepage controller to the loader
			  
			  
			  try {
				Parent root = loader.load(); 
				layout.getChildren().setAll(root); //Making layout display home page
				layout.setPrefSize(1280, 720);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			  
			  
			  
		  });
		  
	  }
}
