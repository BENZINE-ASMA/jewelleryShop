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
    private boolean isEditMode;


    public ProductDialogView(Bijoux product, AdminController adminController, Runnable refreshCallback) {
        this.product = product;
        this.adminController = adminController;
        this.refreshCallback = refreshCallback;
        this.isEditMode = (product != null);

        setTitle(isEditMode ? "Edit Product Details" : "Add New Product");
        setSize(450, 600);
        setLocationRelativeTo(null);
        setModal(true);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        mainPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        nameField.setText(isEditMode ? product.getName() : "");
        mainPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Brand:"), gbc);
        gbc.gridx = 1;
        brandField = new JTextField(20);
        brandField.setText(isEditMode ? product.getBrand() : "");
        mainPanel.add(brandField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Type:"), gbc);
        gbc.gridx = 1;
        typeComboBox = new JComboBox<>(new String[]{"Ring", "Necklace"});
        typeComboBox.setSelectedItem(isEditMode ? product.getCategory() : "Ring");
        mainPanel.add(typeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        descriptionField = new JTextArea(5, 20);
        descriptionField.setLineWrap(true);
        descriptionField.setWrapStyleWord(true);
        descriptionField.setText(isEditMode ? product.getDescription() : "");
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionField);
        descriptionScrollPane.setPreferredSize(new Dimension(250, 100));
        mainPanel.add(descriptionScrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Price (€):"), gbc);
        gbc.gridx = 1;
        priceField = new JTextField(20);
        priceField.setText(isEditMode ? String.valueOf(product.getPrice()) : "");
        mainPanel.add(priceField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Material:"), gbc);
        gbc.gridx = 1;
        materialField = new JTextField(20);
        materialField.setText(isEditMode ? product.getMateriel() : "");
        mainPanel.add(materialField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Size:"), gbc);
        gbc.gridx = 1;
        sizeField = new JTextField(20);
        sizeField.setText((isEditMode && product instanceof Ring) ? String.valueOf(((Ring) product).getSize()) : "");
        mainPanel.add(sizeField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Length (cm):"), gbc);
        gbc.gridx = 1;
        lengthField = new JTextField(20);
        lengthField.setText((isEditMode && product instanceof Necklace) ? String.valueOf(((Necklace) product).getLength()) : "");
        mainPanel.add(lengthField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Stock:"), gbc);
        gbc.gridx = 1;
        stockField = new JTextField(20);
        stockField.setText(isEditMode ? String.valueOf(product.getStock()) : "");
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
        imagePathField.setText(isEditMode ? product.getImagePath() : "");
        mainPanel.add(imagePathField, gbc);

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
            String selectedType = (String) this.typeComboBox.getSelectedItem();

            String relativePath = "resources/jewelry/" + (selectedType.equals("Ring") ? "rings/" : "necklaces/");
            String targetDirectory = "src/main/" + relativePath;
            String targetPath = targetDirectory + fileName;

            try {
                Files.copy(selectedFile.toPath(), Paths.get(targetPath));
                uploadedImagePath = relativePath + fileName;
                imagePathField.setText(uploadedImagePath);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error uploading image: " + ex.getMessage());
            }
        }
    }

    private void saveProduct() {
        try {
            String name = nameField.getText().trim();
            String brand = brandField.getText().trim();
            String description = descriptionField.getText().trim();
            String material = materialField.getText().trim();
            String type = (String) typeComboBox.getSelectedItem();
            String imagePath = (uploadedImagePath != null) ? uploadedImagePath : imagePathField.getText();
            int stock = Integer.parseInt(stockField.getText().trim());
            double price = Double.parseDouble(priceField.getText().trim());

            if (brand.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Brand cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (isEditMode) {
                product.setName(name);
                product.setBrand(brand);
                product.setDescription(description);
                product.setPrice(price);
                product.setMateriel(material);
                product.setImagePath(imagePath);
                product.setStock(stock);
                if (product instanceof Ring) {
                    ((Ring) product).setSize(Integer.parseInt(sizeField.getText().trim()));
                } else if (product instanceof Necklace) {
                    ((Necklace) product).setLength(Double.parseDouble(lengthField.getText().trim()));
                }

                adminController.updateProduct(product);
            } else {
                if ("Ring".equals(type)) {
                    product = new Ring(null, name, brand, type, description, price, material, Integer.parseInt(sizeField.getText().trim()), imagePath, stock);
                } else {
                    product = new Necklace(null, name, brand, type, description, price, material, Double.parseDouble(lengthField.getText().trim()), imagePath, stock);
                }
                adminController.addProduct(product);
            }

            if (refreshCallback != null) refreshCallback.run();
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for stock, price, size, and length.");
        }
    }
}
