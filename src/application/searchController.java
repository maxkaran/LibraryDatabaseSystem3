package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class searchController {

    @FXML private ToggleGroup type;
    @FXML private TextField itemID1, transactionID, title, customerID, ItemID;
    @FXML private Button returnBtn, searcher;
    @FXML private AnchorPane searchResults;
    @FXML private RadioButton item, transaction;
    @FXML private Label label1;

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
    void search(ActionEvent event) {
	RadioButton chk = (RadioButton)item.getToggleGroup().getSelectedToggle(); //gets the currently selected toggle for the group that radio button book is in
    	
    	if(event.getSource() == searcher){
    		if(chk == item){
    			    			
    			if(itemID1.getText().isEmpty() && title.getText().isEmpty())
    				label1.setText("You must enter a value to search for!");
    			else if(!(itemID1.getText().isEmpty()) && title.getText().isEmpty()){ //if there is an ID value, but no name
    				int itemID = Integer.parseInt(itemID1.getText()); //parses ID val to integer
    				
    				if(LibrarySys.itemMap.containsKey(itemID))
    					label1.setText(LibrarySys.itemMap.get(itemID).toString()); //prints out the item if it is found
    				else
    					label1.setText("There is no Item with this ID");
    			}	
    			else if(itemID1.getText().isEmpty() && !(title.getText().isEmpty())){//if there is a name, but no ID value
    				boolean titlefound = false; //flag that shows if the title has been found    
    				for(int key : LibrarySys.itemMap.keySet()){//iterate through hashmap
    				    if(LibrarySys.itemMap.get(key).getName().equals(title.getText())){ //compare search name with this items title
    				    	label1.setText(LibrarySys.itemMap.get(key).toString());//prints out the matching item
    				    	titlefound = true;
    				    	break;
    				    } 
    				}
    				if(titlefound == false)
    					label1.setText("An item with this title was not found");
    			}
    			else if(!(itemID1.getText().isEmpty()) && !(title.getText().isEmpty())){//there is both a title AND an ID
    				boolean found = false; //flag to tell if item was found
    				int itemID = Integer.parseInt(itemID1.getText()); //parses ID val to integer
    				if(LibrarySys.itemMap.containsKey(itemID)){ //see if item ID is in hash map
    					if(title.getText().equals(LibrarySys.itemMap.get(itemID).getName())){//see if title matches search term
    						label1.setText(LibrarySys.itemMap.get(itemID).toString()); //display item if found
    						found = true; //show item was found
    					}
    				}
    				if(found == false)
    					label1.setText("Item was not found");
    			}
    		}
    		else if(chk == transaction){
    			if(ItemID.getText().isEmpty() && transactionID.getText().isEmpty() && customerID.getText().isEmpty())
    				label1.setText("You must enter a value to search for!");
    			else if(!ItemID.getText().isEmpty() && transactionID.getText().isEmpty() && customerID.getText().isEmpty()){//only item ID available
    				boolean found = false; //flag that shows if the transaction has been found    
    				for(int key : LibrarySys.transactionMap.keySet()){//iterate through hashmap
    				    if(LibrarySys.transactionMap.get(key).getRentedItem().getID() == Integer.parseInt(ItemID.getText())){ //compare search ID with this items ID
    				    	label1.setText(LibrarySys.transactionMap.get(key).toString());//prints out the matching item
    				    	found = true;
    				    	break;
    				    } 
    				}
    				if(found == false)
    					label1.setText("A transaction that contained an item with this ID was not found.");
    			}
    			else if(ItemID.getText().isEmpty() && transactionID.getText().isEmpty() && !customerID.getText().isEmpty()){//only customer ID available
    				boolean found = false; //flag that shows if the transaction has been found    
    				for(int key : LibrarySys.transactionMap.keySet()){//iterate through hashmap
    				    if(LibrarySys.transactionMap.get(key).getCustomer().getID() == Integer.parseInt(customerID.getText())){ //compare search ID with this customers ID
    				    	label1.setText(LibrarySys.transactionMap.get(key).toString());//prints out the matching item
    				    	found = true;
    				    	break;
    				    } 
    				}
    				if(found == false)
    					label1.setText("A transaction that contained an customer with this ID was not found.");
    			}
    			else if(ItemID.getText().isEmpty() && !transactionID.getText().isEmpty() && customerID.getText().isEmpty()){//only transaction ID is available
    				if(LibrarySys.transactionMap.containsKey(Integer.parseInt(transactionID.getText()))){
    					label1.setText(LibrarySys.transactionMap.get(Integer.parseInt(transactionID.getText())).toString());//prints out the transaction if found
    				}
    				else{
    					label1.setText("Transaction was not found.");
    				}
    			}
    			else if(ItemID.getText().isEmpty() && !transactionID.getText().isEmpty() && !customerID.getText().isEmpty()){ //no item ID
    				int transID = Integer.parseInt(transactionID.getText());
    				if(LibrarySys.transactionMap.containsKey(transID)){
    					if(LibrarySys.transactionMap.get(transID).getCustomer().getID() == Integer.parseInt(customerID.getText()))
    						label1.setText(LibrarySys.transactionMap.get(transID).toString());
    					else
    						label1.setText("Item not found.");
    				}else
						label1.setText("Item not found.");
    			}
    			
    			else if(!ItemID.getText().isEmpty() && !transactionID.getText().isEmpty() && customerID.getText().isEmpty()){ //no customer ID
    				int transID = Integer.parseInt(transactionID.getText());
    				if(LibrarySys.transactionMap.containsKey(transID)){
    					if(LibrarySys.transactionMap.get(transID).getRentedItem().getID() == Integer.parseInt(ItemID.getText()))
    						label1.setText(LibrarySys.transactionMap.get(transID).toString());
    					else
    						label1.setText("Item not found.");
    				}else
						label1.setText("Item not found.");
    			}
    			else if(!ItemID.getText().isEmpty() && transactionID.getText().isEmpty() && !customerID.getText().isEmpty()){
    				boolean found = false; //flag that shows if the transaction has been found    
    				for(int key : LibrarySys.transactionMap.keySet()){//iterate through hashmap
    				    if(LibrarySys.transactionMap.get(key).getCustomer().getID() == Integer.parseInt(customerID.getText())){ //compare search ID with this customers ID
    				    	if(LibrarySys.transactionMap.get(key).getRentedItem().getID() == Integer.parseInt(ItemID.getText())){
    				    		label1.setText(LibrarySys.transactionMap.get(key).toString());//prints out the matching item
        				    	found = true;
        				    	break;
    				    	}
    				    } 
    				}
    				if(found == false)
    					label1.setText("A transaction that contained an customer with this ID was not found.");
    			}
    			else{
    				boolean found = false;
    				int transID = Integer.parseInt(transactionID.getText());
    				if(LibrarySys.transactionMap.containsKey(transID)){
    					if(LibrarySys.transactionMap.get(transID).getRentedItem().getID() == Integer.parseInt(ItemID.getText())){
    						if(LibrarySys.transactionMap.get(transID).getCustomer().getID() == Integer.parseInt(customerID.getText())){
    							label1.setText(LibrarySys.transactionMap.get(transID).toString());
    							found = true;
    						}
    					}
    				}
    				if(found == false)
    					label1.setText("Transaction not found.");
    			}
    		}
    		
    		
    		else if(chk == null){
    			label1.setText("Must select what type of item this is.");
    			
    		}
    		
    	}
    }

}

