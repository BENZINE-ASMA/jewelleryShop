package view.admin;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import controller.AdminController;
import model.Bijoux;
import model.Necklace;
import model.Ring;

public class ProductDialogView extends JDialog {
    private JTextField nameField;
    private JTextField brandField;
    private JTextField descriptionField;
    private JTextField priceField;
    private JTextField materialField;
    private JTextField sizeField;
    private JTextField lengthField;
    private JTextField stockField;
    private JTextField imagePathField;
    private JComboBox<String> typeComboBox;
    private Bijoux product;
    private AdminController adminController;
    private String uploadedImagePath; // Store the path of the uploaded image

    public ProductDialogView(Bijoux product, AdminController adminController) {
        this.product = product;
        this.adminController = adminController;
        this.setLayout(null);
        this.setSize(400, 600); // Set dialog size
        this.setLocationRelativeTo(null);


        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 30, 80, 25);
        this.add(nameLabel);

        nameField = new JTextField(20);
        nameField.setBounds(150, 30, 150, 25);
        nameField.setText(product != null ? product.getName() : "");
        this.add(nameField);


        JLabel brandLabel = new JLabel("Brand:");
        brandLabel.setBounds(50, 70, 80, 25);
        this.add(brandLabel);

        brandField = new JTextField(20);
        brandField.setBounds(150, 70, 150, 25);
        brandField.setText(product != null ? product.getBrand() : "");
        this.add(brandField);


        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setBounds(50, 110, 80, 25);
        this.add(typeLabel);

        typeComboBox = new JComboBox<>(new String[]{"Ring", "Necklace"});
        typeComboBox.setBounds(150, 110, 150, 25);
        typeComboBox.setSelectedItem(product != null ? product.getType() : "Ring");
        this.add(typeComboBox);


        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setBounds(50, 150, 80, 25);
        this.add(descriptionLabel);

        descriptionField = new JTextField(20);
        descriptionField.setBounds(150, 150, 150, 25);
        descriptionField.setText(product != null ? product.getDescription() : "");
        this.add(descriptionField);


        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(50, 190, 80, 25);
        this.add(priceLabel);

        priceField = new JTextField(20);
        priceField.setBounds(150, 190, 150, 25);
        priceField.setText(product != null ? String.valueOf(product.getPrice()) : "");
        this.add(priceField);


        JLabel materialLabel = new JLabel("Material:");
        materialLabel.setBounds(50, 230, 80, 25);
        this.add(materialLabel);

        materialField = new JTextField(20);
        materialField.setBounds(150, 230, 150, 25);
        materialField.setText(product != null ? product.getMateriel() : "");
        this.add(materialField);


        JLabel sizeLabel = new JLabel("Size:");
        sizeLabel.setBounds(50, 270, 80, 25);
        this.add(sizeLabel);

        sizeField = new JTextField(20);
        sizeField.setBounds(150, 270, 150, 25);
        sizeField.setText(product instanceof Ring ? String.valueOf(((Ring) product).getSize()) : "");
        this.add(sizeField);


        JLabel lengthLabel = new JLabel("Length:");
        lengthLabel.setBounds(50, 310, 80, 25);
        this.add(lengthLabel);

        lengthField = new JTextField(20);
        lengthField.setBounds(150, 310, 150, 25);
        lengthField.setText(product instanceof Necklace ? String.valueOf(((Necklace) product).getLength()) : "");
        this.add(lengthField);


        JLabel stockLabel = new JLabel("Stock:");
        stockLabel.setBounds(50, 350, 80, 25);
        this.add(stockLabel);

        stockField = new JTextField(20);
        stockField.setBounds(150, 350, 150, 25);
        stockField.setText(product != null ? String.valueOf(product.getStock()) : "");
        this.add(stockField);


        JLabel imageLabel = new JLabel("Image:");
        imageLabel.setBounds(50, 390, 80, 25);
        this.add(imageLabel);

        JButton uploadButton = new JButton("Upload Image");
        uploadButton.setBounds(150, 390, 150, 25);
        uploadButton.addActionListener(e -> uploadImage());
        this.add(uploadButton);

        imagePathField = new JTextField(20);
        imagePathField.setBounds(150, 420, 150, 25);
        imagePathField.setEditable(false);
        imagePathField.setText(product != null ? product.getImagePath() : "");
        this.add(imagePathField);


        JButton saveButton = new JButton("Save");
        saveButton.setBounds(150, 460, 80, 25);
        saveButton.addActionListener(e -> saveProduct());
        this.add(saveButton);
    }

    private void uploadImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select an Image");

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String fileName = selectedFile.getName();
            String targetPath = "src/main/java/resources/images/" + fileName; // Destination path in the resources folder

            try {
                Files.createDirectories(Paths.get("src/main/java/resources/images")); // Ensure directory exists
                Files.copy(selectedFile.toPath(), Paths.get(targetPath));
                uploadedImagePath = targetPath;
                imagePathField.setText(uploadedImagePath);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error uploading image: " + ex.getMessage());
            }
        }
    }

    private void saveProduct() {
        String name = nameField.getText();
        String brand = brandField.getText();
        String description = descriptionField.getText();
        String material = materialField.getText();
        String type = (String) typeComboBox.getSelectedItem();
        String imagePath = uploadedImagePath != null ? uploadedImagePath : imagePathField.getText();
        int stock;
        double price;

        try {
            stock = Integer.parseInt(stockField.getText());
            price = Double.parseDouble(priceField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for stock and price.");
            return;
        }

        if (type.equals("Ring")) {
            int size;
            try {
                size = Integer.parseInt(sizeField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid numeric value for size.");
                return;
            }

            if (product == null) {
                product = new Ring(null, name, brand, type, description, price, material, size, imagePath, stock);
                adminController.addProduct(product);
            } else {
                product.setName(name);
                product.setBrand(brand);
                product.setDescription(description);
                product.setPrice(price);
                product.setStock(stock);
                product.setMateriel(material);
                product.setImagePath(imagePath);
                if (product instanceof Ring) {
                    ((Ring) product).setSize(size);
                }
                adminController.updateProduct(product);
            }
        } else if (type.equals("Necklace")) {
            double length;
            try {
                length = Double.parseDouble(lengthField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid numeric value for length.");
                return;
            }

            if (product == null) {
                product = new Necklace(null, name, brand, type, description, price, material, length, imagePath, stock);
                adminController.addProduct(product);
            } else {
                product.setName(name);
                product.setBrand(brand);
                product.setDescription(description);
                product.setPrice(price);
                product.setStock(stock);
                product.setMateriel(material);
                product.setImagePath(imagePath);
                if (product instanceof Necklace) {
                    ((Necklace) product).setLength(length);
                }
                adminController.updateProduct(product);
            }
        }

        this.dispose();
    }

}
