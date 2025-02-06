package controller;

import model.Client;
import model.Invoice;
import model.Order;
import shared.InvoiceGenerator;

public class InvoiceController {
	private Invoice invoice ;
	public InvoiceController() {
		
	}
	
	public void generateInvoice(Client loggedInClient, Order order, DBManager dbManager ,MainController mainController) {
		String invoiceNumber = generateInvoiceNumber(loggedInClient.getId(),dbManager);
		InvoiceGenerator invoiceGenerator = new InvoiceGenerator(order, loggedInClient,mainController.getClientCart()) ;
		invoiceGenerator.generateInvoice(invoiceNumber+".pdf");
		Invoice invoice = new Invoice(loggedInClient.getId(),order.getOrderId(), invoiceNumber,invoiceNumber+".pdf",mainController.getClientCart().getTotalPrice(),"Pending");
		dbManager.addInvoice(invoice ,mainController.getCurrentOrder().getOrderId());
		
		
	}
	
	public String generateInvoiceNumber(Long clientId ,DBManager dbManager) {

		String lastInvoiceNumber = dbManager.getLastInvoiceNumberOfDB(clientId);
		int nextSequenceNumber = 1;
		 if (lastInvoiceNumber != null && !lastInvoiceNumber.isEmpty()) {
			 String next = lastInvoiceNumber.split("-")[2];
			 nextSequenceNumber=Integer.parseInt(next)+1;
		 }
		 String formattedSequence = String.format("%03d", nextSequenceNumber);
		 return "INV-CL" + clientId + "-" + formattedSequence;
		
	}


}
