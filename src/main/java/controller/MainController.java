package controller;

import javax.swing.JOptionPane;

import model.Client;
import view.MainView;

public class MainController {
	private MainView mainView;
	private DBManager dbManager;
	public DBManager getDbManager() {
		return dbManager;
	}

	public void setDbManager(DBManager dbManager) {
		this.dbManager = dbManager;
	}

	private Client loggedInClient;

	public MainController(MainView mainView, DBManager dbManager) {
		this.mainView = mainView;
		this.dbManager = dbManager;
	}

	public void authenticateUser(String username, String password) {
		Client authenticatedUser = dbManager.authenticateUser(username, password);
		if (authenticatedUser != null) {
			this.setLoggedInClient(authenticatedUser);
			mainView.setLoggedInClient(authenticatedUser);
			this.showMaindashboardView();
		} else {
			mainView.showAuthenticationError();
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
	public void showMaindashboardView() {
		mainView.loadMaindashboardView();
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
	
	

	public Client getLoggedInClient() {
		return loggedInClient;
	}

	public void setLoggedInClient(Client loggedInClient) {
		this.loggedInClient = loggedInClient;
	}
}
