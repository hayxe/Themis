package com.model;


import java.util.ArrayList;
import java.util.List;

public final class PeriodicTable {




  List<Element> Elements; //List to hold all element objects created for records in database
	
	public PeriodicTable(){
		
		this.Elements = new ArrayList<Element>(); //Initializing Elements as empty ArrayList of type element
for(int ElementID =1; ElementID < 119; ElementID++){
			
			Elements.add(new Element(ElementID));
		}


	}
	public static void main(String[] args) {
	
		
	new PeriodicTable();	//Start initializer	
		
	}

	}
