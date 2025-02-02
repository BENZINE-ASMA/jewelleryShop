package view;
import javax.swing.*;
import java.awt.*;
import model.Bijoux;
import controller.MainController;

public class ProductDetailView extends JFrame {

    public ProductDetailView(Bijoux bijou, MainController mainController) {
        setTitle("Product Details");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Closes only this window, not the entire app

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(5, 2, 10, 10));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Product Image
        JLabel imageLabel = new JLabel(new ImageIcon(bijou.getImagePath()));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Product Details
        infoPanel.add(new JLabel("Name:"));
        infoPanel.add(new JLabel(bijou.getName()));

        infoPanel.add(new JLabel("Price:"));
        infoPanel.add(new JLabel(String.valueOf(bijou.getPrice()) + " €"));

        infoPanel.add(new JLabel("Category:"));
        infoPanel.add(new JLabel(bijou.getType()));

        infoPanel.add(new JLabel("Stock:"));
        infoPanel.add(new JLabel(String.valueOf(bijou.getStock())));

        // Add to Cart Button
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.addActionListener(e -> {
            mainController.addToCart(bijou);
            JOptionPane.showMessageDialog(this, "Added to Cart!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        // Close Button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose()); // Close only this window

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addToCartButton);
        buttonPanel.add(closeButton);

        mainPanel.add(imageLabel, BorderLayout.NORTH);
        mainPanel.add(infoPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true); // Show the window
    }
}
