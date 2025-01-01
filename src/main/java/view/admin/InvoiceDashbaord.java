package view.admin;

import controller.AdminController;
import controller.InvoiceController;
import model.Client;
import model.Invoice;
import model.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Timestamp;


public class InvoiceDashbaord extends JPanel{
	private JTable invoiceTable;
	private DefaultTableModel tableModel;
	private AdminController adminController;

	public InvoiceDashbaord(AdminController adminController) {
		this.adminController =adminController;
		setLayout(new BorderLayout());

		tableModel = new DefaultTableModel(new String[]{
				"ID", "ClientId", "OrderId", "InvoiceNumber", "Total",
				"Status", "InvoiceDate", "UpdatedDate", "InvoicePath"}, 0);
		invoiceTable = new JTable(tableModel);
		invoiceTable.setAutoCreateRowSorter(true); // Enable sorting
		add(new JScrollPane(invoiceTable), BorderLayout.CENTER);

		// Button Panel
		JPanel buttonPanel = new JPanel();
		JButton editInvoice = new JButton("Edit Invoice");
		editInvoice.addActionListener(e -> {
			int selectedRow = invoiceTable.getSelectedRow();
			if (selectedRow != -1) {
				openEditInvoiceView(getInvoiceFromTable(selectedRow));
			} else {
				JOptionPane.showMessageDialog(this, "Please select an invoice to edit.");
			}
		});
		JButton deleteInvoice = new JButton("Delete Invoice");
		deleteInvoice.addActionListener(e -> deleteInvoice());
		buttonPanel.add(editInvoice);
		buttonPanel.add(deleteInvoice);
		add(buttonPanel, BorderLayout.SOUTH);

		JButton displayOrderButton = new JButton("Display Corresponding Order");
		displayOrderButton.addActionListener(e -> displayOrderPanel());
		buttonPanel.add(displayOrderButton);



		loadInvoiceData();
	}

	private void loadInvoiceData() {
		tableModel.setRowCount(0);
		for (Invoice invoice : adminController.fetchAllInvoices()) {
			tableModel.addRow(new Object[]{
					invoice.getInvoiceId(), invoice.getClientId(), invoice.getOrderId(),
					invoice.getInvoiceNumber(), invoice.getTotalAmount(),
					invoice.getStatus(), invoice.getInvoiceDate(),
					invoice.getUpdateDate(), invoice.getFilePath()
			});
		}
	}

	private Invoice getInvoiceFromTable(int rowIndex) {
		return new Invoice(
				(Long) tableModel.getValueAt(rowIndex, 0),
				(Long) tableModel.getValueAt(rowIndex, 1),
				(Long) tableModel.getValueAt(rowIndex, 2),
				(String) tableModel.getValueAt(rowIndex, 3),
				(String) tableModel.getValueAt(rowIndex, 8),
				(Double) tableModel.getValueAt(rowIndex, 4),
				(String) tableModel.getValueAt(rowIndex, 5),
				(Timestamp) tableModel.getValueAt(rowIndex, 6),
				(Timestamp) tableModel.getValueAt(rowIndex, 7)
		);
	}

	private void openEditInvoiceView(Invoice invoice) {
		InvoiceDialogView dialog = new InvoiceDialogView(invoice, new InvoiceController());
		dialog.setSize(400, 300);
		dialog.setLocationRelativeTo(null);
		dialog.setModal(true);
		dialog.setVisible(true);
		loadInvoiceData(); // Refresh after closing
	}

	private void deleteInvoice() {
		int selectedRow = invoiceTable.getSelectedRow();
		if (selectedRow >= 0) {
			int confirmation = JOptionPane.showConfirmDialog(this,
					"Are you sure you want to delete this invoice?",
					"Confirm Deletion", JOptionPane.YES_NO_OPTION);
			if (confirmation == JOptionPane.YES_OPTION) {
				Long id = (Long) tableModel.getValueAt(selectedRow, 0);
				//adminController.deleteInvoice(id);
				loadInvoiceData();
			}
		} else {
			JOptionPane.showMessageDialog(this, "Please select an invoice to delete.");
		}
	}
	/*public void displayOrderPanel(){
		int selectedRow = this.invoiceTable.getSelectedRow();
		if(selectedRow >=0){
			Long orderId = (Long) this.invoiceTable.getValueAt(selectedRow,2);
			long InvoiceId = (Long) this.invoiceTable.getValueAt(selectedRow,0);
			Order orderToDisplay = this.adminController.fetchOrderById(orderId);

			OrderPanel orderPanel = new OrderPanel(orderToDisplay);
			JFrame frame = new JFrame("Order Details");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.add(orderPanel);
			frame.pack();
			frame.setVisible(true);
		}
	}*/
	public void displayOrderPanel() {
		int selectedRow = this.invoiceTable.getSelectedRow();
		if (selectedRow >= 0) {
			Long orderId = (Long) this.invoiceTable.getValueAt(selectedRow, 2);
			long invoiceId = (Long) this.invoiceTable.getValueAt(selectedRow, 0);

			// Fetch the order by ID
			Order orderToDisplay = this.adminController.fetchOrderById(orderId);

			// Create or update the JFrame to hold the OrderPanel
			JFrame orderFrame = new JFrame("Order Details");
			orderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Close on clicking the "X"
			orderFrame.setLayout(new BorderLayout());

			// Create the OrderPanel and add it to the frame
			OrderPanel orderPanel = new OrderPanel(orderToDisplay);
			orderFrame.add(orderPanel, BorderLayout.CENTER);

			// Make the JFrame fit the size of the content and set it visible
			orderFrame.pack();
			orderFrame.setLocationRelativeTo(null); // Center the frame on the screen
			orderFrame.setVisible(true);  // Make the frame visible
		} else {
			System.out.println("No row selected in the invoice table");
		}
	}



}
