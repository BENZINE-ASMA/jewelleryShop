package view.admin;

import controller.AdminController;
import controller.InvoiceController;
import model.Client;
import model.Invoice;
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
    private InvoiceController invoiceController ;

    public QuickSearchView(AdminController adminController) {
        this.adminController = adminController;
        this.invoiceController = new InvoiceController(null);
        setTitle("Quick Search");
        setSize(700, 500);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        String[] searchOptions = {"Order ID", "Invoice ID", "Client ID", "Client Email"};
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
        tableModel = new DefaultTableModel(new String[]{"ID", "Type", "Details"}, 0);
        resultsTable = new JTable(tableModel);
        resultsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultsPanel.add(new JScrollPane(resultsTable), BorderLayout.CENTER);

        add(resultsPanel, BorderLayout.CENTER);

        clientInfoPanel.setVisible(false);

        searchButton.addActionListener(e -> performSearch());

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
        tableModel.setRowCount(0);
        clientInfoPanel.setVisible(false);

        if (searchQuery.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search query.", "Input Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        switch (selectedSearchType) {
            case "Order ID":
                searchByOrderId(searchQuery);
                break;
            case "Invoice ID":
                searchByInvoiceId(searchQuery);
                break;
            case "Client ID":
                searchByClientId(searchQuery);
                break;
            case "Client Email":
                searchByClientEmail(searchQuery);
                break;
        }
    }
    private void searchByClientEmail(String clientEmail) {
        if (clientEmail == null || clientEmail.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Client client = adminController.fetchClientByEmail(clientEmail.trim());
        if (client != null) {
            // Display the client's details
            displayClientInfo(client);

            // Fetch and display orders and invoices for the client
            ArrayList<Invoice> invoices =new ArrayList<>();
            adminController.getAllInvoices(invoices,client.getId());
            ArrayList<Order> orders = adminController.fetchOrdersByClient(client.getId());

            tableModel.setRowCount(0);

            for (Invoice invoice : invoices) {
                tableModel.addRow(new Object[]{invoice.getInvoiceId(), "Invoice", "Click to view"});
            }

            for (Order order : orders) {
                tableModel.addRow(new Object[]{order.getOrderId(), "Order", "Click to view"});
            }
        } else {
            JOptionPane.showMessageDialog(this, "No client found with this email.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private void searchByOrderId(String orderIdString) {
        if (orderIdString == null || orderIdString.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Order ID.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            long orderId = Long.parseLong(orderIdString.trim());
            Order order = adminController.fetchOrderById(orderId);

            if (order != null) {
                // Fetch the associated client for the order
                Client client = adminController.fetchClientById(order.getClient().getId());

                if (client != null) {
                    // Display client details
                    displayClientInfo(client);

                    // Fetch all orders and invoices for the client
                    ArrayList<Order> orders = adminController.fetchOrdersByClient(client.getId());
                    ArrayList<Invoice> invoices = new ArrayList<>();
                    adminController.getAllInvoices(invoices,client.getId());

                    // Clear previous table results
                    tableModel.setRowCount(0);

                    // Add all invoices to the table
                    for (Invoice invoice : invoices) {
                        tableModel.addRow(new Object[]{invoice.getInvoiceId(), "Invoice", "Click to view"});
                    }

                    // Add all orders to the table
                    for (Order clientOrder : orders) {
                        tableModel.addRow(new Object[]{clientOrder.getOrderId(), "Order", "Click to view"});
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "No order found with this ID.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Order ID format. Please enter a numeric value.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void searchByClientId(String clientIdString) {
        try {
            long clientId = Long.parseLong(clientIdString);
            Client client = adminController.fetchClientById(clientId);
            if (client != null) {
                displayClientInfo(client);

                ArrayList<Invoice> invoices = new ArrayList<>();
                adminController.getAllInvoices(invoices, clientId);

                ArrayList<Order> orders = adminController.fetchOrdersByClient(clientId);

                tableModel.setRowCount(0); // Clear previous results

                for (Invoice invoice : invoices) {
                    tableModel.addRow(new Object[]{invoice.getInvoiceId(), "Invoice", "Click to view"}); // Correct type
                }

                for (Order order : orders) {
                    tableModel.addRow(new Object[]{order.getOrderId(), "Order", "Click to view"}); // Correct type
                }
            } else {
                JOptionPane.showMessageDialog(this, "No client found with this ID.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Client ID format.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void searchByInvoiceId(String invoiceIdString) {
        try {
            long invoiceId = Long.parseLong(invoiceIdString);
            String clientId = adminController.fetchInvoiceById(invoiceId); // Returns client_id as a String

            if (clientId != null) {
                Client client = adminController.fetchClientById(Long.parseLong(clientId));

                if (client != null) {
                    displayClientInfo(client);
                }

                ArrayList<Invoice> invoices = new ArrayList<>();
                adminController.getAllInvoices(invoices, Long.parseLong(clientId));

                ArrayList<Order> orders = adminController.fetchOrdersByClient(Long.parseLong(clientId));

                tableModel.setRowCount(0); // Clear previous results

                for (Invoice invoice : invoices) {
                    tableModel.addRow(new Object[]{invoice.getInvoiceId(), "Invoice", "Click to view"}); // Correct type
                }

                for (Order order : orders) {
                    tableModel.addRow(new Object[]{order.getOrderId(), "Order", "Click to view"}); // Correct type
                }
            } else {
                JOptionPane.showMessageDialog(this, "No invoice found with this ID.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Invoice ID format.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayClientInfo(Client client) {
        clientIdValue.setText(String.valueOf(client.getId()));
        clientNameValue.setText(client.getFirstName() + " " + client.getLastName());
        clientEmailValue.setText(client.getEmail());

        clientInfoPanel.setVisible(true);
        revalidate();
        repaint();
    }
    private void displayClientOrders(Client client) {
        displayClientInfo(client);

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
        String type = (String) resultsTable.getValueAt(selectedRow, 1);

        if (!(idObject instanceof Long)) return;
        long selectedId = (Long) idObject;

        if (type.equals("Order")) {
            Order order = adminController.fetchOrderById(selectedId);
            if (order != null) {
                JFrame orderFrame = new JFrame("Order Details");
                orderFrame.setSize(500, 400);
                orderFrame.setLayout(new BorderLayout());
                orderFrame.add(new OrderPanel(order, adminController, selectedId, new InvoiceDashboard(adminController,invoiceController)), BorderLayout.CENTER);
                orderFrame.setLocationRelativeTo(this);
                orderFrame.setVisible(true);
            }
        } else if (type.equals("Invoice")) {
            Invoice invoice = adminController.fetchInvoiceDetailsById(selectedId);
            if (invoice != null) {
                InvoiceDialogView invoiceDialog = new InvoiceDialogView(invoice, invoiceController,this.adminController);
                invoiceDialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invoice not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
