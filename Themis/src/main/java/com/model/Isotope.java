package com.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

final class Isotope {
	
	int IsotopeID; 
	int ElementID;
	String IsotopeName;
	double relativeIsotopicMass;
	double abundance;
	boolean stable;
	DecayModes decayMode;
	HalfLife halflife;
	
//Initializer
protected Isotope(int IsotopeID){
		
		this.IsotopeID = IsotopeID;
		
		
		try{
			
			Connection linkDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/UknownDB","root","JewishThundercunt"); // Creating Connector to DB. Password and DB Name differs.
			this.IsotopeName = getIsotopeName(IsotopeID,linkDB);
			this.relativeIsotopicMass = getRelativeIsotopicMass(IsotopeID,linkDB);
			this.abundance = getIsotopeAbundance(IsotopeID,linkDB);
			this.stable = getStable(IsotopeID,linkDB);
			this.decayMode = DecayModes.valueOf(getDecayMode(IsotopeID,linkDB));
			this.halflife = getHalfLife(IsotopeID, linkDB);
			this.ElementID = getElementID(IsotopeID,linkDB);
			
			linkDB.clearWarnings();
			linkDB.close();
		}catch(Exception exc){
			
			exc.printStackTrace();
			
		}
		
	}
	
// Enum for modes of Decay	
protected static enum DecayModes{
		
	   
		Alpha,Gamma,BetaPlus,BetaMinus,Proton,SF,Kaon,Stable;
		 
	
	
		
		
	
		
		}

	

//Nested Class to define HalfLife structure
private class HalfLife{
		


		double value;
		String units;
		//Initializer
		private HalfLife(int IsotopeID, Connection link) {
			
		
			this.value = getValue(IsotopeID,link);
			this.units = getUnit(IsotopeID,link);
	
		}
		
// Getter for Half life value	
private double getValue(int IsotopeID, Connection link){
		
		double value;
		
		try{
			Statement query = link.createStatement();
			ResultSet result = query.executeQuery("SELECT Value FROM HalfLife WHERE HalfLifeID= "+getHalfLifeID(IsotopeID,link));
			
			while(result.next()) {
				
				value = result.getDouble("Value");
				return value;
			}
		}catch(Exception exc){
			
			exc.printStackTrace();
		}
		
		return 0;
		
	}
//Getter for Half life unit
private String getUnit(int IsotopeID, Connection link){
		String unit;
		
		try{
			Statement query = link.createStatement();
			ResultSet result = query.executeQuery("SELECT unit FROM HalfLife WHERE HalfLifeID= "+getHalfLifeID(IsotopeID,link));
			
			while(result.next()){
				unit = result.getString("unit");
				return unit;
			}
		}catch(Exception exc){
			exc.printStackTrace();
		}
		
		return null; 
	}
	// Returns a string representation of the HalfLife	
	public String chrHalfLife(){
		
		String halfLife;
		
		halfLife = (this.value + ""+this.units);
		
		return halfLife;
		
	}
	}

//Getter for IDs
private int getDecayModeID(int IsotopeID, Connection link){
	int dMID;
	
	
	try{
		Statement query = link.createStatement();
		ResultSet result = query.executeQuery("SELECT DecayModeID FROM Isotopes WHERE IsotopesID ="+ IsotopeID);
		
		while(result.next()){
			dMID = result.getInt("DecayModeID");
			
			return dMID;
		}
	}catch(Exception exc){
		
		exc.printStackTrace();
	}
	
	return 0;
	
}
private int getHalfLifeID(int HalfLifeID, Connection link){
	int halfLifeID;
	
	try{
		Statement query = link.createStatement();
		ResultSet result = query.executeQuery("SELECT HalfLifeID FROM Isotopes WHERE IsotopesID ="+IsotopeID);
		
		while(result.next()){
			
			halfLifeID = result.getInt("HalfLifeID");
			return halfLifeID;
		}
	}catch(Exception exc){
		exc.printStackTrace();
	}
	
	return 0;
}
private int getElementID(int IsotopeID, Connection link){
		int elementID;
		try{
		Statement query = link.createStatement();
		ResultSet result = query.executeQuery("SELECT ElementID FROM Isotopes WHERE IsotopesID= "+IsotopeID);
		
		while(result.next()){
			elementID = result.getInt("ElementID");
			return elementID;
		}
	}catch(Exception exc){
		exc.printStackTrace();
	}
		return 0;
		}

//Getter for Isotope Name
private String getIsotopeName(int IsotopeID, Connection link){
		String name;
		
		
		try{
		
			Statement query = link.createStatement();
			ResultSet result = query.executeQuery("SELECT Name FROM Isotopes WHERE IsotopesID= "+IsotopeID);
			
			while (result.next()){
				name = result.getString("Name");
				return name;
			}
		}catch(Exception exc){
			exc.printStackTrace();
		}
		
		return "Name not found";
	}
// Getter for Isotopes relative isotopic mass
private double getRelativeIsotopicMass(int IsotopeID, Connection link){

	double RIMass;
	
	try{
		Statement query = link.createStatement();
		ResultSet result = query.executeQuery("SELECT RelativeIsotopicMass FROM Isotopes WHERE IsotopesID= "+ IsotopeID);
		
		while(result.next()){
			
			RIMass = result.getDouble("RelativeIsotopicMass");
			return RIMass;
		}
		}catch(Exception exc){
		
			
			exc.printStackTrace();
		}
		
		return 0;
	}
//Getter for Isotopes abundance 
private double getIsotopeAbundance(int IsotopeID, Connection link){
	
	double iAbundance;
	
	try{
		Statement query = link.createStatement();
		ResultSet result = query.executeQuery("SELECT Abundance FROM Isotopes WHERE IsotopesID ="+IsotopeID);
		
		while(result.next()){
			iAbundance = result.getDouble("Abundance");
			return iAbundance;
		}
		
	}catch(Exception exc){
		
		exc.printStackTrace();
	}
	
	return 0;
}

//Getter for Isotopes state 
private boolean getStable(int IsotopeID, Connection link) {
	boolean stable;
	
	try{
		Statement query = link.createStatement();
		ResultSet result = query.executeQuery("SELECT Stable FROM Isotopes WHERE IsotopesID ="+IsotopeID);
		
		while(result.next()){
			
			stable = result.getBoolean("Stable");
			return stable;
		}
	}catch(Exception exc){
			exc.printStackTrace();
		
		}
	
	return false; //Handle properly
	
}
	
//Getter for Isotopes mode of decay. //Problem fixed
private String getDecayMode(int IsotopeID, Connection link) {
	
	String mode;
	
	try{
		Statement query = link.createStatement();
		ResultSet result = query.executeQuery("SELECT Mode FROM DecayModes WHERE DecayModeID ="+getDecayModeID(IsotopeID,link));
		
		while(result.next()){
			
			mode = result.getString("Mode");
			return mode;
		}
		
	}catch(Exception exc){
		exc.printStackTrace();
	}
	
	return null; // Standard Decay mode
}

//Getter for Isotopes HalfLife
private HalfLife getHalfLife(int IsotopeID, Connection link)  {
	HalfLife halfLife;
	
  halfLife = new HalfLife(IsotopeID,link);
  
  return halfLife;
	
	
	
}
}

//Defining Exceptions thrown because of Isotopes

