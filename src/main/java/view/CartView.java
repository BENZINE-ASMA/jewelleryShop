package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.*;

import controller.MainController;
import model.Bijoux;
import model.Cart;
import model.OrderStatus;
import shared.UtilDisplayingDashboards;


public class CartView extends JPanel {
	private Cart cart;

	public CartView(MainController mainController) {
		this.cart = mainController.getClientCart();
		this.setLayout(new BorderLayout());

		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(e -> mainController.showMainDashboardView());

		JButton confirmButton = new JButton("Confirm Purchase");
		confirmButton.addActionListener(e -> {
			if (mainController.getLoggedInClient() == null) {
				JOptionPane.showMessageDialog(
						CartView.this,
						"You need to log in to confirm your purchase.",
						"Login Required",
						JOptionPane.WARNING_MESSAGE
				);
				mainController.showLoginView(false, "Please log in to complete your purchase.");
			} else {
				mainController.ChangeOrderStatus(OrderStatus.VALIDEE);
				cart.getCart().clear();
				mainController.showMainDashboardView();
			}
		});

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(closeButton);
		buttonPanel.add(confirmButton);

		add(buttonPanel, BorderLayout.NORTH);
		refreshCartView(mainController);
	}

	private void refreshCartView(MainController mainController) {
		this.removeAll(); // Remove all components to refresh the view

		JPanel panelImages = new JPanel();
		panelImages.setLayout(new BoxLayout(panelImages, BoxLayout.Y_AXIS));
		panelImages.setBackground(Color.white);
		panelImages.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JPanel rowPanel = null;
		int imagesPerRow = 4;
		int i = 0;
		for (Bijoux bijoux : cart.getCart().keySet()) {
			i++;
			if (i % imagesPerRow == 1) {
				rowPanel = new JPanel();
				rowPanel.setBackground(Color.white);
				rowPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
				rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));
				panelImages.add(rowPanel);
				panelImages.add(Box.createRigidArea(new Dimension(0, 5)));
			}
			JPanel bijouxPanel = createBijouxPanel(bijoux, mainController);
			rowPanel.add(bijouxPanel);
		}

		JScrollPane scroll = new JScrollPane(panelImages);
		scroll.setPreferredSize(new Dimension(450, 600));
		scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		add(scroll, BorderLayout.CENTER);

		revalidate();
		repaint();
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

		JLabel quantityLabel = new JLabel("Quantity: " + cart.getCart().getOrDefault(bijou, 0));
		JButton addButton = new JButton("+");
		JButton removeButton = new JButton("-");

		addButton.addActionListener(e -> {
			int newQ = mainController.getClientCart().addToCart(bijou);
			quantityLabel.setText("Quantity: " + newQ);
		});

		removeButton.addActionListener(e -> {
			mainController.getClientCart().removeFromCart(bijou);
			if (mainController.getClientCart().getCart().getOrDefault(bijou, 0) == 0) {
				cart.getCart().remove(bijou);
				refreshCartView(mainController);
			} else {
				quantityLabel.setText("Quantity: " + cart.getCart().getOrDefault(bijou, 0));
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
