package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.MainController;
import lombok.Getter;
import lombok.Setter;
import model.Bijoux;

@Getter
@Setter
public class MainDashboardView extends JPanel {
    private JLabel profileIcon;
    private JLabel cartIcon;
    private MainController mainController;

    public MainDashboardView(MainController mainController) {
        this.mainController = mainController;
    	mainController.fetchAllProducts();
        setLayout(new BorderLayout());

        JPanel navBar = new JPanel();
        navBar.setLayout(new FlowLayout(FlowLayout.RIGHT)); 
        navBar.setBackground(Color.WHITE);

        profileIcon = new JLabel();
        loadIcon(profileIcon, "ressources/profileIcon.png", 20, 15); 

        cartIcon = new JLabel();
        loadIcon(cartIcon, "ressources/cartIcon.png", 20, 15);

        navBar.setBorder(BorderFactory.createLineBorder(Color.black, 2, false));

        profileIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainController.showProfileInfoView();
            }
        });
        cartIcon.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		mainController.displayCart();
        		
        	}
        });

        navBar.add(profileIcon);
        navBar.add(cartIcon);
        add(navBar, BorderLayout.NORTH);
        
        this.loadDashbaordImages(mainController);
    }
    
    private void loadDashbaordImages(MainController mainController) {
    	
    	ArrayList<Bijoux> products = mainController.getProducts();
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
    			JPanel bijouxPanel = this.createBijouxPanel(products.get(i-1),mainController);
    			rowPanel.add(bijouxPanel);
    		
    	}
    	
    	JScrollPane scroll = new JScrollPane(panelImages);
    	scroll.setPreferredSize(new Dimension(450,600));
    	this.add(scroll);
    	
   
    
    	
    
    }

   
    private void loadIcon(JLabel label, String path, int width, int height) {
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream(path);
            if (input != null) {
                Image image = ImageIO.read(input);
                label.setIcon(new ImageIcon(image));
                label.setPreferredSize(new java.awt.Dimension(width, height)); 
                
            }else {
            
            throw new RuntimeException("icon " + path + " not found in the classpath!");
        }
    } catch (IOException e) {
        
        throw new RuntimeException("Failed to load icon from " + path + " due to an I/O error.", e);
    }
        
    }
    private Image loadImageBijou(String path) {
    	
    	try {
    		InputStream input= this.getClass().getClassLoader().getResourceAsStream(path);
    		if (input != null) {
    			BufferedImage image= ImageIO.read(input);
    			return image;
    		}else {
    			throw new RuntimeException("icon " + path + " not found in the classpath!");
    		}
    	}catch(IOException e) {
    		throw new RuntimeException("image " + path + " not found in the classpath!");
    	}
    }
    
    private JPanel createBijouxPanel(Bijoux bijou, MainController mainController) {
    	JPanel bijouPanel = new JPanel();
    	bijouPanel.setBackground(Color.white);
    	
    	bijouPanel.setLayout(new BorderLayout());
    	bijouPanel.setPreferredSize(new Dimension(100,150));
    	bijouPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    	
    	JPanel imagePanel = new JPanel();
    	imagePanel.setLayout(new BoxLayout(imagePanel,BoxLayout.Y_AXIS));
    	imagePanel.setBackground(Color.white);
    	
    	Image bijouImg = this.loadImageBijou(bijou.getImagePath());
    	if(bijouImg !=null) {
    		Image scaledImage = bijouImg.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
    		JLabel bijouLabel = new JLabel(new ImageIcon(scaledImage));
    		JButton addToCartButton = new JButton("Add to Cart");
    		
    		addToCartButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					mainController.getClientCart().addToCart(bijou);
					
					
				}
    			
    		});
    		
    		imagePanel.add(bijouLabel);
    		imagePanel.add(Box.createRigidArea(new Dimension(0, 5))); 
            imagePanel.add(addToCartButton);
            bijouPanel.add(imagePanel, BorderLayout.CENTER);
        } else {
            bijouPanel.add(new JLabel("Image not available"), BorderLayout.CENTER); 
        }

        return bijouPanel;
    	
    }
}
