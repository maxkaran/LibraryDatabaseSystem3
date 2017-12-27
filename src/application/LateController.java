package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class LateController implements Initializable{

    @FXML
    private Button returnBtn;

    @FXML
    private ListView<String> list = new ListView<String>();

    @FXML
    private Button searcher;

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
    void Search(ActionEvent event) {
    	if(event.getSource() == searcher){
    		for(int key : LibrarySys.transactionMap.keySet()){//iterate through hashmap
    			if(LibrarySys.transactionMap.get(key).isLate()){
    				list.getItems().add("Transaction ID: "+ key);
    				
    			}
    		}
    	}

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {}

}

