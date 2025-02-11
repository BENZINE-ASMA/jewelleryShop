package view.admin;

import controller.AdminController;
import controller.InvoiceController;
import model.Invoice;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InvoiceDialogView extends JDialog {
    private JTextField invoiceNumberField;
    private JTextField filePathField;
    private JTextField totalAmountField;
    private JComboBox<String> statusComboBox;
    private AdminController adminController;
    private Invoice invoice;
    private InvoiceController invoiceController;

    public InvoiceDialogView(Invoice invoice, InvoiceController invoiceController, AdminController adc) {
        this.invoice = invoice;
        this.invoiceController = invoiceController;
        this.adminController =adc;
        this.setLayout(null);
        this.setTitle("Edit Invoice");
        this.setSize(400, 300);
        this.setModal(true);

        JLabel invoiceNumberLabel = new JLabel("Invoice Number:");
        invoiceNumberLabel.setBounds(50, 30, 120, 25);
        this.add(invoiceNumberLabel);


        invoiceNumberField = new JTextField(20);
        invoiceNumberField.setBounds(180, 30, 150, 25);
        invoiceNumberField.setText(invoice != null ? invoice.getInvoiceNumber() : "");
        this.add(invoiceNumberField);
        invoiceNumberField.setEnabled(false);

        JLabel filePathLabel = new JLabel("File Path:");
        filePathLabel.setBounds(50, 70, 120, 25);
        this.add(filePathLabel);

        filePathField = new JTextField(255);
        filePathField.setBounds(180, 70, 150, 25);
        filePathField.setText(invoice != null ? invoice.getFilePath() : "");
        this.add(filePathField);
        filePathField.setEnabled(false);

        JLabel totalAmountLabel = new JLabel("Total Amount:");
        totalAmountLabel.setBounds(50, 110, 120, 25);
        this.add(totalAmountLabel);


        totalAmountField = new JTextField(20);
        totalAmountField.setBounds(180, 110, 150, 25);
        totalAmountField.setText(invoice != null ? String.valueOf(invoice.getTotalAmount()) : "");
        this.add(totalAmountField);
        totalAmountField.disable();

        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setBounds(50, 150, 120, 25);
        this.add(statusLabel);

        statusComboBox = new JComboBox<>(new String[]{"Pending", "Paid", "Overdue"});
        statusComboBox.setBounds(180, 150, 150, 25);
        statusComboBox.setSelectedItem(invoice != null ? invoice.getStatus() : "Pending");
        this.add(statusComboBox);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(100, 200, 80, 25);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveInvoice();
            }
        });
        this.add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(200, 200, 80, 25);
        cancelButton.addActionListener(e -> this.dispose());
        this.add(cancelButton);
    }

    private void saveInvoice() {
        String invoiceNumber = invoiceNumberField.getText();
        String filePath = filePathField.getText();
        double totalAmount = Double.parseDouble(totalAmountField.getText());
        String status = (String) statusComboBox.getSelectedItem();

        invoice.setInvoiceNumber(invoiceNumber);
        invoice.setFilePath(filePath);
        invoice.setTotalAmount(totalAmount);
        invoice.setStatus(status);
        if (adminController.saveInvoice(invoice)) {
            JOptionPane.showMessageDialog(null, "Invoice saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Failed to save the invoice. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        this.dispose();
    }
}
