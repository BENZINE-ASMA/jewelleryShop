package controller;

import java.util.ArrayList;

import model.Bijoux;
import model.Client;
import model.Invoice;
import model.Order;

public class AdminController {
	private ArrayList<Client> clients;
	private ArrayList<Bijoux> products;
	private ArrayList<Invoice> invoices;
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
	public void updateProduct(Bijoux b ) {
		this.dbManager.updateProduct(b);
	}
	
	public void addProduct(Bijoux b ) {
		this.dbManager.addProduct(b);
	}
	public void deleteProduct(Long id ) {
		this.dbManager.deleteProduct(id);
	}

	public ArrayList<Bijoux> fetchAllProducts() {
		this.products =new ArrayList<Bijoux>();
		this.dbManager.getAllProducts(this.products);
		return this.products;
	}
	public ArrayList<Invoice> fetchAllInvoices() {
		this.invoices =new ArrayList<Invoice>();
		this.dbManager.getAllInvoices(this.invoices);
		return this.invoices;
	}
	public Order fetchOrderById(Long orderId){
		return this.dbManager.fetchOrderById(orderId);
	}

}
