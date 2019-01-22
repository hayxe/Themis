package com.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.model.PeriodicTable;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


//Defines validation functions for User input
public class Validation {
	
	
	

	

	
	
//Function to check that the user has entered input
private static boolean isTextFieldEmpty(String input){
		boolean result = false;
		
		if(input.isEmpty()){ 
			result = true; 
			Alert alert = new Alert(AlertType.WARNING); //Creating Alert 
			alert.setTitle("Empty Text Field"); //setting title of alert 
			alert.setContentText("You haven't entered a value for the property selected. \n Please enter a value"); //Setting text of alert
			alert.showAndWait(); //Showing alert 
			
		}
		
		return result; //True is returned when field is empty and false is returned when field isn't empty
	}
    
//Function to check that the input only consists of letters and no spaces 
private static boolean isPureLetter(String input){
	    boolean result = true;
	    
		if(!input.chars().allMatch(Character::isLetter)){ //Using lambda Java 8 expression I'm checking that all characters in the input are letters
			 result = false;
			 Alert alert = new Alert(AlertType.WARNING); //Creating Alert
				alert.setTitle("Type Mismatch"); //setting title of alert
				alert.setContentText("The type of the value you have inputted doesn't match the type of the property selected. \n Please enter a value that consist of only letters");  //Setting text of alert
				alert.showAndWait(); //Showing alert
		}
		
		return result; //True is returned when the field contains only letters
		
	}
	
//Function to check that the input only consist of letters and spaces 
private static boolean isLetterSpaces(String input){
	
	Pattern p = Pattern.compile("^[ A-Za-z]+$"); //This pattern only describes letters and spaces
	
	//Checking to see if the user input matches with the pattern described above
	Matcher m = p.matcher(input); 
	boolean b = m.matches(); 
	
	if(b){
		return true; //If input matches pattern true is returned 
	}else{
		Alert alert = new Alert(AlertType.WARNING); //Creating Alert
		alert.setTitle("Type Mismatch");// Setting title of alert
		alert.setContentText("The type of the value you have inputted doesn't match the type of the property selected. \n Please enter a value that consist of only letters and spaces"); //Setting text of alert
		alert.showAndWait(); //Showing alert
		return false; //If input doesn't consist purely of letters and spaces false is returned 
	}
}

//Function to check if input is in range 
private static boolean isInRange(int input){
	if(input > 118 || input < 1){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Not in Range");
		alert.setContentText("The value inputted isn't in the required range. \n Please enter a value that is in the range of 1 and 118 inclusive");
		alert.showAndWait();
		return false;
	}
	return true;
}
//Function to validate user input is an integer 
private static boolean isInteger(String input){
		int str;
		
		try{
			
			str = Integer.parseInt(input); //Try to parse the input String to an Integer. If parse fails then an number Format Exception will be thrown
			return isInRange(str); //Check to see if it is in between 1 and 118
			
		}catch(NumberFormatException e){  //Exception caught and value returned false 
			Alert alert = new Alert(AlertType.WARNING); //Creating Alert
			alert.setTitle("Type Mismatch"); //Setting title of alert
			alert.setContentText("The type of the value you have inputted doesn't match the type of the property selected. \n Please enter a value that is a Integer");
			alert.showAndWait();
			return false;
			
		}
		
	
	}
    
//Function to validate user input is a decimal using a double 
private static boolean isDouble(String input){
		double str;
		try{
			str = Double.parseDouble(input); //Try to parse the input String to an Double. If parse fails then an number Format Exception will be thrown
		    
		}catch(NumberFormatException e){ //Exception caught and value returned false
			
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Type Mismatch");
			alert.setContentText("The type of the value you have inputted doesn't match the type of the property selected. \n Please enter a value that is a Decimal");
			alert.showAndWait();
			return false;
		}
		
		return true; //If the parse is successful it will reach the end and return true
	}



static boolean isvalueValid(String input, String propertySelected){
	boolean result;
	
	if(isTextFieldEmpty(input)){
		result = false;
		
	}else{
		
		switch(propertySelected){
		case "Name":
		case "Symbol":
			result = isPureLetter(input);
			break;
		case "Group Name":
			result = isLetterSpaces(input);
			break;
		case "Atomic Number":
			result = isInteger(input);
			break;
		case "Atomic Mass":
		case "Boiling Point":
		case "Melting Point":
		case "Triple Point Pressure":
		case "Triple Point Temperature":
			result = isDouble(input);
			break;
		case "Electron Configuration": //code missing in documentation
            result = true;
            break;
			default:
				result = false;
		}
		
		
	}
	
	
	
	
	return result;
	
}

}

