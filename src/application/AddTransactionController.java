package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class AddTransactionController implements Initializable{

    @FXML private Button returnBtn;
    @FXML private TextField itemID;
    @FXML private TextField customerID;
    @FXML private RadioButton seven, fourteen, twentyone;
    @FXML private ToggleGroup days;
    @FXML private Button add;
    @FXML private Label helper;
    

    
    @FXML
    void Add(ActionEvent event) throws NumberFormatException, DuplicateTransactionID {
    	if(event.getSource() == add){
    		if(itemID.getText().isEmpty() || customerID.getText().isEmpty())
    			helper.setText("Must fill in all values.");
    		if(!itemID.getText().isEmpty() && !customerID.getText().isEmpty()){
    			
    			if(!seven.isSelected() && !fourteen.isSelected() && !twentyone.isSelected())
    				helper.setText("Must select number of days.");
    			
    			else{
    				int rentalDays;
    				if(days.getSelectedToggle() == seven)
    					rentalDays = 7;
    				else if(days.getSelectedToggle() == fourteen)
    					rentalDays = 14;
    				else
    					rentalDays = 21;
    				
    				helper.setText("Transaction added!");
    				LibrarySys.addTransaction(LibrarySys.itemMap.get(Integer.parseInt(itemID.getText())), LibrarySys.customerMap.get(Integer.parseInt(customerID.getText())) , rentalDays);
    			}
    			
    		}
    	}
    	
    }

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


	@Override
	public void initialize(URL location, ResourceBundle resources) {}


}

