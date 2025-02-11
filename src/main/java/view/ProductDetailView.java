package view;

import javax.swing.*;
import java.awt.*;
import model.Bijoux;
import controller.MainController;
import shared.UtilDisplayingDashboards;

public class ProductDetailView extends JFrame {

    public ProductDetailView(Bijoux bijou, MainController mainController) {
        setTitle(bijou.getName() + " - Product Details");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(Color.WHITE);
        imagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel imageLabel = new JLabel();
        Image image = UtilDisplayingDashboards.loadImageBijou(bijou.getImagePath());
        if (image != null) {
            Image scaledImg = image.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImg));
        } else {
            imageLabel.setText("Image not available");
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel titleBrandPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        titleBrandPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("<html><h2>" + bijou.getName() + "</h2></html>");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center alignment
        titleBrandPanel.add(titleLabel);

        JLabel brandLabel = new JLabel("<html><b>Brand:</b> " + bijou.getBrand() + "</html>");
        brandLabel.setHorizontalAlignment(SwingConstants.LEFT); // Center alignment
        brandLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        titleBrandPanel.add(brandLabel);

        infoPanel.add(titleBrandPanel);

        JTextArea descriptionArea = new JTextArea(bijou.getDescription() != null ? bijou.getDescription() : "No description available.");
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setOpaque(false);
        descriptionArea.setEditable(false);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        descriptionScrollPane.setPreferredSize(new Dimension(350, 180));
        descriptionScrollPane.setBorder(BorderFactory.createEmptyBorder());
        infoPanel.add(descriptionScrollPane);

        JPanel detailsPanel = new JPanel(new GridLayout(3, 2, 10, 5));
        detailsPanel.setBackground(Color.WHITE);

        detailsPanel.add(new JLabel("<html><b>Price:</b></html>"));
        detailsPanel.add(new JLabel("<html><h3 style='color: green;'>" + bijou.formattedPrice() + "</h3></html>"));

        detailsPanel.add(new JLabel("<html><b>Material:</b></html>"));
        detailsPanel.add(new JLabel(bijou.getMateriel() != null ? bijou.getMateriel() : "Unknown"));

        detailsPanel.add(new JLabel("<html><b>Stock:</b></html>"));
        detailsPanel.add(new JLabel(bijou.getStock() > 0 ? bijou.getStock() + " available" : "Out of Stock"));

        infoPanel.add(detailsPanel);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
/*
        JButton addToCartButton = new JButton(" Add to Cart");
        addToCartButton.setFont(new Font("Arial", Font.BOLD, 14));
        addToCartButton.setForeground(Color.WHITE);
        addToCartButton.setBackground(new Color(34, 153, 84));
        addToCartButton.setFocusPainted(false);
        addToCartButton.setPreferredSize(new Dimension(140, 30));
        addToCartButton.addActionListener(e -> {
            mainController.addToCart(bijou);
            JOptionPane.showMessageDialog(this, "Added to Cart!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
*/
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setPreferredSize(new Dimension(100, 30));
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);

        infoPanel.add(buttonPanel);

        mainPanel.add(imagePanel, BorderLayout.WEST);
        mainPanel.add(infoPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }
}
