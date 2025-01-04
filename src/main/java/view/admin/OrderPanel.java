package view.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

import controller.AdminController;
import model.*;

public class OrderPanel extends JPanel {
    private long invoiceID;
    private Order order;
    private JLabel orderIdLabel, orderDateLabel, clientLabel, statusLabel, totalPriceLabel;
    private JButton validateOrderButton;
    private JTable cartItemsTable;
    private JScrollPane cartItemsScrollPane;
    private AdminController adminController;

    public OrderPanel(Order order, AdminController adminController,long invoiceID) {
        this.invoiceID= invoiceID;
        this.adminController=adminController;
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
            if(column==2){
                Long productId = (Long)model.getValueAt(row,0);
                int newQ = Integer.parseInt((String) model.getValueAt(row,column));
                this.order.getCartItems().modifyQuantityOfProduct(productId,newQ);
                Double newtotalCart = this.order.getCartItems().getTotalPrice();
                this.totalPriceLabel.setText("Total Price: " + newtotalCart);
                adminController.updateCart(this.order.getOrderId(),productId,newQ);//need orderid, productid, newq
                adminController.updateOrder(this.order.getOrderId(),newtotalCart);// need orderif, totalPrice

            }
        });
        cartItemsTable.setModel(model);
        cartItemsScrollPane = new JScrollPane(cartItemsTable);

        validateOrderButton = new JButton("Validate Order");

        validateOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                order.validateOrder();
                adminController.updateInvoice(invoiceID,order.getCartItems().getTotalPrice());
                // we have to update the invoice here and generate a new invoice
            }
        });

        add(orderIdLabel);
        add(orderDateLabel);
        add(clientLabel);
        add(statusLabel);
        add(totalPriceLabel);
        add(cartItemsScrollPane);
        add(validateOrderButton);
    }

    private DefaultTableModel createTableModel(Cart cart) {
        String[] columnNames = {"Product_id","Product", "Quantity", "Price per Unit", "Total Price"};
        Object[][] data = new Object[cart.getCart().size()][5];

        int i = 0;
        for (Map.Entry<Bijoux, Integer> entry : cart.getCart().entrySet()) {
            Bijoux bijoux = entry.getKey();
            Integer quantity = entry.getValue();
            double pricePerUnit = bijoux.getPrice();
            double totalPrice = pricePerUnit * quantity;

            data[i] = new Object[]{bijoux.getId(),bijoux.getName(), quantity, pricePerUnit, totalPrice};
            i++;
        }

        return new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
            @Override
            public void setValueAt(Object aValue, int row, int column) {
                if (column == 2) {
                    try {
                        int newQuantity = Integer.parseInt(aValue.toString());
                        if (newQuantity <= 0) {

                            JOptionPane.showMessageDialog(null, "Quantity cannot be 0 or negative. you can delete the row", "Invalid Quantity", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid quantity. Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                super.setValueAt(aValue, row, column);
            }

        };

    }
}
