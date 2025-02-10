package view.admin;

import controller.AdminController;
import controller.InvoiceController;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class OrderPanel extends JPanel {
    private Order order;
    private long invoiceId;
    private AdminController adminController;
    private InvoiceDashboard invoiceDashboard;

    private InvoiceController invoiceController;

    public OrderPanel(Order order, AdminController adminController, long invoiceId, InvoiceDashboard invoiceDashboard) {
        this.order = order;
        this.adminController = adminController;
        this.invoiceId = invoiceId;
        this.invoiceDashboard = invoiceDashboard;
        this.invoiceController = new InvoiceController(null);
        this.setPreferredSize(new Dimension(800,500));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around the panel

        // Left panel for order details
        JPanel orderDetailsPanel = new JPanel();
        orderDetailsPanel.setLayout(new BoxLayout(orderDetailsPanel, BoxLayout.Y_AXIS));
        orderDetailsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10)); // Add space between details and table

        addDetail(orderDetailsPanel, "Order ID:", String.valueOf(order.getOrderId()));
        addDetail(orderDetailsPanel, "Order Date:", order.getOrderDate().toString());
        addDetail(orderDetailsPanel, "Client ID:", String.valueOf(order.getClient().getId()));
        addDetail(orderDetailsPanel, "Order Status:", order.getStatus().toString());
        JLabel totalPriceLabel = addDetail(orderDetailsPanel, "Total Price:", String.format("%.2f", order.getCartItems().getTotalPrice()));

        add(orderDetailsPanel, BorderLayout.WEST);

        // Table for cart items
        DefaultTableModel tableModel = createTableModel(order.getCartItems());
        JTable cartTable = new JTable(tableModel);
        cartTable.setRowHeight(25);

        cartTable.getModel().addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();
            if (column == 2) { // Quantity column
                Long productId = (Long) tableModel.getValueAt(row, 0);
                int newQuantity = Integer.parseInt(tableModel.getValueAt(row, column).toString());
                order.getCartItems().modifyQuantityOfProduct(productId, newQuantity);
                double newTotalPrice = order.getCartItems().getTotalPrice();
                totalPriceLabel.setText(String.format("%.2f", newTotalPrice));

                adminController.updateCart(order.getOrderId(), productId, newQuantity);
                adminController.updateOrder(order.getOrderId(), newTotalPrice);
                adminController.updateInvoice(invoiceId, newTotalPrice);
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(cartTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton validateOrderButton = new JButton("Validate Order");
        validateOrderButton.addActionListener(e -> {
            order.validateOrder();
            adminController.updateInvoice(invoiceId, order.getCartItems().getTotalPrice());
            invoiceDashboard.loadInvoiceData();
            Invoice inv = this.adminController.getInvoice(invoiceId);
            JOptionPane.showMessageDialog(this, "Order validated successfully.");
            this.invoiceController.setInvoice(inv);
            this.invoiceController.updateInvoice(order.getClient(),order,order.getCartItems());

        });

        JButton deleteItemButton = new JButton("Delete Item");
        deleteItemButton.addActionListener(e -> {
            int selectedRow = cartTable.getSelectedRow();
            if (selectedRow != -1) {
                Long productId = (Long) tableModel.getValueAt(selectedRow, 0);
                int currentQuantity = (Integer) tableModel.getValueAt(selectedRow, 2);

                if (currentQuantity > 1) {
                    int newQuantity = currentQuantity - 1;
                    tableModel.setValueAt(newQuantity, selectedRow, 2);
                    order.getCartItems().modifyQuantityOfProduct(productId, newQuantity);

                    double newTotalPrice = order.getCartItems().getTotalPrice();
                    totalPriceLabel.setText(String.format("%.2f", newTotalPrice));
                    adminController.updateCart(order.getOrderId(), productId, newQuantity);
                    adminController.updateOrder(order.getOrderId(), newTotalPrice);
                    adminController.updateInvoice(invoiceId, newTotalPrice);

                } else {
                    order.getCartItems().deleteFromCart(productId);
                    double newTotalPrice = order.getCartItems().getTotalPrice();
                    totalPriceLabel.setText(String.format("%.2f", newTotalPrice));

                    adminController.deleteFromCart(order.getOrderId(), productId);
                    adminController.updateOrder(order.getOrderId(), newTotalPrice);
                    adminController.incrementStock(productId, currentQuantity);
                    tableModel.removeRow(selectedRow);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an item to delete.");
            }
        });


        buttonPanel.add(validateOrderButton);
        buttonPanel.add(deleteItemButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JLabel addDetail(JPanel panel, String label, String value) {
        JPanel detailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(labelComponent.getFont().deriveFont(Font.BOLD)); // Make label bold
        JLabel valueComponent = new JLabel(value);

        detailPanel.add(labelComponent);
        detailPanel.add(valueComponent);
        detailPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(detailPanel);
        return valueComponent;
    }

    private DefaultTableModel createTableModel(Cart cart) {
        String[] columns = {"Product ID", "Product Name", "Quantity", "Price Per Unit", "Total Price"};
        Object[][] data = new Object[cart.getCart().size()][columns.length];

        int i = 0;
        for (Map.Entry<Bijoux, Integer> entry : cart.getCart().entrySet()) {
            Bijoux product = entry.getKey();
            int quantity = entry.getValue();
            double price = product.getPrice();
            data[i++] = new Object[]{product.getId(), product.getName(), quantity, price, quantity * price};
        }

        return new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };
    }
}
