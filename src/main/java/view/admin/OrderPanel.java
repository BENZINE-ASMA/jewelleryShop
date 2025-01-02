package view.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import model.*;

public class OrderPanel extends JPanel {
    private Order order;
    private JLabel orderIdLabel, orderDateLabel, clientLabel, statusLabel, totalPriceLabel;
    private JButton validateOrderButton, deliverOrderButton, updateCartButton;
    private JTable cartItemsTable;
    private JScrollPane cartItemsScrollPane;

    public OrderPanel(Order order) {
        this.order = order;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        orderIdLabel = new JLabel("Order ID: " + order.getOrderId());
        orderDateLabel = new JLabel("Order Date: " + order.getOrderDate());
        clientLabel = new JLabel("Client: " + order.getClient().getId());
        statusLabel = new JLabel("Status: " + order.getStatus());
        totalPriceLabel = new JLabel("Total Price: " + order.getCartItems().getTotalPrice());

        cartItemsTable = new JTable();
        DefaultTableModel model = this.createTableModel(order.getCartItems());
        model.addTableModelListener(e->{
            int row = e.getFirstRow();
            int column = e.getColumn();
            if(column==1){
                String productName = (String)model.getValueAt(row,0);
                String newQ = (String) model.getValueAt(row,column);

                System.out.println("hello " +newQ);
            }
        });
        cartItemsTable.setModel(model);
        cartItemsScrollPane = new JScrollPane(cartItemsTable);

        validateOrderButton = new JButton("Validate Order");
        deliverOrderButton = new JButton("Mark as Delivered");
        updateCartButton = new JButton("Update Cart");

        validateOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                order.validateOrder();
                totalPriceLabel.setText("Total Price: " + order.getCartItems().getTotalPrice());
            }
        });

        deliverOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                order.orderDelivered();
            }
        });

        updateCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open a new dialog or panel to modify the cart items
                //CartEditDialog editDialog = new CartEditDialog(order.getCartItems());
               // editDialog.setVisible(true);
                // After editing, refresh the cart table
                cartItemsTable.setModel(createTableModel(order.getCartItems()));

                totalPriceLabel.setText("Total Price: " + order.getCartItems().getTotalPrice());
            }
        });


        add(orderIdLabel);
        add(orderDateLabel);
        add(clientLabel);
        add(statusLabel);
        add(totalPriceLabel);
        add(cartItemsScrollPane);
        add(validateOrderButton);
        add(deliverOrderButton);
        add(updateCartButton);
    }

    private DefaultTableModel createTableModel(Cart cart) {
        String[] columnNames = {"Product", "Quantity", "Price per Unit", "Total Price"};
        Object[][] data = new Object[cart.getCart().size()][4];

        int i = 0;
        for (Map.Entry<Bijoux, Integer> entry : cart.getCart().entrySet()) {
            Bijoux bijoux = entry.getKey();
            Integer quantity = entry.getValue();
            double pricePerUnit = bijoux.getPrice();
            double totalPrice = pricePerUnit * quantity;

            data[i] = new Object[]{bijoux.getName(), quantity, pricePerUnit, totalPrice};
            i++;
        }

        return new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1;
            }
        };
    }
}
