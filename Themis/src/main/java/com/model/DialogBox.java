package com.model;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DialogBox {

	
	public static void display(String header, String message){
		    Stage window = new Stage();
		     window.initModality(Modality.APPLICATION_MODAL);
			
			StackPane stackPane = new StackPane();
			
            //Creating customized layout for JFXDialog
		    JFXDialogLayout contents = new JFXDialogLayout(); //Creating object of JFXDialogLayout class
			contents.setHeading(new Text(header)); //Setting header of dialog
			contents.setBody(new Text(message)); //Placing the message in the body of the dialog
			
			JFXDialog dialog = new JFXDialog(stackPane, contents, JFXDialog.DialogTransition.CENTER); // Creating and initializing the JFXDialog object 

			JFXButton okBtn = new JFXButton("Ok"); //Creating a button for the dialog
			okBtn.setOnAction(e -> dialog.close()); //When the Ok button is clicked the dialog will close
			contents.setActions(okBtn); //Adding  the button to the dialog
			
			dialog.show(); //Showing the Dialog
			
			Scene scene = new Scene(dialog);
			window.setScene(scene);
			window.showAndWait();
			
			
			
		
		

		
	}
}
