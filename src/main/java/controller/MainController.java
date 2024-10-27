package controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import model.Bijoux;
import model.Cart;
import model.Client;
import model.Order;
import view.MainView;

public class MainController {
	private MainView mainView;
	private DBManager dbManager;
	private ArrayList<Bijoux> products;
	private Order currentOrder;

	private Cart clientCart;


	private Client loggedInClient;

	public MainController(MainView mainView, DBManager dbManager) {
		this.mainView = mainView;
		this.dbManager = dbManager;
		this.products= new ArrayList<Bijoux>();
		this.clientCart = new Cart();
	}

	public void authenticateUser(String username, String password) {
		Client authenticatedUser = dbManager.authenticateUser(username, password);
		if (authenticatedUser != null) {
			this.setLoggedInClient(authenticatedUser);
			mainView.setLoggedInClient(authenticatedUser);
			this.createAndShowMaindashboardView();
		} else {
			mainView.showAuthenticationError();
		}
	}

	public void fetchAllProducts() {
		this.dbManager.getAllProducts(this.products);
	}
	public void displayCart() {
		System.out.println(this.clientCart.toString());
	}

	public void updateUser(Client c) {
		boolean updated = this.dbManager.UpdateUser(c);
		System.out.println("helloeoeleo" + updated);
		if (updated == true) {

			this.mainView.showEditPofileSucess();
		}else {
			this.mainView.showEditProfileError();
		}
	}
	public void createAndShowMaindashboardView() {
		mainView.loadMaindashboardView();
		mainView.showPanel("MainDashboard");
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
	
	 public void addToCart(Bijoux b) {
		 if(currentOrder ==null) {
			// currentOrder = new Order();
			 
		 }
	    	this.clientCart.addToCart(b);
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


}
