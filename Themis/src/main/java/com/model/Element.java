package com.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public final class Element {

	
	/*
	 * This provides the standard blueprint for all elements in the periodic table
	 *  and the getter methods to obtain the relevant data from the database 
	 */

	//Start Connection to Database
	
	
    int ElementID;          // This is what uniquely identifies each element 
	int GID;              // Uniquely identifies the group of the element
	int EShellID;        // Equal to ElementID
	int TestID;          // Equal to ElementID
	int UsesID;         // Equal to ElementID
	int TriplePointID; // Equal to ElementID
	int VPID;       // Equal to ElementID
	int ColourID;   // Uniquely identifies Color of element
	
	String ElementName;
		public List<Isotope> getIsotopes() {
		return Isotopes;
	}
	String Symbol;
	String GroupName;
	String GroupProperties; 
	String ElectronConfiguration;
	String TestDescription;
	String UseDescription;
	String Colour;
	
	int AtomicNumber;
	int GroupNumber;
	
	double AtomicMass;
	double MeltingPoint;
	double BoilingPoint;
	double SubmationPoint; // Only holds a value when the melting and boiling point are exactly the same. For instance, Carbon.
	double TPTemperature;
	double TPPressure;
	              
	boolean radioactive;
	
	List<Isotope> Isotopes;


public  Element(int ElementID){
		
		
		
		
		
		
		
		this.ElementID = ElementID;
		this.EShellID = ElementID;
		this.TestID = ElementID;
		this.TestID = ElementID;
		this.UsesID = ElementID;
		this.TriplePointID = ElementID;
		this.VPID = ElementID;
		
		
		
		try{
			//Get Connection
			Class.forName("com.mysql.jdbc.Driver");
			Connection linkDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ThemisDB","root","JewishThundercunt"); // My Jewish friend made the password; excuse his insolence 
			
			this.ElementName = getFromDBElementName(ElementID, linkDB);
			this.Symbol = getFromDBSymbol(ElementID,linkDB);
			this.GroupName= getFromDBGroupName(ElementID,linkDB);
			this.AtomicNumber = getFromDBAtomicNumber(ElementID,linkDB);
			this.AtomicMass = getFromDBAtomicMass(ElementID,linkDB);
			this.SubmationPoint = getFromDBSubmationPoint(ElementID,linkDB);
			
			if(SubmationPoint != 0){
				this.BoilingPoint = 0;
				this.MeltingPoint = 0;
			}else{
				this.BoilingPoint= getFromDBBoilingPoint(ElementID,linkDB);
			    this.MeltingPoint = getFromDBMeltingPoint(ElementID, linkDB);
			}
		    
		    this.TPTemperature = getFromDBTPTemperature(ElementID, linkDB);
		    this.TPPressure = getFromDBTPPressure(ElementID, linkDB);
		    this.Colour = getFromDBColour(ElementID,linkDB);
		    this.ElectronConfiguration = getFromDBElectronConfiguration(ElementID,linkDB);
		    this.TestDescription = getFromDBElementTest(ElementID,linkDB); // Must create Files first
		    this.UseDescription = getFromDBElementUses(ElementID,linkDB); //Must create Files first (Error)
		    this.GroupProperties = getFromDBGroupProperties(ElementID,linkDB);
			this.Isotopes = getIsotopesFromList(ElementID,linkDB);
			this.radioactive = isRadioactive(ElementID,linkDB);
			
			linkDB.clearWarnings();
			linkDB.close();

			
		}
		catch(Exception exc){
			exc.printStackTrace();
		}
		
	}
//ReadingFile content //WORKS GANG SHIT NIGGA
private  String retriveContentFile(String filePath) throws IOException{
		String contents ="";
		
    File file = new File(filePath);
    
   
    Scanner read = new Scanner(file);
    
    while(read.hasNextLine()){
    	
    	contents += read.nextLine();
    }
    read.close();
    
    
 return contents;
    
		
	}
// Getter for G_ID
private int getFromDBGID(int ElementID, Connection link){
		int GID;
		
		try{
			Statement query = link.createStatement();
			ResultSet result = query.executeQuery("SELECT G_ID FROM Base WHERE ElementID= "+ElementID);
			
			while(result.next()){
				
				GID = result.getInt("G_ID");
				return GID;
			}
		}catch(Exception exc){
			
			exc.printStackTrace();
		}
		return 0;
	}
private int getFromDBColourID(int ElementID, Connection link){
		
		int colourID;
		
		try{
			Statement query = link.createStatement();
			ResultSet result = query.executeQuery("SELECT ColourID FROM Base WHERE ElementID= "+ElementID);
			
			while(result.next()){
				colourID = result.getInt("ColourID");
				return colourID;
			}
		}catch(Exception exc){
			
			exc.printStackTrace();
		}
		
		return 0;
	}
// Getter for elements name 
private String getFromDBElementName(int ElementID, Connection link) {
		String eName;
		
		try{
		Statement st = link.createStatement();
		ResultSet result = st.executeQuery("SELECT ElementName FROM Base WHERE ElementID= " + ElementID);
		
		while(result.next()){
			
			eName = result.getString("ElementName");
			
			return eName;
		}
	
		} 
		catch(Exception exc){
			
			exc.printStackTrace();
		}
		
		return "Name not found";
		
		
	}
// Getter for the elements symbol 
private String getFromDBSymbol(int ElementID, Connection link){
		
		String eSymbol;
		
		try{
			
			Statement query = link.createStatement();
			ResultSet result = query.executeQuery("SELECT Symbol FROM Base WHERE ElementID= "+ ElementID);
			
			while(result.next()){
				
				eSymbol = result.getString("Symbol");
				
				return eSymbol;
			}
			
		}catch(Exception exc){
			
			exc.printStackTrace();
		}
		
		return "Symbol not found";
	}
// Getter for the group name
private String getFromDBGroupName(int ElementID, Connection link){
		
		String gName;
		
		try{
			Statement query = link.createStatement();
			ResultSet result = query.executeQuery("SELECT Name FROM GROUPS WHERE G_ID= "+getFromDBGID(ElementID,link));
			
			while(result.next()){
				
				gName = result.getString("Name");
				return gName;
			}
		} catch(Exception exc){
			
			exc.printStackTrace();
		}
		return "Name not found";
	}
// Getter for the group properties //FIX DOESN'T WORK
private String getFromDBGroupProperties(int ElementID, Connection link){
	String linkFile;
	String extension = "/Users/hayxe/Desktop/Unknow/Back-end/TextFiles/GroupInfo/";
	String gDescription;
	
	
	try{
		
		Statement query = link.createStatement();
		ResultSet result = query.executeQuery("SELECT Properties FROM GROUPS WHERE G_ID= "+ getFromDBGID(ElementID,link));
		
		while(result.next()){
			
        linkFile = result.getString("Properties");
        
        gDescription = retriveContentFile(extension+linkFile);
        return gDescription;
		}
		
		
		
	}catch(Exception exc){
		exc.printStackTrace();
	}
		return "description not found ";
	}
//Setter for element uses //FIxed and Optimized 
private String getFromDBElementUses(int ElementID, Connection link) throws IOException{
	String eUses;
	String extension ="/Users/hayxe/Desktop/Unknow/Back-end/TextFiles/Uses/";
	String path;
	
	//Defining the path 
	path = extension + getElementName() +".txt";
	System.out.println(path);
	eUses = retriveContentFile(path);
	
	return eUses;
	
	
	
}
//Setter for test for element //FIXED and Optimized
private String getFromDBElementTest(int ElementID, Connection link) throws IOException{
	String eTest;
	String extension = "/Users/hayxe/Desktop/Unknow/Back-end/TextFiles/Test/"; 
	String path; 
	
  //Defining the path 
	path = extension + getElementName() +".txt";
	eTest = retriveContentFile(path);
	
	return eTest;
	
	
}
// Setter for the element's natural color
private String getFromDBColour(int ElementID, Connection link){
		String colour;
		
		try{
			Statement query = link.createStatement();
			ResultSet result = query.executeQuery("SELECT Name FROM Colours WHERE ColourID= "+getFromDBColourID(ElementID,link));
			
			while(result.next()){
				
				colour = result.getString("Name");
				return colour;
			}
		}catch(Exception exc){
			
			exc.printStackTrace();
		}
		
		return "Colour not found";
	}
//Setter for Element's electron configuration
private String getFromDBElectronConfiguration(int ElementID, Connection link){

	String electronConfiguration;
	
	try{
		Statement query = link.createStatement();
		ResultSet result = query.executeQuery("SELECT Configuration FROM ElectronShells WHERE EShell_ID= "+ElementID);
		
		while(result.next()){
			electronConfiguration = result.getString("Configuration");
			//Character Processing
			electronConfiguration = electronConfiguration.replace(",", " ");
			
			return electronConfiguration;	
		}
		
	}catch(Exception exc){
		exc.printStackTrace();
	}
	
	return "No Configuration found";
}
// Setter for Isotope of the element
private static ArrayList<Isotope> getIsotopesFromList(int ElementID, Connection link){
		ArrayList<Isotope> elementIsotopes = new ArrayList();
		
		IsotopesTable isotopesList = new IsotopesTable();
		
	for(Isotope isotope: isotopesList.Isotopes){
			
			if(isotope.ElementID == ElementID){
				
				elementIsotopes.add(isotope);
			}
		}
		
		return elementIsotopes;
		

	}
// Setter for atomic number of the element
private int getFromDBAtomicNumber(int ElementID, Connection link){
	
		int atomicNum;
		
		try{
			Statement query = link.createStatement();
			ResultSet result = query.executeQuery("SELECT AtomicNumber FROM Base WHERE ElementID= "+ElementID);
			
			while(result.next()){
				
				atomicNum = result.getInt("AtomicNumber");
				return atomicNum;
			}
			
		}catch(Exception exc){
			
			exc.printStackTrace();
		}
		
		return 0;
	}
// Setter for the group number of the element 
private int getFromDBGroupNumber(int ElementID, Connection link){
		int groupNum;
		
		try{
			Statement query = link.createStatement();
			ResultSet result = query.executeQuery("SELECT FROM GROUPS Number WHERE G_ID= "+getFromDBGID(ElementID,link));
			
			while(result.next()){
				groupNum = result.getInt("Number");
			}
		}catch(Exception exc){
			
			exc.printStackTrace();
		}
		return 0;
	}
//Setter for the atomicMass 
private double getFromDBAtomicMass(int ElementID, Connection link){
	
	double atomicMass;
	
	try{
		Statement query = link.createStatement();
		ResultSet result = query.executeQuery("SELECT AtomicMass FROM Base WHERE ElementID= "+ ElementID);
		
		while(result.next()){
			
			atomicMass = result.getDouble("AtomicMass");
			return atomicMass;
		}
		
	}catch(Exception exc){
		exc.printStackTrace();
	}
	
	return 0;
}
//Setter for boiling point
private double getFromDBBoilingPoint(int ElementID, Connection link){
	
	double boilingPoint;
	
	try{
		Statement query = link.createStatement();
		ResultSet result = query.executeQuery("SELECT BoilingPoint FROM VitalPoints WHERE VP_ID= "+ElementID);
		
		while(result.next()){
			
			boilingPoint = result.getDouble("BoilingPoint");
			return boilingPoint;
		}
		
	}catch(Exception exc){
		
		exc.printStackTrace();
	}
	
	return 0;
	
}
//Setter for melting point
private double getFromDBMeltingPoint(int ElementID, Connection link){
	
	double meltingPoint;
	
	try{
		
		Statement query = link.createStatement();
		ResultSet result = query.executeQuery("SELECT MeltingPoint FROM VitalPoints WHERE VP_ID= "+ElementID);
		
		while(result.next()){
			meltingPoint = result.getDouble("MeltingPoint");
			return meltingPoint;
		}
		
	}catch(Exception exc){
		
		exc.printStackTrace();
	}
	
	return  0;
}
//Setter for the Triple point temperature if exist (in Kelvin)
private double getFromDBTPTemperature(int ElementID, Connection link){
	
	double temperature;
	
	try{
		Statement query = link.createStatement();
		ResultSet result = query.executeQuery("SELECT Temperature FROM TriplePoints WHERE TriplePointID= "+ElementID);
		
		while(result.next()){
			
			temperature = result.getDouble("Temperature");
			return temperature;
		}
		
	}catch(Exception exc){
		exc.printStackTrace();
	}
	
	return 0;
}
//Setter for the Triple Point Pressure if exist
private double getFromDBTPPressure(int ElementID, Connection link){
	
	double pressure;
	
	try{
		Statement query = link.createStatement();
		ResultSet result = query.executeQuery("SELECT Pressure FROM TriplePoints WHERE TriplePointID= "+ElementID);
		
		while(result.next()){
			
			pressure = result.getDouble("Pressure");
			return pressure;
			
		}
	}catch(Exception exc){
		
		exc.printStackTrace();
	}
	
	return 0;
}
//Setter for submation point if it exist
private double getFromDBSubmationPoint(int ElementID, Connection link){
	double submationPoint;
	
	if(getFromDBBoilingPoint(ElementID,link)==getFromDBMeltingPoint(ElementID,link)){
	
		submationPoint = getFromDBBoilingPoint(ElementID,link);
		return submationPoint;
	}
	
	return 0;
}
//Setter for radioactive boolean
private boolean isRadioactive(int ElementID, Connection link){
	
	boolean elementRadioactive;
	
	try{
		
		Statement query = link.createStatement();
		ResultSet result = query.executeQuery("SELECT Radioactive FROM Base WHERE ElementID= "+ElementID);
		
		while(result.next()){
			
			elementRadioactive = result.getBoolean("Radioactive");
			return elementRadioactive;
		}
	}catch(Exception e){
			
			e.printStackTrace();
			System.err.print("Exception has been caught");
		}
		
		
		
	return false; // Most elements in the periodic table aren't radioactive so this should be the default value 
}

//Getter for element properties
public int getColourID() {
	return ColourID;
}
public String getElementName() {
	return ElementName;
}
public String getSymbol() {
	return Symbol;
}
public String getGroupName() {
	return GroupName;
}
public String getGroupProperties() {
	return GroupProperties;
}
public String getElectronConfiguration() {
	return ElectronConfiguration;
}
public String getTestDescription() {
	return TestDescription;
}
public String getUseDescription() {
	return UseDescription;
}
public String getColour() {
	return Colour;
}
public int getAtomicNumber() {
	return AtomicNumber;
}
public int getGroupNumber() {
	return GroupNumber;
}
public double getAtomicMass() {
	return AtomicMass;
}
public double getMeltingPoint() {
	return MeltingPoint;
}
public double getBoilingPoint() {
	return BoilingPoint;
}
public double getSubmationPoint() {
	return SubmationPoint;
}
public double getTPTemperature() {
	return TPTemperature;
}
public double getTPPressure() {
	return TPPressure;
}
public boolean isRadioactive() {
	return radioactive;
}




}



