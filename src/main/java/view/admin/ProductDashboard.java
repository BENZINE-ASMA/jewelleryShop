package view.admin;

import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import controller.AdminController;
import model.Bijoux;
import model.Necklace;
import model.Ring;

public class ProductDashboard extends JPanel {
    private JTable productTable;
    private DefaultTableModel tableModel;
    private AdminController adminController;

    public ProductDashboard(AdminController adminController) {
        this.adminController = adminController;
        setLayout(new BorderLayout());

        // Header
        DashboardHeaderAdmin header = new DashboardHeaderAdmin(adminController);
        header.setBorder(new EmptyBorder(0, 0, 5, 0));
        add(header, BorderLayout.NORTH);

        // Table Model
        tableModel = new DefaultTableModel(new String[]{
                "ID", "Name", "Brand", "Type", "Description", "Price",
                "Material", "Size", "Length", "Stock", "Reserved Stock", "Image_Path"
        }, 0);

        productTable = new JTable(tableModel);
        loadProductData();
        add(new JScrollPane(productTable), BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();

        JButton addProduct = new JButton("Add");
        addProduct.addActionListener(e -> {
            openEditProductView(null);
            loadProductData();
        });

        JButton editProduct = new JButton("Edit");
        editProduct.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow != -1) {
                Bijoux selectedBijoux = getProductFromTable(selectedRow);
                openEditProductView(selectedBijoux);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a product to edit.");
            }
        });

        JButton deleteProduct = new JButton("Delete");
        deleteProduct.addActionListener(e -> deleteSelectedProduct());

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {
            adminController.ShowMaindashboardAdminView();
            this.setVisible(false);
        });

        buttonPanel.add(addProduct);
        buttonPanel.add(editProduct);
        buttonPanel.add(deleteProduct);
        buttonPanel.add(closeButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadProductData() {
        tableModel.setRowCount(0); // Clear table

        for (Bijoux bijou : adminController.fetchAllProducts()) {
            if ("Ring".equalsIgnoreCase(bijou.getCategory())) {
                tableModel.addRow(new Object[]{
                        bijou.getId(), bijou.getName(), bijou.getBrand(), bijou.getCategory(),
                        bijou.getDescription(), bijou.getPrice(), bijou.getMateriel(),
                        ((Ring) bijou).getSize(), null, bijou.getStock(), bijou.getImagePath()
                });
            } else if ("Necklace".equalsIgnoreCase(bijou.getCategory())) {
                tableModel.addRow(new Object[]{
                        bijou.getId(), bijou.getName(), bijou.getBrand(), bijou.getCategory(),
                        bijou.getDescription(), bijou.getPrice(), bijou.getMateriel(),
                        null, ((Necklace) bijou).getLength(), bijou.getStock(), bijou.getImagePath()
                });
            }
        }
    }

    private Bijoux getProductFromTable(int rowIndex) {
        // Retrieve values safely, handling nulls where necessary
        Long id = (Long) tableModel.getValueAt(rowIndex, 0);
        String name = (String) tableModel.getValueAt(rowIndex, 1);
        String brand = (String) tableModel.getValueAt(rowIndex, 2);
        String type = (String) tableModel.getValueAt(rowIndex, 3);
        String description = (String) tableModel.getValueAt(rowIndex, 4);
        double price = (double) tableModel.getValueAt(rowIndex, 5);
        String material = (String) tableModel.getValueAt(rowIndex, 6);
        Integer size = (tableModel.getValueAt(rowIndex, 7) != null) ? (Integer) tableModel.getValueAt(rowIndex, 7) : null;
        Double length = (tableModel.getValueAt(rowIndex, 8) != null) ? (Double) tableModel.getValueAt(rowIndex, 8) : null;
        int stock = (int) tableModel.getValueAt(rowIndex, 9);
        int reservedStock = (int) tableModel.getValueAt(rowIndex, 10);
        String imagePath = (String) tableModel.getValueAt(rowIndex, 11);

        if ("Ring".equalsIgnoreCase(type)) {
            return new Ring(id, name, brand, type, description, price, material, size, imagePath, stock);
        } else if ("Necklace".equalsIgnoreCase(type)) {
            return new Necklace(id, name, brand, type, description, price, material, length, imagePath, stock);
        }
        return null;
    }

    private void refreshTable() {
        loadProductData();
        tableModel.fireTableDataChanged();
    }

    private void openEditProductView(Bijoux bijou) {
        ProductDialogView dialog = new ProductDialogView(bijou, adminController, this::refreshTable);
        dialog.setSize(450, 550);
        dialog.setLocationRelativeTo(null);
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    private void deleteSelectedProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            adminController.deleteProduct(id);
            loadProductData(); // Refresh table after deletion
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product to delete.");
        }
    }
}
