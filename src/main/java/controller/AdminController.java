package controller;

import java.util.ArrayList;

import model.Client;

public class AdminController {
	private ArrayList<Client> clients;
	private DBManager dbManager;
	
	public DBManager getDbManager() {
		return dbManager;
	}

	public void setDbManager(DBManager dbManager) {
		this.dbManager = dbManager;
	}

	public AdminController(DBManager dbManager) {
		this.clients = new ArrayList<>();
		this.dbManager = dbManager;
	}
	
	public ArrayList<Client> fetchAllClients() {
		this.clients = new ArrayList<Client>();
		dbManager.getClients(clients);
		return this.clients;
	}

	public ArrayList<Client> getClients() {
		return clients;
	}

	public void setClients(ArrayList<Client> clients) {
		this.clients = clients;
	}
	
	public void updateClient(Client c ) {
		this.dbManager.updateClient(c);
	}
	
	public void addClient(Client c ) {
		this.dbManager.addClient(c);
	}
	public void deleteClient(Long id ) {
		this.dbManager.deleteCLient(id);
	}

}
