package view.admin;

import controller.AdminController;
import controller.InvoiceController;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;
import java.util.HashMap;
/**
 * This class represents the order panel in the admin view, providing functionalities
 * to manage and validate orders, update product quantities, and handle invoices.
 */
public class OrderPanel extends JPanel {
    private Order order;
    private long invoiceId;
    private AdminController adminController;
    private InvoiceDashboard invoiceDashboard;
    private InvoiceController invoiceController;
    private JButton deleteItemButton;
    private Map<Long, Integer> deletedQuantities = new HashMap<>();


    private Map<Long, Integer> updatedQuantities = new HashMap<>();

    public OrderPanel(Order order, AdminController adminController, long invoiceId, InvoiceDashboard invoiceDashboard) {
        this.order = order;
        this.adminController = adminController;
        this.invoiceId = invoiceId;
        this.invoiceDashboard = invoiceDashboard;
        this.invoiceController = new InvoiceController(null);
        this.setPreferredSize(new Dimension(800, 500));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel orderDetailsPanel = new JPanel();
        orderDetailsPanel.setLayout(new BoxLayout(orderDetailsPanel, BoxLayout.Y_AXIS));
        orderDetailsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));

        addDetail(orderDetailsPanel, "Order ID:", String.valueOf(order.getOrderId()));
        addDetail(orderDetailsPanel, "Order Date:", order.getOrderDate().toString());
        addDetail(orderDetailsPanel, "Client ID:", String.valueOf(order.getClient().getId()));
        addDetail(orderDetailsPanel, "Order Status:", order.getStatus().toString());
        JLabel totalPriceLabel = addDetail(orderDetailsPanel, "Total Price:", String.format("%.2f", order.getCartItems().getTotalPrice()));

        add(orderDetailsPanel, BorderLayout.WEST);

        DefaultTableModel tableModel = createTableModel(order.getCartItems());
        JTable cartTable = new JTable(tableModel);
        cartTable.setRowHeight(25);

        cartTable.getModel().addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();
            if (column == 2) {
                Long productId = (Long) tableModel.getValueAt(row, 0);
                int newQuantity = Integer.parseInt(tableModel.getValueAt(row, column).toString());
                order.getCartItems().modifyQuantityOfProduct(productId, newQuantity);
                double newTotalPrice = order.getCartItems().getTotalPrice();
                totalPriceLabel.setText(String.format("%.2f", newTotalPrice));

                updatedQuantities.put(productId, newQuantity);
            }
        });


        JScrollPane tableScrollPane = new JScrollPane(cartTable);
        add(tableScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton validateOrderButton = new JButton("Validate Order");

        validateOrderButton.addActionListener(e -> {
            order.validateOrder();

            for (Map.Entry<Long, Integer> entry : updatedQuantities.entrySet()) {
                Long productId = entry.getKey();
                int quantity = entry.getValue();
                adminController.updateCart(order.getOrderId(), productId, quantity);

                if (quantity == 0) {
                    adminController.deleteItemIfZero(order.getOrderId(), productId);
                }
            }

            for (Map.Entry<Long, Integer> entry : deletedQuantities.entrySet()) {
                Long productId = entry.getKey();
                int quantityRestored = entry.getValue();
                adminController.incrementStock(productId, quantityRestored);
            }

            adminController.updateInvoice(invoiceId, order.getCartItems().getTotalPrice());
            invoiceDashboard.loadInvoiceData();

            Invoice inv = this.adminController.getInvoice(invoiceId);
            JOptionPane.showMessageDialog(this, "Order validated successfully.");
            Client tosendto = adminController.fetchClientById(inv.getClientId());

            this.invoiceController.setInvoice(inv);
            this.invoiceController.updateInvoice(tosendto, order, order.getCartItems());

            updatedQuantities.clear();
            deletedQuantities.clear();
        });
        deleteItemButton = new JButton("Delete Item");
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

                    updatedQuantities.put(productId, newQuantity);

                    deletedQuantities.put(productId, deletedQuantities.getOrDefault(productId, 0) + 1);
                } else {
                    order.getCartItems().deleteFromCart(productId);
                    tableModel.removeRow(selectedRow);

                    double newTotalPrice = order.getCartItems().getTotalPrice();
                    totalPriceLabel.setText(String.format("%.2f", newTotalPrice));

                    updatedQuantities.put(productId, 0);

                    deletedQuantities.put(productId, deletedQuantities.getOrDefault(productId, 0) + currentQuantity);
                }

                checkAndDisableDeleteButton();
            } else {
                JOptionPane.showMessageDialog(this, "Please select an item to delete.");
            }
        });



        buttonPanel.add(validateOrderButton);
        buttonPanel.add(deleteItemButton);
        add(buttonPanel, BorderLayout.SOUTH);
        checkAndDisableDeleteButton();
    }

    private JLabel addDetail(JPanel panel, String label, String value) {
        JPanel detailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(labelComponent.getFont().deriveFont(Font.BOLD));
        JLabel valueComponent = new JLabel(value);

        detailPanel.add(labelComponent);
        detailPanel.add(valueComponent);
        detailPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(detailPanel);
        return valueComponent;
    }
    /**
     * Checks the order and disables the delete button when there is one item left in this order, it can't be deleted unless you delete the invoice itself
     */
    private void checkAndDisableDeleteButton() {
        long remainingItems = order.getCartItems().getCart().size();
        int totalQuantity = order.getCartItems().getCart().values().stream().mapToInt(Integer::intValue).sum();

        if (remainingItems == 1 && totalQuantity == 1) {
            deleteItemButton.setEnabled(false);
        } else {
            deleteItemButton.setEnabled(true);
        }
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
                return false;
            }
        };
    }

}
