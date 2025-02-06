package view.admin;

import controller.AdminController;
import controller.InvoiceController;
import model.Invoice;
import model.Order;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Timestamp;
import java.util.ArrayList;

public class InvoiceDashboard extends JPanel {
	private JTable invoiceTable;
	private DefaultTableModel tableModel;
	private AdminController adminController;
	private InvoiceController invoiceController;

	public InvoiceDashboard(AdminController adminController, InvoiceController invoiceController) {
		this.adminController = adminController;
		this.invoiceController = invoiceController;
		setLayout(new BorderLayout());

		// Header
		DashboardHeaderAdmin header = new DashboardHeaderAdmin(adminController);
		header.setBorder(new EmptyBorder(0, 0, 5, 0));
		add(header, BorderLayout.NORTH);

		// Table
		tableModel = new DefaultTableModel(new String[]{
				"ID", "ClientId", "OrderId", "InvoiceNumber", "Total",
				"Status", "InvoiceDate", "UpdatedDate", "InvoicePath"}, 0);
		invoiceTable = new JTable(tableModel);
		invoiceTable.setAutoCreateRowSorter(true);
		add(new JScrollPane(invoiceTable), BorderLayout.CENTER);

		// Button Panel
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

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

		JButton displayOrder = new JButton("Display Order");
		displayOrder.addActionListener(e -> displayOrderPanel());

		buttonPanel.add(editInvoice);
		buttonPanel.add(deleteInvoice);
		buttonPanel.add(displayOrder);
		add(buttonPanel, BorderLayout.SOUTH);

		loadInvoiceData();
	}

	public void loadInvoiceData() {
		ArrayList<Invoice> invoices = adminController.fetchAllInvoices();
		tableModel.setRowCount(0);
		for (Invoice invoice : invoices) {
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
		InvoiceDialogView dialog = new InvoiceDialogView(invoice, invoiceController,this.adminController);
		dialog.setSize(400, 300);
		dialog.setLocationRelativeTo(null);
		dialog.setModal(true);
		dialog.setVisible(true);
		loadInvoiceData(); // Refresh after editing
	}

	private void deleteInvoice() {
		int selectedRow = invoiceTable.getSelectedRow();
		if (selectedRow >= 0) {
			int confirmation = JOptionPane.showConfirmDialog(this,
					"Are you sure you want to delete this invoice?",
					"Confirm Deletion", JOptionPane.YES_NO_OPTION);
			if (confirmation == JOptionPane.YES_OPTION) {
				Long invoiceId = (Long) tableModel.getValueAt(selectedRow, 0);
				adminController.deleteInvoice(invoiceId);
				loadInvoiceData();
			}
		} else {
			JOptionPane.showMessageDialog(this, "Please select an invoice to delete.");
		}
	}

	public void displayOrderPanel() {
		int selectedRow = invoiceTable.getSelectedRow();
		if (selectedRow >= 0) {
			Long orderId = (Long) tableModel.getValueAt(selectedRow, 2);
			Long invoiceId = (Long) tableModel.getValueAt(selectedRow, 0);

			Order order = adminController.fetchOrderById(orderId);

			if (order != null) {
				JFrame orderFrame = new JFrame("Order Details");
				orderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				orderFrame.setLayout(new BorderLayout());

				OrderPanel orderPanel = new OrderPanel(order, adminController, invoiceId, this);
				orderFrame.add(orderPanel, BorderLayout.CENTER);

				orderFrame.pack();
				orderFrame.setLocationRelativeTo(null);
				orderFrame.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(this, "No order found for the selected invoice.");
			}
		} else {
			JOptionPane.showMessageDialog(this, "Please select an invoice to view its order.");
		}
	}
}
