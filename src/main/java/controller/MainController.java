package controller;

import java.util.HashMap;

import javax.swing.JOptionPane;

import model.Bijoux;
import model.Cart;
import model.Client;
import view.MainView;

public class MainController {
	private MainView mainView;
	private DBManager dbManager;
	private HashMap<Bijoux,Integer> products;
	private Cart clientCart;

	private Client loggedInClient;

	public MainController(MainView mainView, DBManager dbManager) {
		this.mainView = mainView;
		this.dbManager = dbManager;
		this.products= new HashMap<Bijoux,Integer>();
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
		System.out.println("i got clickd yayay");
		this.dbManager.getAllProducts(this.products);
		for(Bijoux b : this.products.keySet()) {
			System.out.println(b.toString() );
			System.out.println(this.products.get(b));
		}
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
}
