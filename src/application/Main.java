package application;
	
import java.util.Date;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Navigation.fxml"));
			Scene scene = new Scene(root,600,400);
			primaryStage.setTitle("Queen's Library"); //set name of the application in window
	        
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws DateReturnedBeforeDateRented, TransactionAlreadyClosed, DuplicateTransactionID, DuplicateCustomerID, DuplicateItemID, WrongRentalCost {

		//-------------------------------------------From Previous Assn, Creat new Database----------------------------------------------
		LibrarySys lib = new LibrarySys(); //create a new library database
		
		//__________________________Adding items to the collection__________________________________
		LibrarySys.addItem(new Book("Atlas Shrugged", "Ayn Rand", "New York Print", 1978));
		LibrarySys.addItem(new Device("Kobo EReader", 20));
		LibrarySys.addItem(new Adapter(45, LibrarySys.adapterPrice)); //custom ID constructor
		LibrarySys.addItem(new Adapter(2)); //demonstrate the wrong rental cost transaction
		LibrarySys.addItem(new Textbook(45, "Grade 12 Biology", "Sean Smith, Dan Harbours, Evan King", "Scholastic", 2013)); //demonstrate duplicate ID exception ));
		LibrarySys.addItem(new Laptop("Lenovo Y50", LibrarySys.laptopPrice));
		
		//__________________Creating Customers___________________________________________________
		LibrarySys.addCustomer(6904, "Ted Smith", "Geology", Customer.Type.Employee);
		LibrarySys.addCustomer(6750, "Alana Bethel", "Biology", Customer.Type.Student);
		LibrarySys.addCustomer(5555, "Neil Degrasse Tyson", "Physics", Customer.Type.Employee);
		LibrarySys.addCustomer(5555, "Susan Eren", "Computer Science", Customer.Type.Employee); //demonstrate duplicate ID exception
		
		
		//______________________________Create some transactions______________________________________		
		LibrarySys.addTransaction(LibrarySys.itemMap.get(5), LibrarySys.customerMap.get(5555), 4); //Neil takes the laptop for 4 days
		LibrarySys.addTransaction(LibrarySys.itemMap.get(1), LibrarySys.customerMap.get(5556), 7); //Susan takes Atlas Shrugged for 7 days
		LibrarySys.addTransaction(LibrarySys.itemMap.get(45), LibrarySys.customerMap.get(6904), 1); //Ted takes adaptor for 1 day
		LibrarySys.addTransaction(LibrarySys.itemMap.get(2), LibrarySys.customerMap.get(6904), 5); //Ted takes EReader for 5 days
		LibrarySys.addTransaction(LibrarySys.itemMap.get(4), LibrarySys.customerMap.get(6750), 14); //Alana takes Biology textbook for 2 weeks

		//____________Change dates on when the were taken to demonstrate late + rental fees___________
		LibrarySys.transactionMap.get(1).setRentalTime(new Date(117, 2, 24)); //first transaction happened march 24
		LibrarySys.transactionMap.get(2).setRentalTime(new Date(117, 2, 20)); //second transaction happened march 20
		LibrarySys.transactionMap.get(3).setRentalTime(new Date(117, 2, 23)); //third transaction happened march 23
		LibrarySys.transactionMap.get(4).setRentalTime(new Date(117, 2, 22)); //fourth transaction happened march 22
		LibrarySys.transactionMap.get(5).setRentalTime(new Date(117, 2, 7)); //fifth transaction happened march 7

		//Unfortunately, I need to reset my expected return times since I have changed my rental times as well
		LibrarySys.transactionMap.get(1).setExpectedReturnTime();
		LibrarySys.transactionMap.get(2).setExpectedReturnTime();
		LibrarySys.transactionMap.get(3).setExpectedReturnTime();
		LibrarySys.transactionMap.get(4).setExpectedReturnTime();
		LibrarySys.transactionMap.get(5).setExpectedReturnTime();
		
		//______________Close all transactions and calculate late fees and total fees________________________
		//double lateFees = 0, totFees = 0;
		
		System.out.println();
		for(int i=1;i<6;i++){
			//lateFees += LibrarySys.transactionMap.get(i).getLateFee();
			if(LibrarySys.transactionMap.get(i).isLate()){
				System.out.println(LibrarySys.transactionMap.get(i).toString()); //this will print out all transactions in library
			}
				
		}
		
		/*for(int i=1;i<6;i++){
			totFees += lib.closeTransaction(i);
		}*/
		
		//lib.closeTransaction(3); //demonstrate transaction already closed exception
		
		//System.out.println("Total Late Fees: $" +lateFees+ "\nTotal Fees: $" +totFees);
		//-------------------------------------End of previous Assn-------------------------------------------
		
		launch(args); //launch the GUI
	}
}
