package com.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.locks.Condition;
import java.util.stream.Collectors;
import javax.lang.model.util.Elements;
import javafx.beans.property.StringProperty;


public class SeacrhQuery {
	
   Properties property;
   Object value;
	String elementName;
	String symbol;
	String groupName;
	String electronConfiguration;
	int atomicNumber;
	int groupNumber;
	double relativeAtomicMass;
	double boilingPoint;
	double meltingPoint;
	double triplePointTemperature;
	double triplePointPressure;
	boolean radioactive; //ignore
	

	//Initiliser requires two parameters 
	public SeacrhQuery(String strproperty,Object value){
		
		this.property = Properties.valueOf(strproperty);
		this.value = value;
	
		switch(property){
		
		case Name:
			this.elementName = (String)value;
			break;
		case Symbol:
			this.symbol = (String)value;
			break;
		case GroupName:
			this.groupName = (String)value;
		    break;
		case ElectronConfiguration:
			this.electronConfiguration = (String)value;
			break;
		case AtomicNumber:
			this.atomicNumber = Integer.parseInt( value.toString());
			break;
		case GroupNumber:
			this.groupNumber = Integer.parseInt((String) value);
			break;
		case AtomicMass:
			this.relativeAtomicMass = Double.parseDouble((String)value);
			break;
		case BoilingPoint:
			this.boilingPoint = Double.parseDouble((String)value);
			break;
		case MeltingPoint:
			this.meltingPoint = Double.parseDouble((String)value);
			break;
		case TriplePointTemperature:
			this.triplePointTemperature = Double.parseDouble((String)value);
			break;
		case TriplePointPressure:
			this.triplePointPressure = Double.parseDouble((String)value);
			break;
		
			
			
		
			// Default case not necessary as I will validate input using the controller
		}
		
		

	}

	//Enum to define properties that can be used to search for elements	
enum Properties {
		
		Name,Symbol,GroupName,GroupNumber,ElectronConfiguration,AtomicNumber,AtomicMass,BoilingPoint,MeltingPoint,Radioactive,TriplePointTemperature,TriplePointPressure
		
		
	}
	

//Interface to define Search Conditions (Optimzation)
interface SearchCondition{
	
	boolean filtercondition(Element element);

	
}
	
	@SuppressWarnings("rawtypes")
	

	
	//Optimization Search function definitions (Fully Optimized)
		private  <T> ArrayList<Element> filterByCondition(ArrayList<Element> Elements, SearchCondition condition){
		ArrayList<Element> result = new ArrayList<Element>();
	
		//using Java 8 streams 
//Changes Elements to stream and filters through based on the condition passed in the argument then collects the filtered elements converts it to list and then casted back to ArrayList 
	 result.addAll((ArrayList<Element>) Elements.stream().filter( e -> condition.filtercondition(e)).collect(Collectors.toList())); 
	return result; 
	 		
	 	}

	//Runs query by Element Name(Optimized)
	private ArrayList<Element> searchByElementName(ArrayList<Element> Elements,String elementName) {
		
		return filterByCondition(Elements, element -> (element.ElementName.equals(elementName))); //Lambda Java 8

	}
   
	//Runs query by Symbol(Optimized)
	private ArrayList<Element> searchBySymbol(ArrayList<Element> Elements,String symbol){
		return filterByCondition(Elements, element -> (element.Symbol.equals(symbol)));
	}
	
	//Runs query by Group Name(Optimized)
	private ArrayList<Element>searchByGroupName(ArrayList<Element> Elements,String groupName){
		return filterByCondition(Elements, element -> (element.GroupName.equals(groupName)));
	}

	//Runs query by ELectron Configuration(Optimized)
	private ArrayList<Element> searchByElectronConfiguration (ArrayList<Element> Elements,String electronConfiguration){
		return filterByCondition(Elements, element -> (element.ElectronConfiguration.equals(electronConfiguration)));
	}
	
	//Runs query by Atomic Number (Optomized using binary search)
	private ArrayList<Element> searchByAtomicNumber(ArrayList<Element> Elements,int atomicNumber){
	
		
		return bSearch(Elements, atomicNumber);

	}

