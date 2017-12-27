package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class AddItemController implements Initializable {
	
	
	@FXML private Button returnBtn, add;
    @FXML private ToggleGroup type;
    @FXML private RadioButton book, device;
    @FXML private TextField bookID, deviceID, bookTitle, deviceTitle, rentalCost, authors, year, publisher;
    @FXML private Label helper;

    @FXML
    void Return(ActionEvent event) throws IOException {
    	if(event.getSource() == returnBtn){
    		Stage stage = (Stage) returnBtn.getScene().getWindow();
			Parent NewScene = FXMLLoader.load(getClass().getResource("Navigation.fxml"));
			Scene scene = new Scene(NewScene);
			stage.setScene(scene);
			stage.show();
    	}
    }
    
    @FXML
    void Add(ActionEvent event) throws DuplicateItemID, WrongRentalCost {
    	RadioButton chk = (RadioButton)book.getToggleGroup().getSelectedToggle(); //gets the currently selected toggle for the group that radio button book is in
    	
    	if(event.getSource() == add){
    		if(chk == device){
    			//read values from text fields
    			int itemID = Integer.parseInt(deviceID.getText());
    			String name = deviceTitle.getText();
    			double rent = Double.parseDouble(rentalCost.getText());
    			
    			if(deviceID.getText() == "")//if id is not provided
    				LibrarySys.addItem(new Device(name, rent));//a unique id is generated
    			else
    				LibrarySys.addItem(new Device(itemID, name, rent)); //otherwise, provided ID is used
    			
    			helper.setText("Device added succesfully!");
    		}
    		else if(chk == book){
    			int itemID = Integer.parseInt(bookID.getText());
    			String name = bookTitle.getText();
    			String author = authors.getText();
    			int years = Integer.parseInt(year.getText());
    			String publishers = publisher.getText();
    			
    			if(bookID.getText().isEmpty())
    				LibrarySys.addItem(new Book(name, author, publishers, years));
    			else
    				LibrarySys.addItem(new Book(itemID, name, author, publishers, years));
    			
    			helper.setText("Book added succesfully!");
    			
    		}
    		else if(chk == null){
    			helper.setText("Must select what type of item this is.");
    			
    		}
    		
    	}
    }
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
	}
}
