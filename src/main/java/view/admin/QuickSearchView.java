package view.admin;

import controller.AdminController;
import model.Client;
import model.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class QuickSearchView extends JFrame {
    private AdminController adminController;
    private JTextField searchField;
    private JTable resultsTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> searchTypeDropdown;

    // Labels for client details
    private JLabel clientIdValue, clientNameValue, clientEmailValue;
    private JPanel clientInfoPanel;
    private JPanel resultsPanel;

    public QuickSearchView(AdminController adminController) {
        this.adminController = adminController;
        setTitle("Quick Search");
        setSize(700, 500);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        String[] searchOptions = {"Order ID", "Client ID", "Client Name", "Client Email"};
        searchTypeDropdown = new JComboBox<>(searchOptions);
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        searchPanel.add(new JLabel("Search by:"));
        searchPanel.add(searchTypeDropdown);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.NORTH);

        // Client Info Panel
        clientInfoPanel = new JPanel(new GridBagLayout());
        clientInfoPanel.setBorder(BorderFactory.createTitledBorder("Client Information"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Padding

        JLabel clientIdLabel = new JLabel("Client ID:");
        clientIdValue = new JLabel("-");
        JLabel clientNameLabel = new JLabel("Name:");
        clientNameValue = new JLabel("-");
        JLabel clientEmailLabel = new JLabel("Email:");
        clientEmailValue = new JLabel("-");

        // Set Font Styles
        clientIdLabel.setFont(new Font("Arial", Font.BOLD, 12));
        clientNameLabel.setFont(new Font("Arial", Font.BOLD, 12));
        clientEmailLabel.setFont(new Font("Arial", Font.BOLD, 12));

        // Row 1: Client ID
        gbc.gridx = 0; gbc.gridy = 0;
        clientInfoPanel.add(clientIdLabel, gbc);
        gbc.gridx = 1;
        clientInfoPanel.add(clientIdValue, gbc);

        // Row 2: Name
        gbc.gridx = 0; gbc.gridy = 1;
        clientInfoPanel.add(clientNameLabel, gbc);
        gbc.gridx = 1;
        clientInfoPanel.add(clientNameValue, gbc);

        // Row 3: Email
        gbc.gridx = 0; gbc.gridy = 2;
        clientInfoPanel.add(clientEmailLabel, gbc);
        gbc.gridx = 1;
        clientInfoPanel.add(clientEmailValue, gbc);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BorderLayout());
        resultsPanel.add(clientInfoPanel, BorderLayout.NORTH);

        // Results Table
        tableModel = new DefaultTableModel(new String[]{"Order ID", "Order Details"}, 0);
        resultsTable = new JTable(tableModel);
        resultsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultsPanel.add(new JScrollPane(resultsTable), BorderLayout.CENTER);

        add(resultsPanel, BorderLayout.CENTER);

        // Initially hide client info panel
        clientInfoPanel.setVisible(false);

        // Search Button Action
        searchButton.addActionListener(e -> performSearch());

        // Table Click Listener
        resultsTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && resultsTable.getSelectedRow() != -1) {
                handleRowSelection();
            }
        });

        setVisible(true);
    }

    private void performSearch() {
        String searchQuery = searchField.getText().trim();
        String selectedSearchType = (String) searchTypeDropdown.getSelectedItem();
        tableModel.setRowCount(0); // Clear previous results
        clientInfoPanel.setVisible(false); // Hide client info until needed

        if (searchQuery.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search query.", "Input Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        switch (selectedSearchType) {
            case "Order ID":
                searchByOrderId(searchQuery);
                break;
            case "Client ID":
                searchByClientId(searchQuery);
                break;
            case "Client Name":
                searchByClientName(searchQuery);
                break;
            case "Client Email":
                searchByClientEmail(searchQuery);
                break;
        }
    }

    private void searchByOrderId(String orderIdString) {
        try {
            long orderId = Long.parseLong(orderIdString);
            Order order = adminController.fetchOrderById(orderId);
            if (order != null) {
                Client client = adminController.fetchClientById(order.getClient().getId());
                if (client != null) {
                    displayClientInfo(client);
                }
                tableModel.addRow(new Object[]{order.getOrderId(), "Click to view cart"});
            } else {
                JOptionPane.showMessageDialog(this, "No order found with this ID.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Order ID format.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchByClientId(String clientIdString) {
        try {
            long clientId = Long.parseLong(clientIdString);
            Client client = adminController.fetchClientById(clientId);
            if (client != null) {
                displayClientOrders(client);
            } else {
                JOptionPane.showMessageDialog(this, "No client found with this ID.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Client ID format.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchByClientName(String clientName) {
        ArrayList<Client> clients = adminController.fetchClientsByName(clientName);
        if (clients.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No clients found with this name.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Client client : clients) {
                displayClientOrders(client);
            }
        }
    }

    private void searchByClientEmail(String clientEmail) {
        Client client = adminController.fetchClientByEmail(clientEmail);
        if (client != null) {
            displayClientOrders(client);
        } else {
            JOptionPane.showMessageDialog(this, "No client found with this email.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void displayClientInfo(Client client) {
        clientIdValue.setText(String.valueOf(client.getId()));
        clientNameValue.setText(client.getFirstName() + " " + client.getLastName());
        clientEmailValue.setText(client.getEmail());

        clientInfoPanel.setVisible(true); // Show client info
        revalidate();
        repaint();
    }

    private void displayClientOrders(Client client) {
        displayClientInfo(client); // Show client info before listing orders

        ArrayList<Order> orders = adminController.fetchOrdersByClient(client.getId());
        if (orders.isEmpty()) {
            JOptionPane.showMessageDialog(this, "This client has no orders.", "Order Search", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Order order : orders) {
                tableModel.addRow(new Object[]{order.getOrderId(), "Click to view cart"});
            }
        }
    }

    private void handleRowSelection() {
        int selectedRow = resultsTable.getSelectedRow();
        if (selectedRow == -1) return;

        Object idObject = resultsTable.getValueAt(selectedRow, 0);
        if (!(idObject instanceof Long)) return;

        long selectedId = (Long) idObject;
        Order order = adminController.fetchOrderById(selectedId);
        if (order != null) {
            JFrame orderFrame = new JFrame("Order Details");
            orderFrame.setSize(500, 400);
            orderFrame.setLayout(new BorderLayout());
            orderFrame.add(new OrderPanel(order, adminController, selectedId, null), BorderLayout.CENTER);
            orderFrame.setLocationRelativeTo(this);
            orderFrame.setVisible(true);
        }
    }
}
