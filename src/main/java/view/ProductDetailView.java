package view;

import javax.swing.*;
import java.awt.*;
import model.Bijoux;
import controller.MainController;

public class ProductDetailView extends JFrame {

    public ProductDetailView(Bijoux bijou, MainController mainController) {
        setTitle(bijou.getName() + " - Product Details");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main container
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Top Panel: Product Image
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(Color.WHITE);
        imagePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel imageLabel = new JLabel();
        ImageIcon icon = new ImageIcon(bijou.getImagePath());
        Image scaledImg = icon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImg));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagePanel.add(imageLabel);

        // Center Panel: Product Info
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        infoPanel.setBackground(Color.WHITE);

        // Title and Description
        JPanel titleDescPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        titleDescPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("<html><h1>" + bijou.getName() + "</h1></html>");
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel brandLabel = new JLabel("<html><b>Brand:</b> " + bijou.getBrand() + "</html>");
        brandLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JTextArea descriptionArea = new JTextArea(bijou.getDescription());
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setOpaque(false);
        descriptionArea.setEditable(false);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));

        titleDescPanel.add(titleLabel);
        titleDescPanel.add(brandLabel);
        titleDescPanel.add(descriptionArea);

        // Price and Material Info
        JPanel priceMaterialPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        priceMaterialPanel.setBackground(Color.WHITE);

        JLabel priceLabel = new JLabel("<html><b>Price:</b></html>");
        JLabel priceValue = new JLabel("<html><h2 style='color: green;'>" + bijou.formattedPrice() + "</h2></html>");

        JLabel materialLabel = new JLabel("<html><b>Material:</b></html>");
        JLabel materialValue = new JLabel(bijou.getMateriel());

        JLabel stockLabel = new JLabel("<html><b>Stock:</b></html>");
        JLabel stockValue = new JLabel(bijou.getStock() > 0 ? bijou.getStock() + " available" : "Out of Stock");

        priceMaterialPanel.add(priceLabel);
        priceMaterialPanel.add(priceValue);
        priceMaterialPanel.add(materialLabel);
        priceMaterialPanel.add(materialValue);
        priceMaterialPanel.add(stockLabel);
        priceMaterialPanel.add(stockValue);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton addToCartButton = new JButton("🛒 Add to Cart");
        addToCartButton.setFont(new Font("Arial", Font.BOLD, 16));
        addToCartButton.setForeground(Color.WHITE);
        addToCartButton.setBackground(new Color(34, 153, 84));
        addToCartButton.setFocusPainted(false);
        addToCartButton.setPreferredSize(new Dimension(200, 50));
        addToCartButton.addActionListener(e -> {
            mainController.addToCart(bijou);
            JOptionPane.showMessageDialog(this, "Added to Cart!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setPreferredSize(new Dimension(100, 40));
        closeButton.addActionListener(e -> dispose());

        buttonPanel.add(addToCartButton);
        buttonPanel.add(closeButton);

        // Assemble panels
        infoPanel.add(titleDescPanel, BorderLayout.NORTH);
        infoPanel.add(priceMaterialPanel, BorderLayout.CENTER);
        infoPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(imagePanel, BorderLayout.WEST);
        mainPanel.add(infoPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }
}
