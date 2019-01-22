package com.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

//Works
 public class AdvancedReport extends Report {
	
	   static String choosenLocation; //Stores the location where the user wants to write the pdf too
	   static Element element;
	   
	   //Initializer  
	public AdvancedReport(Element element, String chosenLocation){
		super(element, chosenLocation);
		this.choosenLocation = chosenLocation;
		this.element = element;
		
		
		writeAdvancedreport();
		
	}
	
	
	
	private static void writeAdvancedreport(){
		
		Document advReport = new Document(PageSize.A4); //Creating Basic report document 
		
		try{
			PdfWriter.getInstance(advReport, new FileOutputStream(new File(choosenLocation))); //Creating PDFWriter 
			advReport.open(); //Opening the report so we can modify it 
			
			
			//Title 
			 Font titleFont = new Font(Font.FontFamily.HELVETICA,30,Font.BOLD);
			 Paragraph title = new Paragraph(element.ElementName,titleFont);
			 advReport.add(title); //Add title to document 
			 
			 //Basic
			 Font bodyFont = new Font(Font.FontFamily.HELVETICA, 14); //Creating font for all body Paragraphs
			 Paragraph basicBody = new Paragraph("Name: " + element.ElementName +"\n" + "Symbol: "+ element.Symbol+ "\n" + "Atomic Number: " + element.AtomicNumber + "\n"+ "Relative Atomic Mass: "+ "\n" + element.AtomicMass +
					 "\n" + "Melting Point: "+ element.MeltingPoint + "\n" + "Boiling Point: " + element.BoilingPoint + "\n" + "Group Name: " + element.GroupName + 
					 "\n" + "Triple Point Pressure: " + element.getTPPressure() + "\n" + "Triple Point Temperature :" + element.getTPTemperature(),bodyFont); //Creating basic info paragraph 
			
			
			 advReport.add(basicBody); //Add basicBody to document 
			 
			//Uses Paragraph
			Font subTitle = new Font(Font.FontFamily.HELVETICA,20, Font.BOLDITALIC); //Creating font for all subtitles 
			Paragraph useTitle = new Paragraph("Uses",subTitle); //Creating sub title Uses
			advReport.add(useTitle);  //Add useTitle to document 
			
			 
			Paragraph useBody = new Paragraph(element.getUseDescription(),bodyFont); //Creating uses paragraph
			advReport.add(useBody); // Add useBody to document 
			 
			//Test Paragraph
			//If there isn't a test for the chosen element the following code won't run.
			if (element.getTestDescription() != null) {
				
			    Paragraph testTitle = new Paragraph("Test",subTitle); //Creating sub title Test
			    advReport.add(testTitle); //Add testTitle to document 
			    
				Paragraph testBody = new Paragraph(element.getTestDescription(),bodyFont); //Creating test paragraph 
				advReport.add(testBody); //Add test body to document 
			}
			
			//Group 
			if(!element.getGroupName().equals("Name not found")){
				Paragraph groupTitle = new Paragraph("Group Properties",subTitle); //Creating sub title Group Properties 
				advReport.add(groupTitle); //Add groupTitle to document 
				
				Paragraph groupBody = new Paragraph(element.getGroupProperties(),bodyFont); //Creating group properties paragraph
				advReport.add(groupBody); //Add group body to document 
			}
			
			
			
			advReport.close(); //Closing the document 
			
			
			
		}catch(FileNotFoundException | DocumentException e){
			
		}
		
		
	}
	
	// Create procedure that forms Advanced Report

}