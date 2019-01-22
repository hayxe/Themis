package com.model;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

//Defines the general structure of Report
public abstract class Report {
	
	Element element; //Element Object that report will be on
	String location; //Stores the location of where the report will be saved
	
	//Initializer for all report subclasses
	public Report(Element element, String location){
		this.element = element;
		this.location = location;
		
	}





}