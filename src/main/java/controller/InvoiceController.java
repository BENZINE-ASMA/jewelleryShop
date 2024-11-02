package controller;

import model.Client;
import model.Invoice;
import model.Order;
import shared.InvoiceGenerator;

public class InvoiceController {
	private Invoice invoice ;
	
	
	
	public InvoiceController() {
		
	}
	
	public void generateInvoice(Client loggedInClient, Order order, DBManager dbManager) {
		String invoiceNumber = generateInvoiceNumber(loggedInClient.getId(),dbManager);
		InvoiceGenerator invoiceGenerator = new InvoiceGenerator(order, loggedInClient) ;
		invoiceGenerator.generateInvoice(invoiceNumber+".pdf");
		Invoice invoice = new Invoice(loggedInClient.getId(), invoiceNumber,invoiceNumber+".pdf",order.getCartItems().getTotalPrice(),"Pending");
		dbManager.addInvoice(invoice);
		// public Invoice(int clientId, String invoiceNumber, String filePath, double totalAmount, String status) {
		
	}
	
	public String generateInvoiceNumber(Long clientId ,DBManager dbManager) {
		
		// the syntax taht m choosing is INV-CLclientID-001
		String lastInvoiceNumber = dbManager.getLastInvoiceNumberOfDB();
		int nextSequenceNumber = 1;
		 if (lastInvoiceNumber != null && !lastInvoiceNumber.isEmpty()) {
			 String next = lastInvoiceNumber.split("-")[2];
			 nextSequenceNumber=Integer.parseInt(next)+1;
		 }
		 String formattedSequence = String.format("%03d", nextSequenceNumber);
		 return "INV-CL" + clientId + "-" + formattedSequence;
		
	}

}
