package controller;

import model.Cart;
import model.Client;
import model.Invoice;
import model.Order;
import shared.EmailSender;
import shared.InvoiceGenerator;

/**
 * This class manages invoice-related operations such as generating,
 * updating, and retrieving invoices for orders.
 */
public class InvoiceController {
	private Invoice invoice ;
	EmailSender emailSender;
	public InvoiceController(Invoice invoice) {
		this.invoice = invoice;
		this.emailSender = new EmailSender("");
	}
	/**
	 * Generates an invoice for a given order and stores it in the database.
	 * @param loggedInClient The client associated with the invoice.
	 * @param order The order for which the invoice is generated.
	 * @param dbManager The database manager.
	 * @param mainController The main controller for accessing cart information.
	 */
	public void generateInvoice(Client loggedInClient, Order order, DBManager dbManager ,MainController mainController) {
		String invoiceNumber = generateInvoiceNumber(loggedInClient.getId(),dbManager);
		String messageText = "Hello " + loggedInClient.getFirstName() + ",\n\n"
				+ "Thank you for your purchase at Precious.\n"
				+ "Please find attached the invoice corresponding to your order.\n\n"
				+ "Best regards,\n"
				+ "The Precious Team";
		InvoiceGenerator invoiceGenerator = new InvoiceGenerator(order, loggedInClient,mainController.getClientCart()) ;
		invoiceGenerator.generateInvoice(invoiceNumber+".pdf",messageText);
		Invoice invoice = new Invoice(loggedInClient.getId(),order.getOrderId(), invoiceNumber,invoiceNumber+".pdf",mainController.getClientCart().getTotalPrice(),"Pending");
		dbManager.addInvoice(invoice ,mainController.getCurrentOrder().getOrderId());

	}
	/**
	 * Updates an existing invoice with new order and cart details.
	 * @param loggedInClient The client associated with the invoice.
	 * @param order The order whose invoice is updated.
	 * @param cart The updated cart information.
	 * @throws IllegalStateException If no existing invoice is available to update.
	 */
	public void updateInvoice(Client loggedInClient, Order order, Cart cart) {
		if (this.invoice == null) {
			throw new IllegalStateException("No existing invoice to update.");
		}

		String existingInvoicePath = this.invoice.getFilePath();
		String invoiceNumber = this.invoice.getInvoiceNumber();


		InvoiceGenerator invoiceGenerator = new InvoiceGenerator(order, loggedInClient, cart);
		String messageText = "Hello " + loggedInClient.getFirstName() + ",\n\n"
				+ "Thank you for your purchase at Precious.\n"
				+ "Please find attached the invoice corresponding to your order.\n\n"
				+ "Best regards,\n"
				+ "The Precious Team";
		invoiceGenerator.generateInvoice(existingInvoicePath,messageText);

		this.invoice.setStatus("Updated");
	}



	/**
	 * Generates a unique invoice number based on the client's ID and the last recorded invoice number.
	 * @param clientId The client's unique ID.
	 * @param dbManager The database manager to retrieve the last invoice number.
	 * @return A formatted invoice number string.
	 */
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
	public Invoice getInvoice() {
		return this.invoice;
	}

	public void setInvoice(final Invoice invoice) {
		this.invoice = invoice;
	}


}