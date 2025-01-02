package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.MainController;
import model.Bijoux;
import model.Cart;
import model.OrderStatus;
import shared.UtilDisplayingDashboards;


public class CartView extends JPanel {
	private Cart cart;

	public CartView(MainController mainController) {
		
		this.cart= mainController.getClientCart();
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		JPanel panelImages = new JPanel();
    	panelImages.setLayout(new BoxLayout(panelImages,BoxLayout.Y_AXIS));
    	panelImages.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    	
    	JButton  closeButton = new JButton("Close");
    	
    	
    	panelImages.add(closeButton, BorderLayout.NORTH);
		//this.add(closeButton, BorderLayout.NORTH);
    	
    	
    	panelImages.setBackground(Color.white);
    	panelImages.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    	this.setBackground(Color.white);
    	JPanel rowPanel = null;
    	int imagesPerRow = 4;
    	int i=0;
    	for(Bijoux bijoux:cart.getCart().keySet()) {
    		i++;
    		if(i % imagesPerRow == 1) {
    			rowPanel = new JPanel();
    	        rowPanel.setBackground(Color.white);
    	        rowPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
    	        panelImages.add(rowPanel);
    		}
    		JPanel bijouxPanel = this.createBijouxPanel(bijoux, mainController);
    	    rowPanel.add(bijouxPanel);
    	}
    	
    	JScrollPane scroll = new JScrollPane(panelImages);
    	scroll.setPreferredSize(new Dimension(450,600));
    	this.add(scroll);
    	
    	
    	closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainController.showMainDashboardView();
				
			}
		});
    	JButton  confirmButton = new JButton("Confirm Purchase");
    	confirmButton.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			mainController.ChangeOrderStatus(OrderStatus.VALIDEE);
				cart.getCart().clear();
				mainController.showMainDashboardView();
    		}
    	});
		panelImages.add(confirmButton, BorderLayout.SOUTH);
    	
   
	}
	
	private JPanel createBijouxPanel(Bijoux bijou, MainController mainController) {
	    JPanel bijouPanel = new JPanel();
	    bijouPanel.setBackground(Color.white);
	    bijouPanel.setLayout(new BorderLayout());
	    bijouPanel.setPreferredSize(new Dimension(180, 180)); 
	    bijouPanel.setBorder(BorderFactory.createLineBorder(Color.black));

	    
	    JPanel imagePanel = new JPanel();
	    imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
	    imagePanel.setBackground(Color.white);
	    
	   
	    bijouPanel.add(imagePanel, BorderLayout.CENTER);

	    Image bijouImg = UtilDisplayingDashboards.loadImageBijou(bijou.getImagePath());
	    if (bijouImg != null) {
	        Image scaledImage = bijouImg.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
	        JLabel bijouLabel = new JLabel(new ImageIcon(scaledImage));
	        bijouLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);  
	        imagePanel.add(bijouLabel);
	        imagePanel.add(Box.createRigidArea(new Dimension(0, 5))); 
	    } else {
	        JLabel noImageLabel = new JLabel("Image not available");
	        noImageLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);  
	        imagePanel.add(noImageLabel);
	    }

	    
	    JLabel descriptionLabel = new JLabel("<html><center>" + bijou.getDescription() + "</center></html>");
	    descriptionLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);  
	    imagePanel.add(descriptionLabel);
	    imagePanel.add(Box.createRigidArea(new Dimension(0, 5)));  

	   
	    JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
	    quantityPanel.setBackground(Color.white);
	    
	    int stock =bijou.getStock();
        JLabel stocklabel = new JLabel("Stock: " + stock);
        stocklabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        imagePanel.add(stocklabel);

	    

	    JLabel quantityLabel = new JLabel("Quantity: " + mainController.getClientCart().getCart().getOrDefault(bijou, 0));
	    JButton addButton = new JButton("+");
	    JButton removeButton = new JButton("-");
	
	    
	    addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				 int newQ = mainController.getClientCart().addToCart(bijou);
			      quantityLabel.setText("Quantity: " +newQ);
			}
		});
	    
	   

	

	    removeButton.addActionListener(e -> {
	        mainController.getClientCart().removeFromCart(bijou);
	        int newQuantity = mainController.getClientCart().getCart().getOrDefault(bijou, 0);
	        quantityLabel.setText("Quantity: " + newQuantity);
	    });
	    
	    

	    quantityPanel.add(removeButton);
	    quantityPanel.add(quantityLabel);
	    quantityPanel.add(addButton);
	    imagePanel.add(quantityPanel);

	    return bijouPanel;
	}

	
public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}
	

}
