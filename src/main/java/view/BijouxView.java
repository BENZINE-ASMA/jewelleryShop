package view;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BijouxView extends JFrame {

    public BijouxView() {
        super("Vente de Bijoux");

        // Panneau principal pour toutes les images (conteneur global)
        JPanel panelImages = new JPanel();
        panelImages.setLayout(new BoxLayout(panelImages, BoxLayout.Y_AXIS)); // Disposition verticale pour les rangées
        panelImages.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // top, left, bottom, right padding

        // Définir la couleur de fond du panneau sur blanc
        panelImages.setBackground(Color.WHITE);
        // Nombre maximal d'images par rangée
        int imagesPerRow = 4;

        // Panel pour contenir une rangée d'images
        JPanel rowPanel = null;
        

        // Boucle pour afficher les 10 bijoux
        for (int i = 1; i <= 10; i++) {
            // Créer un nouveau panel de rangée tous les 4 items
            if (i % imagesPerRow == 1) {
                rowPanel = new JPanel();
                rowPanel.setBackground(Color.WHITE);
                rowPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // FlowLayout pour aligner les images
                panelImages.add(rowPanel); // Ajouter la rangée au panneau principal
            }

            // Créer le panel pour le bijou
            JPanel bijouPanel = createBijouPanel("Bijou " + i, "ressources/profileIcon.png", "Description du Bijou " + i);
            rowPanel.add(bijouPanel); // Ajouter le bijou à la rangée actuelle
        }

        // Mettre le JPanel dans un JScrollPane pour permettre le défilement
        JScrollPane scrollPane = new JScrollPane(panelImages);
        scrollPane.setPreferredSize(new Dimension(450, 600)); // Taille de la fenêtre avec scrolling

        // Ajouter le JScrollPane à la fenêtre
        getContentPane().add(scrollPane);

        // Paramètres de la fenêtre
        setSize(1000, 700); // Taille de la fenêtre principale
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Méthode pour créer un panel pour chaque bijou
    private JPanel createBijouPanel(String name, String imagePath, String description) {
        // Panel principal pour chaque bijou
        JPanel bijouPanel = new JPanel();
        bijouPanel.setBackground(Color.WHITE);
       
        bijouPanel.setLayout(new BorderLayout());
        bijouPanel.setPreferredSize(new Dimension(100, 150)); // Taille préférée pour chaque bijou
        bijouPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Bordure noire autour du panel

        // Panel pour l'image et le bouton
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS)); // Pour placer l'image et le bouton verticalement
        imagePanel.setBackground(Color.WHITE);
        // Charger et redimensionner l'image
        Image image = loadProfileIcon(imagePath);
        if (image != null) {
            Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));

            // Bouton pour ajouter au panier
            JButton addToCartButton = new JButton("Add to Cart");

            // Ajouter l'image et le bouton dans le même panel
            imagePanel.add(imageLabel);
            imagePanel.add(Box.createRigidArea(new Dimension(0, 5))); // Espace entre l'image et le bouton
            imagePanel.add(addToCartButton);
            

            // Ajouter le panel de l'image et du bouton à gauche
            bijouPanel.add(imagePanel, BorderLayout.CENTER);
        } else {
            bijouPanel.add(new JLabel("Image not available"), BorderLayout.CENTER); // Si l'image n'est pas disponible
        }

        return bijouPanel;
    }

    // Méthode pour charger une image depuis le classpath
    private Image loadProfileIcon(String path) {
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream(path);
            if (input != null) {
                BufferedImage image = ImageIO.read(input);
                return image;
            } else {
                System.out.println("Resource " + path + " not found in the classpath!");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        new BijouxView();
    }
}
