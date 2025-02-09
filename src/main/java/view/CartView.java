package view;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import controller.MainController;
import model.Bijoux;
import model.Cart;
import model.OrderStatus;
import shared.UtilDisplayingDashboards;

public class CartView extends JPanel {
	private Cart cart;
	private JPanel buttonPanelTop; // Top panel for the "Close" button
	private JPanel buttonPanelBottom; // Bottom panel for the "Confirm Purchase" button

	public CartView(MainController mainController) {
		this.cart = mainController.getClientCart();
		this.setLayout(new BorderLayout());


		buttonPanelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(e -> mainController.showMainDashboardView());
		buttonPanelTop.add(closeButton);

		// Bottom Button Panel (for "Confirm Purchase" button)
		buttonPanelBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
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
				mainController.decrementStockForConfirmedOrder(cart);

				mainController.ChangeOrderStatus(OrderStatus.VALIDEE);
				cart.getCart().clear();

				mainController.createAndShowMaindashboardView();
			}
		});


		buttonPanelBottom.add(confirmButton);

		// Add the button panels
		add(buttonPanelTop, BorderLayout.NORTH);
		add(buttonPanelBottom, BorderLayout.SOUTH);

		refreshCartView(mainController);
	}

	private void refreshCartView(MainController mainController) {
		// Remove only the CENTER panel content while keeping the button panels
		this.removeAll();
		add(buttonPanelTop, BorderLayout.NORTH); // Re-add the top button panel
		add(buttonPanelBottom, BorderLayout.SOUTH); // Re-add the bottom button panel

		JPanel cartPanel = new JPanel();
		cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));
		cartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		cartPanel.setBackground(Color.WHITE);

		JPanel rowPanel = null;
		int itemsPerRow = 4;
		int count = 0;

		for (Bijoux bijoux : new ArrayList<>(cart.getCart().keySet())) {
			if (count % itemsPerRow == 0) {
				rowPanel = new JPanel();
				rowPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
				rowPanel.setBackground(Color.WHITE);
				rowPanel.setBorder(null);
				cartPanel.add(rowPanel);
			}

			JPanel bijouxPanel = createBijouxPanel(bijoux, mainController);
			rowPanel.add(bijouxPanel);
			count++;
		}

		JScrollPane scrollPane = new JScrollPane(cartPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(800, 600));
		scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		add(scrollPane, BorderLayout.CENTER);
		revalidate();
		repaint();
	}

	private JPanel createBijouxPanel(Bijoux bijou, MainController mainController) {
		JPanel bijouPanel = new JPanel();
		bijouPanel.setBackground(Color.WHITE);
		bijouPanel.setLayout(new BorderLayout());
		bijouPanel.setPreferredSize(new Dimension(180, 220));
		bijouPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

		// Image Panel
		JPanel imagePanel = new JPanel(new BorderLayout());
		imagePanel.setPreferredSize(new Dimension(180, 140));
		imagePanel.setBackground(Color.WHITE);

		Image bijouImg = UtilDisplayingDashboards.loadImageBijou(bijou.getImagePath());
		JLabel bijouLabel;
		if (bijouImg != null) {
			Image scaledImage = bijouImg.getScaledInstance(130, 130, Image.SCALE_SMOOTH);
			bijouLabel = new JLabel(new ImageIcon(scaledImage));
		} else {
			bijouLabel = new JLabel("Image not available");
		}
		bijouLabel.setHorizontalAlignment(JLabel.CENTER);
		bijouLabel.setVerticalAlignment(JLabel.CENTER);
		imagePanel.add(bijouLabel, BorderLayout.CENTER);
		
		imagePanel.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (e.getClickCount() == 2) {
					new ProductDetailView(bijou, mainController);
				}
			}
		});

		// Info Panel
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.setBackground(Color.WHITE);

		JLabel nameLabel = new JLabel("<html><center>" + bijou.getName() + "</center></html>");
		nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel priceLabel = new JLabel("Price: €" + bijou.getPrice());
		priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel stockLabel = new JLabel("Stock: " + bijou.getStock() + " available");
		stockLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		infoPanel.add(nameLabel);
		infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		infoPanel.add(priceLabel);
		infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		infoPanel.add(stockLabel);

		// Quantity Panel
		JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		quantityPanel.setBackground(Color.WHITE);

		JLabel quantityLabel = new JLabel("Quantity: " + cart.getCart().getOrDefault(bijou, 0));
		JButton addButton = new JButton("+");
		JButton removeButton = new JButton("-");

		addButton.addActionListener(e -> {
			int newQuantity = mainController.getClientCart().addToCart(bijou);
			quantityLabel.setText("Quantity: " + newQuantity);

		});

		removeButton.addActionListener(e -> {
			mainController.getClientCart().removeFromCart(bijou);
			int newQuantity = mainController.getClientCart().getCart().getOrDefault(bijou, 0);
			quantityLabel.setText("Quantity: " + newQuantity);

			if (newQuantity == 0) {
				cart.getCart().remove(bijou);
				refreshCartView(mainController);


				if (UtilDisplayingDashboards.addToCartButtons.containsKey(bijou)) {
					UtilDisplayingDashboards.addToCartButtons.get(bijou).setEnabled(true);
				}
			}
		});


		quantityPanel.add(removeButton);
		quantityPanel.add(quantityLabel);
		quantityPanel.add(addButton);

		infoPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between sections
		infoPanel.add(quantityPanel);

		bijouPanel.add(imagePanel, BorderLayout.CENTER);
		bijouPanel.add(infoPanel, BorderLayout.SOUTH);

		return bijouPanel;
	}


	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}
}
