package com.model;

import java.util.ArrayList;
import java.util.List;



public final class IsotopesTable {

 static List<Isotope> Isotopes; //Stores Isotope objects that are created for records in the isotope table in the database 
	
//Initializer 
 public IsotopesTable(){
	 
	 this.Isotopes = new ArrayList<Isotope>();
	 //Dynamically generating isotope objects 
	for( int IsotopeID =1; IsotopeID <10; IsotopeID++){
			
			Isotopes.add(new Isotope(IsotopeID));
			
		}
 }
	
 
	}