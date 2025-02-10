package shared;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;

import controller.MainController;
import model.Bijoux;
import model.Ring;
import view.MainDashboardView;
import view.ProductDetailView;

public class UtilDisplayingDashboards {
    public static Map<Bijoux, JButton> addToCartButtons = new HashMap<>();
    public static Image loadImageBijou(String path) {
        try {
            InputStream input = UtilDisplayingDashboards.class.getClassLoader().getResourceAsStream(path);
            if (input != null) {
                BufferedImage image = ImageIO.read(input);
                return image;
            } else {
                throw new RuntimeException("icon " + path + " not found in the classpath!");
            }
        } catch (IOException e) {
            throw new RuntimeException("image " + path + " not found in the classpath!");
        }
    }

    public static JPanel createBijouxPanel(Bijoux bijou, MainController mainController, String role, ArrayList<? extends Bijoux> products) {
        JPanel bijouPanel = new JPanel();
        bijouPanel.setBackground(Color.white);
        bijouPanel.setLayout(new BorderLayout());
        bijouPanel.setPreferredSize(new Dimension(180, 185));
        bijouPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
        imagePanel.setBackground(Color.white);
        imagePanel.setPreferredSize(new Dimension(120, 140));

        Image bijouImg = UtilDisplayingDashboards.loadImageBijou(bijou.getImagePath());
        if (bijouImg != null) {
            Image scaledImage = bijouImg.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            JLabel bijouLabel = new JLabel(new ImageIcon(scaledImage));
            bijouLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            imagePanel.add(bijouLabel);
        } else {
            imagePanel.add(new JLabel("Image not available"));
        }

        JLabel nameLabel = new JLabel("<html><center>" + bijou.getName() + "</center></html>");
        nameLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        imagePanel.add(nameLabel);

        int stock = bijou.getStock();
        JLabel quantityLabel = new JLabel("Stock: " + stock);
        quantityLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        imagePanel.add(quantityLabel);

        JLabel priceLabel = new JLabel("Price: " + bijou.getPrice() + "€");
        priceLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        imagePanel.add(priceLabel);

        if (role.equals("client")) {
            JPanel buttonPanel = new JPanel(new FlowLayout());
            JButton addButton = new JButton("Add to Cart");

            // ✅ Check if the item is already in the cart and disable the button
            if (mainController.isItemInCart(bijou)) {
                addButton.setEnabled(false);
            }

            addButton.addActionListener(e -> {
                mainController.addToCart(bijou);
                quantityLabel.setText("Stock: " + bijou.getStock());
                addButton.setEnabled(false); // Disable after adding

                // Store reference for re-enabling if item is removed later
                UtilDisplayingDashboards.addToCartButtons.put(bijou, addButton);
            });

            buttonPanel.add(addButton);
            imagePanel.add(buttonPanel);
        }

        bijouPanel.add(imagePanel, BorderLayout.CENTER);

        bijouPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    new ProductDetailView(bijou, mainController);
                }
            }
        });

        return bijouPanel;
    }



    public static  void loadIcon(JLabel label, String path, int width, int height) {
        try {
            InputStream input = MainDashboardView.class.getClassLoader().getResourceAsStream(path);
            if (input != null) {


                Image image = ImageIO.read(input);

                if (path.contains("hotlineIcon")||path.contains("logo") || path.contains(("logout"))) {
                	image =image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                }
                label.setIcon(new ImageIcon(image));
                label.setPreferredSize(new java.awt.Dimension(width, height));

            }else {

            throw new RuntimeException("icon " + path + " not found in the classpath!");
        }
    } catch (IOException e) {

        throw new RuntimeException("Failed to load icon from " + path + " due to an I/O error.", e);
    }

    }

    public static void loadDashbaordImages(MainController mainController, ArrayList<? extends Bijoux> products, JPanel current,String role) {
        JPanel panelImages = new JPanel();
        panelImages.setLayout(new BoxLayout(panelImages, BoxLayout.Y_AXIS));
        panelImages.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, Color.LIGHT_GRAY));
        panelImages.setBackground(Color.WHITE);

        int imagesPerRow = 4;
        JPanel rowPanel = null;
        for (int i = 1; i <= products.size(); i++) {
            if (i % imagesPerRow == 1) {
                rowPanel = new JPanel();
                rowPanel.setBackground(Color.WHITE);
                rowPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
                rowPanel.setBorder(null);
                panelImages.add(rowPanel);
            }


            JPanel bijouxPanel = UtilDisplayingDashboards.createBijouxPanel(products.get(i - 1), mainController,role,products);


            bijouxPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            rowPanel.add(bijouxPanel);
        }

        JScrollPane scroll = new JScrollPane(panelImages, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setPreferredSize(new Dimension(450, 600));
        scroll.setBorder(null);

        current.add(scroll);

    }




}