	//Runs query by Group Number
	private ArrayList<Element> searchByGroupNumber(ArrayList<Element> Elements,int groupNumber){
		return filterByCondition(Elements, element -> (element.GroupNumber == groupNumber));
	}

	//Runs query by Relative Atomic Mass
	private ArrayList<Element> searchByRelativeAtomicMass(ArrayList<Element> Elements,double relativeAtomicMass){
		
		
		return filterByCondition(Elements, element -> (element.AtomicMass == relativeAtomicMass));
		 
		
	
	}
   
	//Runs query by Boiling point(Optimized)
	private ArrayList<Element> searchByBoilingPoint(ArrayList<Element> Elements,double boilingPoint){

		return filterByCondition(Elements, element -> (element.BoilingPoint == boilingPoint));
	}
	
	//Runs query by Melting point(Optimized)
	private ArrayList<Element> searchByMeltingPoint(ArrayList<Element> Elements,double meltingPoint){
		return filterByCondition(Elements, element -> (element.MeltingPoint == meltingPoint));
	}
	
	//Runs query by triple point temperature(Optimized)
	private ArrayList<Element> searchByTriplePointTemperature(ArrayList<Element> Elements,double triplePointTemperature){
		

		return filterByCondition(Elements, element -> (element.TPTemperature == triplePointTemperature));
	}
	
	//Runs query by triple point pressure(Optimized)
	private ArrayList<Element> searchByTriplePointPressure(ArrayList<Element> Elements,double triplePointPressure){
		return filterByCondition(Elements, element -> (element.TPPressure == triplePointPressure));
	}
	
	//Runs query by Radio-activity(Optimized)
	private ArrayList<Element> searchByRadioactivity(ArrayList<Element> Elements,boolean Radioactive){

		return filterByCondition(Elements, element -> (element.radioactive == Radioactive));
	}

	//Optimization Binary Search defining function
	private static  ArrayList<Element> bSearch( ArrayList<Element> Elements,  int left,  int right,  int value){
		
		ArrayList<Element> result = new ArrayList<Element>();
		
		
		int mid = (left + right)/2;

			
		if (Elements.get(mid).AtomicNumber == value){  
				
				result.add(Elements.get(mid));
				return result;
				
			}else if(Elements.get(mid).AtomicNumber > value){
				return bSearch(Elements, left, mid - 1, value);
				
				
			}else{
				return bSearch(Elements, mid + 1, right, value);

			}
		}

    //Optimization Binary Search implementing function
	private static ArrayList<Element> bSearch(ArrayList<Element> Elements, int value  ){
		return bSearch(Elements, 0, Elements.size() -1, value );
	}
	
	// Runs the relevant query depending on the property selected 
	public ArrayList<Element> Search(){
		PeriodicTable pTable = new PeriodicTable();
		ArrayList<Element> results = new ArrayList<Element>();
		ArrayList<Element> elements = new ArrayList<Element>();
		elements = (ArrayList<Element>)pTable.Elements;
		
		
		switch(property){
		
		
		case Name:
			results = searchByElementName(elements, elementName);
			return results;
			
		case Symbol:
			results = searchBySymbol(elements, symbol);
			return results;
			
		case GroupName:
			results = searchByGroupName(elements, groupName);
			return results;
		
		case ElectronConfiguration:
			results = searchByElectronConfiguration(elements, electronConfiguration);
			return results;
		
		case AtomicNumber:
			results = searchByAtomicNumber(elements,atomicNumber);
			return results;
	
		case GroupNumber:
			results = searchByGroupNumber(elements, groupNumber);
			return results;
		
		case AtomicMass:
			results = searchByRelativeAtomicMass(elements, relativeAtomicMass);
			return results;
		
		case BoilingPoint:
			results = searchByBoilingPoint(elements, boilingPoint);
			return results;
		
		case MeltingPoint:
			results = searchByMeltingPoint(elements, meltingPoint);
			return results;
		
		case TriplePointTemperature:
			results = searchByTriplePointTemperature(elements, triplePointTemperature);
			return results;
		
		case TriplePointPressure:
			results = searchByTriplePointPressure(elements, triplePointPressure);
			return results;
			
		case Radioactive:
			results = searchByRadioactivity(elements, radioactive);
			return results;
			
			
			
		}
		
		
		return results; // Returns an Empty ArrayList
	}


}

