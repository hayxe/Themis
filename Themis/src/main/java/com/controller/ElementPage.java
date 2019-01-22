package com.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextArea;
import com.model.AdvancedReport;
import com.model.BasicReport;
import com.model.Element;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

public class ElementPage implements Initializable {
     
	@FXML 
	private AnchorPane layout;
	@FXML
	private MenuBar menuBar;
	
	@FXML
	private JFXTextArea infoArea;
	
	@FXML
    private MenuItem mrBasic;
	
	@FXML
	private MenuItem mrAdvance;
	
	@FXML
	private MenuItem tHome;
	
	
	@FXML
	private Label header;
	
	

	
	Element element;
	
	final static ExtensionFilter pdfFilter = new ExtensionFilter("PDF File","*.pdf");
	 
	@Override
 public void initialize(URL location, ResourceBundle resources)  {
    displayInformation();
    onMBRBasic();
    onMBRAdvance();
    onTHome();

	
	}
	
	public void getElement(Element e){
		this.element = e;
		
	}

	
	private  void displayInformation(){
		
		String tPT; //Stores string representation of the Triple point temperature
		String tPP; //Stores string representation of the Triple point Pressure
		
		//Assigning the triple point values for the element if it exist to the strings declared above
		if(isNA(element.getTPTemperature())){
			tPT="N/A";
			tPP= "N/A";
		}else{
			tPT = String.valueOf(element.getTPTemperature());
			tPP = String.valueOf(element.getTPPressure());
			
		}
        //Main header
	    Text mainHeader = new Text(""+element.getElementName()); //Creating the main headers text
		header.setText(mainHeader.getText()); // Setting the main headers text to the label header declared above 
		
		//Key Info
		//Setting the Text for all the key basic information on the element
		Text keyInfoBox = new Text(" Element Name: "+element.getElementName()+"\n Atomic Number: "+element.getAtomicNumber()+"\n Atomic Mass: "+element.getAtomicMass()+
				"\n Electron Configuration: "+element.getElectronConfiguration()+"\n Group: "+element.getGroupName()+"\n Boiling Point: "+element.getBoilingPoint()+"\n Melting Point: "+element.getMeltingPoint()+
				"\n Triple Point Temperature: " + tPT + "\n Triple Point Pressure: "+ tPP);
		keyInfoBox.setId("keyInfo");
		//infoArea.setText(keyInfoBox.getText());
		//Uses
		
		//Uses of element information
		Text usesHeader = new Text("Uses of "+element.getElementName()); //Creating text for uses header
		usesHeader.setId("usesHeader"); // Setting an ID to the text we created above so we can style in style sheet 
		Text usesBox = new Text(element.getUseDescription()); // Creating  text for the elements  use description
		usesBox.setId("usesBox"); //Setting id of the usesBox so we can style in style sheet
		
		//Test of element information
		Text testHeader = new Text("Test for "+ element.getElementName()); //Creating text for test header
		testHeader.setId("testHeader"); // Setting ID to the testHeader so we can style in in style sheet
		Text testBox = new Text(element.getTestDescription()); //Creating the text for elements test description
		testBox.setId("testBox"); //Setting id of the testBox so we can style in style sheet
		
		
		
		infoArea.setText(keyInfoBox.getText() + "\n \n \n " + usesHeader.getText() + "\n \n" +usesBox.getText() + "\n \n \n" + testHeader.getText() + "\n \n" + testBox.getText()); //Adding all information to infoArea
		
		System.out.println(element.getUseDescription());
		System.out.println(element.getTestDescription());
		System.out.println(element.getElementName());
		
	}
	//Check if value is available or not
	private static boolean isNA(Double propertyValue){
		boolean result = false;
		
		if(propertyValue==0){
			result = true;
		}
		return true;
	}
    
	
//Defines what occurs when the basic menu item is clicked
  private void  onMBRBasic() {
    	
     mrBasic.setOnAction(e -> {
    	 	FileChooser fc = new FileChooser(); //Creates new FileChooser
        	fc.getExtensionFilters().add(pdfFilter);
        	File selectedFile = fc.showSaveDialog(null); //Opens up save dialog allowing user to save to desired place
        	
        	if(selectedFile != null){
        		try {
					BasicReport bReport = new BasicReport(element,selectedFile.getCanonicalPath());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} //Creating Basic report and saving it to desired location
        		
        	}
     });
    	
    }
//Defines what occurs when the advanced menu item is clicked
  private void onMBRAdvance(){
    
	  mrAdvance.setOnAction(e -> {
		  
		    FileChooser fc = new FileChooser(); //Creates new FileChooser
		    fc.getExtensionFilters().add(pdfFilter);
		    File selectedFile = fc.showSaveDialog(null); //Opens up save dialog allowing user to save to desired place
		    	
		    	if(selectedFile != null){
		    		try {
						AdvancedReport advReport = new AdvancedReport(element,selectedFile.getCanonicalPath());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		    		
		    	}
	  });
	  
	 
    	
    }
//Defines what occurs when the Home menu item is clicked 
  private void onTHome(){
	  //Re-directs user to the home page
	  tHome.setOnAction(e -> {
		  
		  FXMLLoader loader =  new FXMLLoader(getClass().getResource("/com/view/HomePage.fxml")); 
		  HomePage homeController = new HomePage(); //Creating Homepage controller object 
		  loader.setController(homeController); //Setting the Homepage controller to the loader
		  
		  
		  try {
			Parent root = loader.load(); //
			layout.getChildren().setAll(root); //Making layout display home page
			layout.setPrefSize(1280, 720);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		  
		  
		  
	  });
	  
  }
  
 


}
