package view;

import controller.MainController;
import model.Order;
import model.Bijoux;
import model.Cart;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class MyOrdersView extends JPanel {
    private MainController mainController;
    private JTable ordersTable;
    private DefaultTableModel tableModel;

    public MyOrdersView(MainController mainController) {
        this.mainController = mainController;
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("My Orders", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Order ID", "Date", "Status", "Total"}, 0);
        ordersTable = new JTable(tableModel);
        loadOrders(mainController.getLoggedInClient().getId());

        ordersTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && ordersTable.getSelectedRow() != -1) {
                int selectedRow = ordersTable.getSelectedRow();
                Long orderId = (Long) tableModel.getValueAt(selectedRow, 0);
                displayOrderDetails(orderId);
            }
        });

        add(new JScrollPane(ordersTable), BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> mainController.showMainDashboardView());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadOrders(Long clientId) {
        ArrayList<Order> orders = mainController.fetchOrdersByClient(clientId);
        tableModel.setRowCount(0);
        for (Order order : orders) {
            tableModel.addRow(new Object[]{order.getOrderId(), order.getOrderDate(), order.getStatus(), order.getCartItems().getTotalPrice()});
        }
    }

    private void displayOrderDetails(Long orderId) {
        Order order = mainController.fetchOrderById(orderId);
        if (order == null) {
            JOptionPane.showMessageDialog(this, "Order not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog orderDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Order Details", true);
        orderDialog.setSize(600, 400);
        orderDialog.setLayout(new BorderLayout());

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.add(new JLabel("Order ID: " + order.getOrderId()));
        detailsPanel.add(new JLabel("Order Date: " + order.getOrderDate()));
        detailsPanel.add(new JLabel("Status: " + order.getStatus()));

        DefaultTableModel itemTableModel = new DefaultTableModel(new String[]{"Product Name", "Quantity", "Price", "Total"}, 0);
        JTable itemsTable = new JTable(itemTableModel);

        Cart cart = order.getCartItems();
        for (Map.Entry<Bijoux, Integer> entry : cart.getCart().entrySet()) {
            Bijoux product = entry.getKey();
            int quantity = entry.getValue();
            itemTableModel.addRow(new Object[]{product.getName(), quantity, product.getPrice(), quantity * product.getPrice()});
        }

        orderDialog.add(detailsPanel, BorderLayout.NORTH);
        orderDialog.add(new JScrollPane(itemsTable), BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> orderDialog.dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        orderDialog.add(buttonPanel, BorderLayout.SOUTH);

        orderDialog.setLocationRelativeTo(this);
        orderDialog.setVisible(true);
    }
}
