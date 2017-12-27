package application;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;

import application.Customer.Type;

public class LibrarySys {
	
	final static double laptopPrice = 35; //price to rent laptops per day in dollars
	final static double adapterPrice = 8; //price to rent adapters per day in dollars
	
	//_________________Various HashMaps + ArrayList_____________________________________________________
	
	static HashMap<Integer, Customer> customerMap = new HashMap<Integer, Customer>();
	static HashMap<Integer, Rental> transactionMap = new HashMap<Integer, Rental>();
	static HashMap<Integer, Item> itemMap = new HashMap<Integer, Item>();
	
	static ArrayList<Rental> rentals = new ArrayList<Rental>();//ArrayList is necessary for various search functions
	
	//____________________Adding Items, Customers and Transactions Methods Below_________________________
	
	public static void addItem(Item item) throws DuplicateItemID, WrongRentalCost{
		try{	
				if(itemMap.containsKey(item.getID())) //check if this ID is already in use
						throw new DuplicateItemID();
				itemMap.put(item.getID(), item);
				
				if((item instanceof Laptop && ((Laptop) item).getRentalCost() != laptopPrice) || (item instanceof Adapter && ((Adapter) item).getRentalCost() != adapterPrice)){
					throw new WrongRentalCost();
				}
						
				
		}		
		catch(DuplicateItemID e){//will generate a new ID for the item, if chosen ID already exists
			while(itemMap.containsKey(item.getID())){
				item.setID(item.getNextID());
			}
			
			//once unique ID has been generated, add to itemMap
			itemMap.put(item.getID(), item);
			System.out.println("Duplicate Item ID: Unique ID has been generated.");
		}
		catch(WrongRentalCost e){ //this will correct the rental cost to the standard price
			if(item instanceof Laptop)
				((Laptop) item).setRentalCost(laptopPrice);
			else if(item instanceof Adapter)
				((Adapter) item).setRentalCost(adapterPrice);
			
			System.out.println("This is not the correct rental cost, the cost has been set to the standard price.");
		}
	}
	
	public static void addCustomer(int iD, String name, String department, Type type) throws DuplicateCustomerID{
		Customer newCustomer = new Customer(iD, name, department, type);
		try{
			if(customerMap.containsKey(iD)) //check if this ID is already in use
				throw new DuplicateCustomerID();
			
			customerMap.put(newCustomer.getID(), newCustomer);
		}
		catch(DuplicateCustomerID e){
			while(customerMap.containsKey(iD)){
				iD++;
			}
			
			newCustomer.setID(iD); //sets unique ID for the customer
			
			//once unique ID has been generated, add to itemMap
			customerMap.put(iD, newCustomer);
			System.out.println("Duplicate Customer ID: Unique ID has been generated.");
		}
	}
	
	public static void addTransaction(Item rentedItem, Customer customer , int rentalDays) throws DuplicateTransactionID{
		Rental newRental = new Rental(rentedItem, customer , rentalDays);
		try{
			if(transactionMap.containsKey(newRental.getTransactionID()))
				throw new DuplicateTransactionID();
			
			transactionMap.put(newRental.getTransactionID(), newRental);
			rentals.add(newRental); //adds to arraylist for search purposes
		}
		catch(DuplicateTransactionID e){
			System.out.println("Transaction Creation Failed: Duplicated Transaction ID.");
		}
		

	}
	
