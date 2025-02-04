package view.admin;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.AdminController;
import model.Bijoux;
import model.Client;
import model.Necklace;
import model.Ring;

public class ProductDashboard extends JPanel {
    private JTable productTable;
    private DefaultTableModel tableModel;
    private AdminController adminController;

    public ProductDashboard(AdminController adminController) {
        this.adminController = adminController;
        setLayout(new BorderLayout());
        DashboardHeaderAdmin header = new DashboardHeaderAdmin(adminController);
        header.setBorder(new EmptyBorder(0, 0, 5, 0));
        add(header, BorderLayout.NORTH);
        tableModel = new DefaultTableModel(new String[]{
            "ID", "Name", "Brand", "Type", "Description", "Price",
            "Material", "Size", "Length", "Stock", "Image_Path"
        }, 0);
        
        productTable = new JTable(tableModel);
        this.loadProductData();
        add(new JScrollPane(productTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addProduct = new JButton("Add");
        addProduct.addActionListener(e -> {
            this.openEditProductView(null);
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
        deleteProduct.addActionListener(e -> this.deleteProduct());

        buttonPanel.add(addProduct);
        buttonPanel.add(editProduct);
        buttonPanel.add(deleteProduct);

        this.add(buttonPanel, BorderLayout.SOUTH);
        JButton closeButton = new JButton("close");
        closeButton.addActionListener(e -> {
            adminController.ShowMaindashboardAdminView();
            this.setVisible(false);
        });

    }

    private void loadProductData() {
        this.tableModel.setRowCount(0);

        for (Bijoux bijou : this.adminController.fetchAllProducts()) {
            if ("ring".equalsIgnoreCase(bijou.getCategory())) {
                tableModel.addRow(new Object[]{
                    bijou.getId(), bijou.getName(), bijou.getBrand(), bijou.getCategory(),
                    bijou.getDescription(), bijou.getPrice(), bijou.getMateriel(),
                    ((Ring) bijou).getSize(), null, bijou.getStock(), bijou.getImagePath()
                });
            } else if ("necklace".equalsIgnoreCase(bijou.getCategory())) {
                tableModel.addRow(new Object[]{
                    bijou.getId(), bijou.getName(), bijou.getBrand(), bijou.getCategory(),
                    bijou.getDescription(), bijou.getPrice(), bijou.getMateriel(),
                    null, ((Necklace) bijou).getLength(), bijou.getStock(), bijou.getImagePath()
                });
            }
        }
    }

    private Bijoux getProductFromTable(int rowIndex) {
        Long id = (Long) tableModel.getValueAt(rowIndex, 0);
        String name = (String) tableModel.getValueAt(rowIndex, 1);
        String brand = (String) tableModel.getValueAt(rowIndex, 2);
        String type = (String) tableModel.getValueAt(rowIndex, 3);
        String description = (String) tableModel.getValueAt(rowIndex, 4);
        double price = (double) tableModel.getValueAt(rowIndex, 5);
        String material = (String) tableModel.getValueAt(rowIndex, 6);
        Integer size = (Integer) tableModel.getValueAt(rowIndex, 7);
        Double length = (Double) tableModel.getValueAt(rowIndex, 8);
        int stock = (int) tableModel.getValueAt(rowIndex, 9);
        String imagePath = (String) tableModel.getValueAt(rowIndex, 10);

        if ("ring".equalsIgnoreCase(type)) {
            return new Ring(id, name, brand, type, description, price, material, size, imagePath, stock);
        } else if ("necklace".equalsIgnoreCase(type)) {
            return new Necklace(id, name, brand, type, description, price, material, length, imagePath, stock);
        }
        return null;
    }

    private void refreshTable() {
        loadProductData();
        tableModel.fireTableDataChanged(); // Notify the table model that data has changed
    }

    private void openEditProductView(Bijoux bijou) {
        ProductDialogView dialog = new ProductDialogView(bijou, adminController, this::refreshTable);
        dialog.setSize(450, 550);
        dialog.setLocationRelativeTo(null);
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    private void deleteProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            adminController.deleteProduct(id); // Ensure this method is implemented
            loadProductData(); // Refresh table after deletion
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product to delete.");
        }
    }
}
