package controller;

import java.util.ArrayList;
import java.util.List;

import model.*;
import view.MainView;

public class AdminController {
	private ArrayList<Client> clients;
	private ArrayList<Bijoux> products;
	private ArrayList<Invoice> invoices;
	private DBManager dbManager;
	private MainView mainView;
	private Client loggedInAdmin; // Admin session

	public AdminController(DBManager dbManager, MainView mainView) {
		this.mainView = mainView;
		this.clients = new ArrayList<>();
		this.dbManager = dbManager;
		this.loggedInAdmin = null;
	}

	public Client getLoggedInAdmin() {
		return loggedInAdmin;
	}

	public void setLoggedInAdmin(Client admin) {
		this.loggedInAdmin = admin;
	}

	public void logout() {
		this.loggedInAdmin = null;
		this.showLoginView(false, "You have successfully logged out.");
	}

	public ArrayList<Client> fetchAllClients() {
		this.clients = new ArrayList<>();
		dbManager.getClients(clients);
		return this.clients;
	}

	public void updateClient(Client c) {
		this.dbManager.updateClient(c);
	}

	public void addClient(Client c) {
		this.dbManager.addClient(c);
	}

	public void deleteClient(Long id) {
		this.dbManager.deleteCLient(id);
	}

	public void updateProduct(Bijoux b) {
		this.dbManager.updateProduct(b);
	}

	public void addProduct(Bijoux b) {
		this.dbManager.addProduct(b);
	}

	public void deleteProduct(Long id) {
		this.dbManager.deleteProduct(id);
	}

	public ArrayList<Bijoux> fetchAllProducts() {
		this.products = new ArrayList<>();
		this.dbManager.getAllProducts(this.products);
		return this.products;
	}

	public ArrayList<Invoice> fetchAllInvoices() {
		this.invoices = new ArrayList<>();
		this.dbManager.getAllInvoices(this.invoices);
		return this.invoices;
	}

	public Order fetchOrderById(Long orderId) {
		return this.dbManager.fetchOrderById(orderId);
	}
	public String fetchInvoiceById(Long invoiceId){
		return this.dbManager.fetchInvoiceById(invoiceId);
	}
	public void getAllInvoices(ArrayList<Invoice>invoices,Long clientid){
		 this.dbManager.getAllInvoices(invoices,clientid);
	}
	public Invoice fetchInvoiceDetailsById(Long invoiceid){
		return this.dbManager.fetchInvoiceDetailsById(invoiceid);
	}

	public boolean updateCart(Long orderId, Long productId, int newQuantity) {
		return this.dbManager.updateCart(orderId, productId, newQuantity);
	}

	public boolean updateOrder(Long orderId, Double totalPrice) {
		return this.dbManager.updateOrder(orderId, totalPrice);
	}

	public boolean updateInvoice(Long invoiceId, Double newTotalAmount) {
		return this.dbManager.updateInvoice(invoiceId, newTotalAmount);
	}
	public boolean deleteInvoice(Long invoiceId) {
		return this.dbManager.deleteInvoice(invoiceId);
	}

	public boolean deleteFromCart(Long orderId, Long productId) {
		return this.dbManager.deleteFromCart(orderId, productId);
	}

	public void showRingDashboardsView() {
		mainView.loadRingsDashboardView();
		mainView.showPanel("ringsDashbaord");
	}

	public void showInvoiceView() {
		mainView.loadInvoiceDashbaordView();
		mainView.showPanel("InvoiceDashbaord");
	}

	public void showClientsDashbaordView() {
		mainView.loadClientsDashbaordView();
		mainView.showPanel("ClientDashbaord");
	}

	public void showProductsDashbaordView() {
		mainView.loadProductsDashbaordView();
		mainView.showPanel("ProductDashbaord");
	}

	public void ShowMaindashboardAdminView() {
		mainView.loadMainDashboardAdminView();
		mainView.showPanel("mainDashboardAdminView");
	}

	public void showLoginView(boolean displayOptionPane, String todisplay) {
		mainView.loadLoginView(displayOptionPane, todisplay);
		mainView.showPanel("loginView");
	}

	public Client fetchClientById(Long clientId) {
		return dbManager.fetchClientById(clientId);
	}

	public Client fetchClientByEmail(String email) {
		return dbManager.fetchClientByEmail(email);
	}

	public ArrayList<Order> fetchOrdersByClient(Long clientId) {
		return dbManager.fetchOrdersByClient(clientId);
	}



	public Cart fetchCartByOrderId(Long orderId) {
		return dbManager.fetchCart(orderId);
	}
	public ArrayList<Client> fetchClientsByName(String name){
		return this.dbManager.fetchClientsByName(name);
	}
	public boolean saveInvoice(Invoice invoice){
		return this.dbManager.saveInvoice(invoice);
	}

}
