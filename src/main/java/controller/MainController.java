package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import Exceptions.DatabaseConnectionException;
import Exceptions.SQLExecutionException;
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
	private Cart preLoginCart;


	private Client loggedInClient;

	public MainController(MainView mainView, DBManager dbManager) {
		this.mainView = mainView;
		this.dbManager = dbManager;
		this.products= new ArrayList<Bijoux>();
		this.clientCart = new Cart();
		this.preLoginCart = new Cart();
		this.invoiceController = new InvoiceController(null);
	}
	public void initializeDatabase() {
		try {
			dbManager.connect();
			executeSQLScript("resources/init.sql");

		} catch (DatabaseConnectionException e) {
			JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage(),
					"Database Error", JOptionPane.ERROR_MESSAGE);
		} catch (SQLExecutionException e) {
			JOptionPane.showMessageDialog(null, "SQL execution error: " + e.getMessage(),
					"SQL Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public boolean isItemInCart(Bijoux bijoux) {
		for (Bijoux b : clientCart.getCart().keySet()) {
			if (b.getId().equals(bijoux.getId())) {
				return true;
			}
		}
		return false;
	}

	public void authenticateUser(String username, String password) {
		preLoginCart = new Cart();
		preLoginCart.setCart(new HashMap<>(clientCart.getCart()));


		Client authenticatedUser = dbManager.authenticateUser(username, password);

		if (authenticatedUser != null) {
			this.setLoggedInClient(authenticatedUser);
			mainView.setLoggedInClient(authenticatedUser);

			if (authenticatedUser.getRole().equals("ADMIN")) {
				mainView.getAdminController().setLoggedInAdmin(authenticatedUser);
				this.createAndShowMaindashboardAdminView();
			} else {
				this.createAndShowMaindashboardView();
			}


			restoreCartAfterLogin();
		} else {
			mainView.showAuthenticationError();
		}
	}
	public void executeSQLScript(String path){
		this.dbManager.executeSQLScript(path);
		this.dbManager.executeSQLScript(path);
	}
	private void restoreCartAfterLogin() {
		HashMap<Bijoux, Integer> updatedCart = new HashMap<>();

		for (Map.Entry<Bijoux, Integer> entry : preLoginCart.getCart().entrySet()) {
			Long oldBijouxId = entry.getKey().getId();
			int quantity = entry.getValue();

			for (Bijoux newBijoux : products) {
				if (newBijoux.getId().equals(oldBijouxId)) {
					updatedCart.put(newBijoux, quantity);
					break;
				}
			}
		}

		clientCart.setCart(updatedCart);
	}


	public void fetchAllFilteredProducts(ArrayList<Bijoux> filteredDashboard,String name2, String description2,
										 String brand2, String type2, String pricemin2,String pricemax2, String material2){
		this.dbManager.getAllFilteredProducts(filteredDashboard,name2,description2,brand2,type2,pricemin2,pricemax2,material2);
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
	public void logout() {

		this.loggedInClient = null;
		this.clientCart.getCart().clear();
		this.currentOrder = null;
		this.showLoginView(false, "You have successfully logged out.");
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

	public void createAndShowMyOrdesView(){
		mainView.loadMyOrdersView();

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
		mainView.loadRingsDashboardView();
		mainView.showPanel("ringsDashbaord");
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
		if (currentOrder != null && loggedInClient != null) {
			currentOrder.setClient(loggedInClient);
			dbManager.addOrder(currentOrder);
		} else {
			System.out.println("Order or Client is null. Cannot add order to DB.");
		}
	}

	public void saveCartInDB(Long orderId) {

		this.dbManager.saveCart(clientCart,orderId);
	}
	public void ChangeOrderStatus(OrderStatus status) {
		if (currentOrder == null) {
			currentOrder = new Order(loggedInClient);
		}

		currentOrder.setStatus(status);

		if (status == OrderStatus.VALIDEE) {
			currentOrder.setCartItems(clientCart);
			addOrderToDB();
			saveCartInDB(currentOrder.getOrderId());
			System.out.println("Generating invoice for client: " + loggedInClient);
			invoiceController.generateInvoice(loggedInClient, currentOrder, dbManager, this);
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

	public List<String> getDistinctMaterials(){
		return this.dbManager.getDistinctMaterials();
	}
	/*ended up not suing search input but rather a panel
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
	*/
	public void decrementStockForConfirmedOrder(Cart cart) {
		Map<Long, Integer> productQuantities = new HashMap<>();

		for (Map.Entry<Bijoux, Integer> entry : cart.getCart().entrySet()) {
			Bijoux bijoux = entry.getKey();
			int quantityPurchased = entry.getValue();

			productQuantities.put(bijoux.getId(), quantityPurchased);
		}

		dbManager.decrementStock(productQuantities);
	}


	public ArrayList<Order> fetchOrdersByClient(Long clientId){
		return this.dbManager.fetchOrdersByClient(clientId);
	}
	public Order fetchOrderById(Long id){
		return this.dbManager.fetchOrderById(id);
	}




}
