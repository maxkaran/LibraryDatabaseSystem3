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
import javafx.stage.Stage;

public class NavigationController implements Initializable {

	@FXML
	private Button addItemButton, addTransactionButton, lateRentalsButton, searchButton;

	@FXML
	void handleNavigationButtonEvent(ActionEvent event) throws IOException {
		Stage stage = null;
		Parent NewScene = null;

		if (event.getSource() == addItemButton) {
			stage = (Stage) addItemButton.getScene().getWindow();
			NewScene = FXMLLoader.load(getClass().getResource("AddItem.fxml"));

		} else if (event.getSource() == addTransactionButton) {
			stage = (Stage) addTransactionButton.getScene().getWindow();
			NewScene = FXMLLoader.load(getClass().getResource("AddTransaction.fxml"));
			
		} else if (event.getSource() == lateRentalsButton) {
			stage = (Stage) lateRentalsButton.getScene().getWindow();
			NewScene = FXMLLoader.load(getClass().getResource("SeeLateRentals.fxml"));
			
		} else if (event.getSource() == searchButton) {
			stage = (Stage) searchButton.getScene().getWindow();
			NewScene = FXMLLoader.load(getClass().getResource("Search.fxml"));
		}

		Scene scene = new Scene(NewScene);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
}
