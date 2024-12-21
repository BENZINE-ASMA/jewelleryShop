package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import model.Bijoux;
import model.Cart;
import model.Client;
import model.Order;
import model.OrderStatus;
import view.MainView;

public class MainController {
	private MainView mainView;
	private DBManager dbManager;
	private ArrayList<Bijoux> products;
	private Order currentOrder;


	private InvoiceController invoiceController;

	private Cart clientCart;


	private Client loggedInClient;

	public MainController(MainView mainView, DBManager dbManager) {
		this.mainView = mainView;
		this.dbManager = dbManager;
		this.products= new ArrayList<Bijoux>();
		this.clientCart = new Cart();
		this.invoiceController = new InvoiceController();
	}
	public void initializeDatabase() {
		try {
			dbManager.connect();
			dbManager.executeSQLScript("resources/init.sql");
		} catch (SQLException e) {
			//JOptionPane.showMessageDialog(, "Database initialization failed: " + e.getMessage(),
			//		"Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();

			}

	}

	public void authenticateUser(String username, String password) {
		Client authenticatedUser = dbManager.authenticateUser(username, password);
		if (authenticatedUser != null) {
			this.setLoggedInClient(authenticatedUser);
			mainView.setLoggedInClient(authenticatedUser);
			
			if (authenticatedUser.getRole().equals("admin")) {
				this.createAndShowMaindashboardAdminView();
			}else {
				
				this.createAndShowMaindashboardView();
			}
			
		} else {
			mainView.showAuthenticationError();
		}
	}

	public void fetchAllProducts() {
		this.products =new ArrayList<Bijoux>();
		this.dbManager.getAllProducts(this.products);
	}
	public void displayCart() {
		System.out.println(this.clientCart.toString());
	}

	public void updateUser(Client c) {
		boolean updated = this.dbManager.UpdateUser(c);
		if (updated == true) {

			this.mainView.showEditPofileSucess();
		}else {
			this.mainView.showEditProfileError();
		}
	}
	
	public void deleteUser() {
		boolean rowsAffected = this.dbManager.deleteUser(loggedInClient);
		if(rowsAffected) {
			this.showLoginView(true, "user was successfully deleted");
		}else {
			this.showLoginView(true, "issue with deleting the user");
		}
	}
	public void createAndShowMaindashboardView() {
		mainView.loadMaindashboardView();
		mainView.showPanel("MainDashboard");
	}

	public void createAndShowMaindashboardAdminView() {
		mainView.loadMainDashboardAdminView();
		mainView.showPanel("mainDashboardAdminView");
	}
	public void showMainDashboardView() {
		mainView.showPanel("MainDashboard");
	}

	public void showSignUpView() {
		mainView.loadSignUpView();
		mainView.showPanel("Signup");
	}
	public void showProfileInfoView() {
		mainView.loadProfileInfoView();
		mainView.showPanel("ProfileInfo");
	}
	public void showCartView() {
		mainView.loadCartView();;
		mainView.showPanel("cart");
	}
	
	public void showRingDashboardsView() {
		mainView.loadProductDashbaordView();
		mainView.showPanel("product");
		/*
		mainView.loadRingsDashboardView();
		mainView.showPanel("ringsDashbaord");
		*/
	}
	public void showNecklaceDashboardsView() {
		mainView.loadNecklacesDashboardView();
		mainView.showPanel("necklacesDashbaord");
	}
	
	public void showLoginView(boolean displayOptionPane, String todisplay) {
		mainView.loadLoginView(displayOptionPane,todisplay);
		mainView.showPanel("loginView");
	}
	
	
	
	
	 public void addToCart(Bijoux b) {
		 if(currentOrder == null) {
			 
			 currentOrder = new Order(this.loggedInClient);
		 }
	    	this.clientCart.addToCart(b);
	    }
	 
	 public void addOrderToDB() {
		 this.dbManager.addOrder(currentOrder);
	 }
	 public void ChangeOrderStatus(OrderStatus status) {
		 this.currentOrder.setStatus(status);
		 if (status == OrderStatus.VALIDEE) {
			 this.currentOrder.setCartItems(clientCart);
			 this.addOrderToDB();
			// System.out.println("thisssssss " + this.currentOrder.getStatus() + "    "+ this.currentOrder.getCartItems().toString()); 
		 
			 // we ll generate the invoice 
			 System.out.println("generating invoice for client "+  this.loggedInClient.toString());
			 this.invoiceController.generateInvoice(this.loggedInClient,this.currentOrder,this.dbManager,this);
		 }
	 }
	
	public DBManager getDbManager() {
		return dbManager;
	}

	public void setDbManager(DBManager dbManager) {
		this.dbManager = dbManager;
	}


	public Client getLoggedInClient() {
		return loggedInClient;
	}

	public void setLoggedInClient(Client loggedInClient) {
		this.loggedInClient = loggedInClient;
	}
	public ArrayList<Bijoux> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Bijoux> products) {
		this.products = products;
	}
	
	public Cart getClientCart() {
		return clientCart;
	}

	public void setClientCart(Cart clientCart) {
		this.clientCart = clientCart;
	}
	public Order getCurrentOrder() {
		return currentOrder;
	}

	public void setCurrentOrder(Order currentOrder) {
		this.currentOrder = currentOrder;
	}

	public ArrayList<? extends Bijoux> searchByKey(String query){
		ArrayList<?> result = new ArrayList<>();
		String[] orKeys = query.split("(?i)\\s+ou\\s+");
		for(String orkey : orKeys) {
			String[] andKeys = orkey.split("(?i)\\s+et\\s+");
			ArrayList<?> temp = new ArrayList<>(this.products);
			
			for(String andKey: andKeys ) {
				// gotta finish logic here 
			}
		}
		return null;
	}
	

}
