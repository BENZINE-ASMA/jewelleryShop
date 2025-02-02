package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

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
		JButton  closeButton = new JButton("Close");
		JPanel panelImages = new JPanel();
		panelImages.setLayout(new BoxLayout(panelImages, BoxLayout.Y_AXIS));
		panelImages.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Adjust margins for panelImages
		panelImages.setBackground(Color.white);

		JPanel rowPanel = null;
		int imagesPerRow = 4;
		int i = 0;
		for (Bijoux bijoux : cart.getCart().keySet()) {
			i++;
			if (i % imagesPerRow == 1) {
				rowPanel = new JPanel();
				rowPanel.setBackground(Color.white);
				rowPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5)); // Set smaller vertical gap
				panelImages.add(rowPanel);
			}
			JPanel bijouxPanel = this.createBijouxPanel(bijoux, mainController, rowPanel);
			rowPanel.add(bijouxPanel);
		}

		JScrollPane scroll = new JScrollPane(panelImages);
		scroll.setPreferredSize(new Dimension(450, 600));
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
				if (mainController.getLoggedInClient() == null) {
					// Show login view and notify the user to log in
					JOptionPane.showMessageDialog(
							CartView.this,
							"You need to log in to confirm your purchase.",
							"Login Required",
							JOptionPane.WARNING_MESSAGE
					);
					mainController.showLoginView(false, "Please log in to complete your purchase.");
				} else {
					// Confirm purchase if the user is logged in
					mainController.ChangeOrderStatus(OrderStatus.VALIDEE);
					cart.getCart().clear(); // Clear the cart after purchase
					mainController.showMainDashboardView();
				}
			}
		});

		panelImages.add(confirmButton, BorderLayout.SOUTH);
    	
   
	}

	private JPanel createBijouxPanel(Bijoux bijou, MainController mainController, JPanel rowPanel) {
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

		int stock = bijou.getStock();
		JLabel stockLabel = new JLabel("Stock: " + stock);
		stockLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		imagePanel.add(stockLabel);

		JLabel quantityLabel = new JLabel("Quantity: " + mainController.getClientCart().getCart().getOrDefault(bijou, 0));
		JButton addButton = new JButton("+");
		JButton removeButton = new JButton("-");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int newQ = mainController.getClientCart().addToCart(bijou);
				quantityLabel.setText("Quantity: " + newQ);
			}
		});
		removeButton.addActionListener(e -> {
			mainController.getClientCart().removeFromCart(bijou);
			int newQuantity = mainController.getClientCart().getCart().getOrDefault(bijou, 0);
			quantityLabel.setText("Quantity: " + newQuantity);

			if (newQuantity == 0) {
				rowPanel.remove(bijouPanel);
				rowPanel.revalidate();
				rowPanel.repaint();
			}
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
