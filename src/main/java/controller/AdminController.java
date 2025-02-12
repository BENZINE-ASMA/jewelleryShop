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
	private Client loggedInAdmin;

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
	/**
	 * Logs out the currently logged-in admin and shows the login view.
	 */
	public void logout() {
		this.loggedInAdmin = null;
		this.showLoginView(false, "You have successfully logged out.");
	}
	/**
	 * Fetches all clients from the database.
	 * @return A list of all clients.
	 */
	public ArrayList<Client> fetchAllClients() {
		this.clients = new ArrayList<>();
		dbManager.getClients(clients);
		return this.clients;
	}
	/**
	 * Updates client information in the database.
	 * @param c The client to update.
	 */
	public void updateClient(Client c) {
		this.dbManager.updateClient(c);
	}
	/**
	 * Adds a new client to the database.
	 * @param c The client to add.
	 */
	public void addClient(Client c) {
		this.dbManager.addClient(c);
	}
	/**
	 * Deletes a client by ID.
	 * @param id The ID of the client to delete.
	 * @return True if the client was successfully deleted, false otherwise.
	 */
	public boolean deleteClient(Long id) {
		return this.dbManager.deleteClient(id);
	}
	/**
	 * Updates a product in the database.
	 * @param b The product to update.
	 */
	public void updateProduct(Bijoux b) {
		this.dbManager.updateProduct(b);
	}
	/**
	 * Adds a new product to the database.
	 * @param b The product to add.
	 * @return True if the product was added successfully, false otherwise.
	 */
	public boolean addProduct(Bijoux b) {
		return this.dbManager.addProduct(b);
	}

	public void deleteProduct(Long id) {
		this.dbManager.deleteProduct(id);
	}
	/**
	 * Fetches all products from the database.
	 * @return A list of all products.
	 */
	public ArrayList<Bijoux> fetchAllProducts() {
		this.products = new ArrayList<>();
		this.dbManager.getAllProductsForAdmin(this.products);
		return this.products;
	}
	/**
	 * Fetches all invoices from the database.
	 * @return A list of all invoices.
	 */
	public ArrayList<Invoice> fetchAllInvoices() {
		this.invoices = new ArrayList<>();
		this.dbManager.getAllInvoices(this.invoices);
		return this.invoices;
	}
	/**
	 * Fetches an order by its ID.
	 * @param orderId The ID of the order.
	 * @return The order object.
	 */
	public Order fetchOrderById(Long orderId) {
		return this.dbManager.fetchOrderById(orderId);
	}
	public String fetchInvoiceById(Long invoiceId){
		return this.dbManager.fetchInvoiceById(invoiceId);
	}
	public void getAllInvoices(ArrayList<Invoice>invoices,Long clientid){
		 this.dbManager.getAllInvoices(invoices,clientid);
	}
	/**
	 * Fetches detailed information about an invoice by its ID.
	 * @param invoiceid The ID of the invoice.
	 * @return The Invoice object.
	 */
	public Invoice fetchInvoiceDetailsById(Long invoiceid){
		return this.dbManager.fetchInvoiceDetailsById(invoiceid);
	}

	/**
	 * Updates the quantity of a product in an order.
	 * @param orderId The order ID.
	 * @param productId The product ID.
	 * @param newQuantity The new quantity to update.
	 * @return True if the update was successful, false otherwise.
	 */
	public boolean updateCart(Long orderId, Long productId, int newQuantity) {
		return this.dbManager.updateCart(orderId, productId, newQuantity);
	}

	/**
	 * Updates the total price of an order.
	 * @param orderId The order ID.
	 * @param totalPrice The new total price.
	 * @return True if the update was successful, false otherwise.
	 */
	public boolean updateOrder(Long orderId, Double totalPrice) {
		return this.dbManager.updateOrder(orderId, totalPrice);
	}
	/**
	 * Updates the total amount of an invoice.
	 * @param invoiceId The invoice ID.
	 * @param newTotalAmount The new total amount.
	 * @return True if the update was successful, false otherwise.
	 */
	public boolean updateInvoice(Long invoiceId, Double newTotalAmount) {
		return this.dbManager.updateInvoice(invoiceId, newTotalAmount);
	}
	/**
	 * Deletes an invoice by its ID.
	 * @param invoiceId The ID of the invoice.
	 * @return True if deletion was successful, false otherwise.
	 */
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
	public boolean deleteItemIfZero(Long orderId, Long productId) {
		return this.dbManager.deleteItemIfZero(orderId,productId);
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

	public Invoice getInvoice(long invoiceId){
		return this.dbManager.getInvoice(invoiceId);
	}
	public void incrementStock(Long productId, int quantity) {
		dbManager.incrementStock(productId, quantity);
	}
	public DBManager getDbManager() {
		return this.dbManager;
	}
}