	//__________________________File Reading Methods_____________________________________________________________
	public void addItems(String fileName) throws DuplicateItemID, WrongRentalCost {
		try {
			// Initializing file reading requirements
			File file = new File(fileName);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			String className;
			StringTokenizer stk;
			Item item = null;

			// Generate new item for every line read
			while ((line = bufferedReader.readLine()) != null) {

				stk = new StringTokenizer(line, ",");

				// Get class name and create new object of that class
				className = stk.nextToken();

				if (className.equals("Book") || className.equals("Textbook") || className.equals("Magazine")) {
					Integer id = Integer.parseInt(stk.nextToken());
					String name = stk.nextToken();
					String authors = stk.nextToken();
					String publishers = stk.nextToken();
					Integer year = Integer.parseInt(stk.nextToken());

					switch (className) {
					case "Book": {
						Book book = new Book(id, name, authors, publishers, year);
						item = book;
						break;
					}

					case "Textbook": {
						Textbook tbk = new Textbook(id, name, authors, publishers, year);
						item = tbk;
						break;
					}
					case "Magazine": {
						Magazine mag = new Magazine(id, name, authors, publishers, year);
						item = mag;
						break;
					}
					}// end switch
				} else if (className.equals("Device") || className.equals("Laptop") || className.equals("Adaptor")) {
					Integer id = Integer.parseInt(stk.nextToken());
					String name = stk.nextToken();
					double cost = Double.parseDouble(stk.nextToken());

					switch (className) {
					case "Device": {
						Device dev = new Device(id, name, cost);
						item = dev;
						break;
					}
					case "Laptop": {
						Laptop lap = new Laptop(id, name, cost);
						item = lap;
						break;
					}
					case "Adaptor": {
						Adapter adp = new Adapter(id, cost);
						item = adp;
						break;
					}
					}// end switch
				} // end else if
				addItem(item);
				stringBuffer.append(line);
				stringBuffer.append("\n");
			} // end while
			fileReader.close();
		} // end try
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addCustomers(String fileName) throws DuplicateCustomerID {
		try {
			// Initializing file reading requirements
			File file = new File(fileName);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			StringTokenizer stk;

			// Generate new item for every line read
			while ((line = bufferedReader.readLine()) != null) {

				stk = new StringTokenizer(line, ",");

				Integer id = Integer.parseInt(stk.nextToken());
				String name = stk.nextToken();
				String department = stk.nextToken();
				String type = stk.nextToken();

				if (type.equals("Employee")) {
					addCustomer(id, name, department, Customer.Type.Employee);

				} else if (type.equals("Student")) {
					addCustomer(id, name, department, Customer.Type.Student);
				} else {
					System.out.println("Customer type not specified");
				}

				stringBuffer.append(line);
				stringBuffer.append("\n");
			} // end while
			fileReader.close();
			bufferedReader.close();
		} // end try
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addTransactions(String fileName) throws ParseException, DuplicateTransactionID,
			DateReturnedBeforeDateRented {
		try {
			// Initializing file reading requirements
			File file = new File(fileName);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			StringTokenizer stk;
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);

			Integer transactionId = null;
			Integer id = null;
			Item item = null;
			Customer customer = null;
			Date rentalDate = null;
			Date returnDate = null;
			Date estimatedReturnDate = null;
			String date = null;
			String day = null;
			
			int rentalDays = (int) ((returnDate.getTime()-rentalDate.getTime())/Rental.getDAY());

			while ((line = bufferedReader.readLine()) != null) {

				stk = new StringTokenizer(line, ",");

				transactionId = Integer.parseInt(stk.nextToken());
				if (transactionMap.containsKey(transactionId)) {
					throw new DuplicateTransactionID();
				} else {

					// Retrieving item from itemID
					id = Integer.parseInt(stk.nextToken());

					if (itemMap.containsKey(id)) {
						item = itemMap.get(id);
					} else {// ===ERROR
						System.out.println("ERROR! Item is not in library system.");
					}

					// Retrieving customer from customerID
					id = Integer.parseInt(stk.nextToken());

					if (customerMap.containsKey(id)) {
						customer = customerMap.get(id);
					} else {// ===ERROR
						System.out.println("ERROR! Customer is not in library system");
					}

					// Getting Dates
					date = stk.nextToken();
					rentalDate = format.parse(date);

					date = stk.nextToken();
					estimatedReturnDate = format.parse(date);

					date = stk.nextToken();
					stk = new StringTokenizer(date, "/");
					day = stk.nextToken();

					if (day.equals("00")) {
						returnDate = null;
					} else {
						returnDate = format.parse(date);
					}

					// creating new rental
					Rental rental = new Rental(item, customer, rentalDays);

					this.transactionMap.put(transactionId, rental);
				} // end if
				stringBuffer.append(line);
				stringBuffer.append("\n");
			} // end while
			fileReader.close();
			bufferedReader.close();

		} // end try

		catch (IOException e) {
			e.printStackTrace();

		}

	}
	
	//_______________________________________Search Methods_____________________________________________________
	
	
	
	//____________________Method for a returned item that returns the cost for late and rental fees______________

	public double closeTransaction(int transactionID) throws DateReturnedBeforeDateRented, TransactionAlreadyClosed{
		double cost;
		
		Rental rental = transactionMap.get(transactionID); //finding the rented item in the hashmap
		
		try{
			if(rental.getStatus() == Rental.Status.CLOSED) //throws exception if transaction is already closed
				throw new TransactionAlreadyClosed();
			
			if(rental.getRentalTime().after(new Date())) //throws exception if return date is before rental date
				throw new DateReturnedBeforeDateRented();
			
			rental.itemReturned();
			
			cost = rental.getLateFee();
			cost += rental.getRentalCosts();
		}
		catch(DateReturnedBeforeDateRented e){
			System.out.println("Date returned is before the date item was rented.");
			cost = 0;
		}		
		catch(TransactionAlreadyClosed e){
			System.out.println("This transaction is already closed.");
			cost = 0;
		}
		
		return cost;
	}
	
	public ArrayList<Rental> searchByDate(String startDate, String endDate) throws ParseException{ //formatting for dates is mm/dd/yyyy
		ArrayList<Rental> search = new ArrayList<Rental>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
		
		Date dateS = sdf.parse(startDate);
		Date dateE = sdf.parse(endDate);
		
		for(Rental rent : rentals){
			if(rent.getRentalTime().after(dateS) && rent.getRentalTime().before(dateE))
				search.add(rent);
		}
		
		return search;
	}
}
