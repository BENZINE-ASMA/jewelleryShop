package controller;

import java.util.ArrayList;

import model.Client;

public class AdminController {
	private ArrayList<Client> clients;
	private DBManager dbManager;
	
	public AdminController(DBManager dbManager) {
		this.clients = new ArrayList<>();
		this.dbManager = dbManager;
	}
	
	public ArrayList<Client> fetchAllClients() {
		dbManager.getClients(clients);
		return this.clients;
	}

	public ArrayList<Client> getClients() {
		return clients;
	}

	public void setClients(ArrayList<Client> clients) {
		this.clients = clients;
	}

}
