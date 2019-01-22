package com.model;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

//Works
public class BasicReport extends Report{

   static String choosenLocation; //Stores the location where the user wants to write the pdf too
   static Element element;
 
	
	//Initializer
	public BasicReport(Element element, String chosenLocation) {
		
	 super(element, chosenLocation); //Initializing super 
	 this.choosenLocation = chosenLocation;
	 this.element = element;
		
	 writeBasicReport();
	}

	
	
	
	//Method that creates basic Report
	private static void writeBasicReport(){
		 Document bReport =  new Document(PageSize.A4); // Creating a  basic Report document 
		 
		 try{
			 
			 PdfWriter.getInstance(bReport, new FileOutputStream(new File(choosenLocation))); //Creating a pdfWriter
			 bReport.open(); // Opening the report so we can modify it
			 
			 //Title 
			 Font titleFont = new Font(Font.FontFamily.HELVETICA,30,Font.BOLDITALIC);
			 Paragraph title = new Paragraph(element.ElementName,titleFont);
			 
			
			 
			 //Body
			 Font bodyFont = new Font(Font.FontFamily.HELVETICA, 14);
			 Paragraph body = new Paragraph("Name: " + element.ElementName +"\n" + "Symbol: "+ element.Symbol+ "\n" + "Atomic Number: " + element.AtomicNumber + "\n"+ "Relative Atomic Mass: "+ "\n" + element.AtomicMass +
					 "\n" + "Melting Point: "+ element.MeltingPoint + "\n" + "Boiling Point: " + element.BoilingPoint + "\n" + "Group Name: " + element.GroupName + 
					 "\n" + "Triple Point Pressure: " + element.getTPPressure() + "\n" + "Triple Point Temperature :" + element.getTPTemperature(),bodyFont);
			 
			 bReport.add(title);
			 bReport.add(body);
			 bReport.close(); //Closing Report
			 
			 
		 }catch(FileNotFoundException | DocumentException e){
			 e.printStackTrace();
		 }
		 
		 
		
		
	}
	
	
}
