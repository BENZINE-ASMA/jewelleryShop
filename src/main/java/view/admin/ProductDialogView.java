package view.admin;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import controller.AdminController;
import model.Bijoux;
import model.Necklace;
import model.Ring;

public class ProductDialogView extends JDialog {
    private JTextField nameField, brandField, priceField, materialField, sizeField, lengthField, stockField, imagePathField;
    private JTextArea descriptionField;
    private JComboBox<String> typeComboBox;
    private Bijoux product;
    private AdminController adminController;
    private String uploadedImagePath;
    private Runnable refreshCallback;

    public ProductDialogView(Bijoux product, AdminController adminController, Runnable refreshCallback) {
        this.product = product;
        this.adminController = adminController;
        this.refreshCallback = refreshCallback;

        setTitle(product == null ? "Add New Product" : "Edit Product Details");
        setSize(450, 600);
        setLocationRelativeTo(null);
        setModal(true);

        // Main Panel with GridBagLayout for alignment
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Labels & Fields
        mainPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        nameField.setText(product != null ? product.getName() : "");
        mainPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Brand:"), gbc);
        gbc.gridx = 1;
        brandField = new JTextField(20);
        brandField.setText(product != null ? product.getBrand() : "");
        mainPanel.add(brandField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Type:"), gbc);
        gbc.gridx = 1;
        typeComboBox = new JComboBox<>(new String[]{"Ring", "Necklace"});
        typeComboBox.setSelectedItem(product != null ? product.getCategory() : "Ring");
        mainPanel.add(typeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        descriptionField = new JTextArea(5, 20);
        descriptionField.setLineWrap(true);
        descriptionField.setWrapStyleWord(true);
        descriptionField.setText(product != null ? product.getDescription() : "");
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionField);
        descriptionScrollPane.setPreferredSize(new Dimension(250, 100));
        descriptionScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        descriptionScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(descriptionScrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Price (€):"), gbc);
        gbc.gridx = 1;
        priceField = new JTextField(20);
        priceField.setText(product != null ? String.valueOf(product.getPrice()) : "");
        mainPanel.add(priceField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Material:"), gbc);
        gbc.gridx = 1;
        materialField = new JTextField(20);
        materialField.setText(product != null ? product.getMateriel() : "");
        mainPanel.add(materialField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Size:"), gbc);
        gbc.gridx = 1;
        sizeField = new JTextField(20);
        sizeField.setText(product instanceof Ring ? String.valueOf(((Ring) product).getSize()) : "");
        mainPanel.add(sizeField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Length (cm):"), gbc);
        gbc.gridx = 1;
        lengthField = new JTextField(20);
        lengthField.setText(product instanceof Necklace ? String.valueOf(((Necklace) product).getLength()) : "");
        mainPanel.add(lengthField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Stock:"), gbc);
        gbc.gridx = 1;
        stockField = new JTextField(20);
        stockField.setText(product != null ? String.valueOf(product.getStock()) : "");
        mainPanel.add(stockField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Image:"), gbc);
        gbc.gridx = 1;
        JButton uploadButton = new JButton("Upload Image");
        uploadButton.addActionListener(e -> uploadImage());
        mainPanel.add(uploadButton, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        imagePathField = new JTextField(20);
        imagePathField.setEditable(false);
        imagePathField.setText(product != null ? product.getImagePath() : "");
        mainPanel.add(imagePathField, gbc);

        // Save Button
        gbc.gridx = 1;
        gbc.gridy++;
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveProduct());
        mainPanel.add(saveButton, gbc);

        add(mainPanel);
    }

    private void uploadImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select an Image");
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String fileName = selectedFile.getName();
            String targetPath = "src/main/java/resources/images/" + fileName;

            try {
                Files.createDirectories(Paths.get("src/main/java/resources/images"));
                Files.copy(selectedFile.toPath(), Paths.get(targetPath));
                uploadedImagePath = targetPath;
                imagePathField.setText(uploadedImagePath);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error uploading image: " + ex.getMessage());
            }
        }
    }

    private void saveProduct() {
        try {
            String name = nameField.getText();
            String brand = brandField.getText();
            String description = descriptionField.getText();
            String material = materialField.getText();
            String type = (String) typeComboBox.getSelectedItem();
            String imagePath = uploadedImagePath != null ? uploadedImagePath : imagePathField.getText();
            int stock = Integer.parseInt(stockField.getText());
            double price = Double.parseDouble(priceField.getText());

            if (type.equals("Ring")) {
                int size = Integer.parseInt(sizeField.getText());
                product = (product == null) ? new Ring(null, name, brand, type, description, price, material, size, imagePath, stock)
                        : (Ring) product;
                ((Ring) product).setSize(size);
            } else if (type.equals("Necklace")) {
                double length = Double.parseDouble(lengthField.getText());
                product = (product == null) ? new Necklace(null, name, brand, type, description, price, material, length, imagePath, stock)
                        : (Necklace) product;
                ((Necklace) product).setLength(length);
            }

            if (product != null) adminController.updateProduct(product);
            else adminController.addProduct(product);

            if (refreshCallback != null) refreshCallback.run();
            dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for stock, price, size, and length.");
        }
    }
}
