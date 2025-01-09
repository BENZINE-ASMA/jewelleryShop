package controller;

import java.util.ArrayList;

import model.Bijoux;
import model.Client;
import model.Invoice;
import model.Order;
import view.MainView;

public class AdminController {
	private ArrayList<Client> clients;
	private ArrayList<Bijoux> products;
	private ArrayList<Invoice> invoices;
	private DBManager dbManager;
	private MainView mainView;
	public DBManager getDbManager() {
		return dbManager;
	}

	public void setDbManager(DBManager dbManager) {
		this.dbManager = dbManager;
	}

	public AdminController(DBManager dbManager, MainView mainView) {
		this.mainView=mainView;
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

	public boolean updateCart(Long orderId, Long productId , int newQuantity){
		return this.dbManager.updateCart(orderId,productId,newQuantity);
	}
	public boolean updateOrder(Long orderId, Double totalPrice){
		return this.dbManager.updateOrder(orderId,totalPrice);
	}
	public boolean updateInvoice(Long invoiceId, Double newTtalAmount){
		return this.dbManager.updateInvoice(invoiceId,newTtalAmount);
	}
	public boolean deleteFromCart (Long orderId, Long productId){
		return this.dbManager.deleteFromCart(orderId,productId);
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

	}
