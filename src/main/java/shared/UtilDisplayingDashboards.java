package shared;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.MainController;
import model.Bijoux;
import model.Ring;
import view.MainDashboardView;

public class UtilDisplayingDashboards {
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
    
    public static JPanel createBijouxPanel(Bijoux bijou, MainController mainController) {
        JPanel bijouPanel = new JPanel();
        bijouPanel.setBackground(Color.white);
        bijouPanel.setLayout(new BorderLayout());
        bijouPanel.setPreferredSize(new Dimension(150, 180));
        bijouPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
        imagePanel.setBackground(Color.white);

        // Load and display image
        Image bijouImg = UtilDisplayingDashboards.loadImageBijou(bijou.getImagePath());
        if (bijouImg != null) {
            Image scaledImage = bijouImg.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            JLabel bijouLabel = new JLabel(new ImageIcon(scaledImage));
            bijouLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);  
            imagePanel.add(bijouLabel);
        } else {
            imagePanel.add(new JLabel("Image not available"));
        }

   
        JLabel descriptionLabel = new JLabel("<html><center>" + bijou.getDescription() + "</center></html>");
        descriptionLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        imagePanel.add(descriptionLabel);

    
        int stock =bijou.getStock();
        JLabel quantityLabel = new JLabel("Stock: " + stock);
        quantityLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        imagePanel.add(quantityLabel);

     
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("add to cart");
      

        addButton.addActionListener(e -> {
            mainController.addToCart(bijou);
            
          
        });

       

    
        buttonPanel.add(addButton);
        imagePanel.add(buttonPanel);

        bijouPanel.add(imagePanel, BorderLayout.CENTER);
        return bijouPanel;
    }
    
    public static  void loadIcon(JLabel label, String path, int width, int height) {
        try {
            InputStream input = MainDashboardView.class.getClassLoader().getResourceAsStream(path);
            if (input != null) {
            	
            	
                Image image = ImageIO.read(input);
                
                if (path.contains("hotlineIcon")) {
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
    
public static void loadDashbaordImages( MainController mainController,ArrayList<? extends Bijoux> products, JPanel current) {
    	
    	JPanel panelImages = new JPanel();
    	panelImages.setLayout(new BoxLayout(panelImages,BoxLayout.Y_AXIS));
    	panelImages.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    	
    	panelImages.setBackground(Color.white);
    	
    	int imagesPerRow = 4;
    	JPanel rowPanel = null;
    	for(int i = 1; i <=products.size();i++) {
    		if(i % imagesPerRow ==1) {
    			rowPanel =new JPanel();
    			rowPanel.setBackground(Color.white);
    			rowPanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
    			panelImages.add(rowPanel);
    		}
    			JPanel bijouxPanel = UtilDisplayingDashboards.createBijouxPanel(products.get(i-1),mainController);
    			rowPanel.add(bijouxPanel);
    		
    	}
    	
    	JScrollPane scroll = new JScrollPane(panelImages);
    	scroll.setPreferredSize(new Dimension(450,600));
    	current.add(scroll);
    }


    
}
