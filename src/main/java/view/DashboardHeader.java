package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import controller.MainController;
import shared.UtilDisplayingDashboards;

public class DashboardHeader extends JPanel {
    private JLabel profileIcon;
    private JLabel cartIcon;
    private JTextField searchField;
    private MainController mainController;

    // Constructor to initialize the header with a reference to the MainController
    public DashboardHeader(MainController mainController) {
        this.mainController = mainController;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Create left panel with buttons and search field
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(Color.WHITE);

        JButton ringButton = new JButton("Ring");
        ringButton.addActionListener(e -> {
            mainController.showRingDashboardsView();
        });

        JButton necklaceButton = new JButton("Necklace");
        necklaceButton.addActionListener(e -> {
            mainController.showNecklaceDashboardsView();
        });

        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            String query = searchField.getText().trim();
            if (!query.isEmpty()) {
                // Implement search logic if needed
            }
        });

        leftPanel.add(ringButton);
        leftPanel.add(necklaceButton);
        leftPanel.add(searchField);
        leftPanel.add(searchButton);

        // Add the left panel to the left side of the header
        add(leftPanel, BorderLayout.WEST);

        // Create right panel with profile and cart icons
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(Color.WHITE);

        profileIcon = new JLabel();
        UtilDisplayingDashboards.loadIcon(profileIcon, "ressources/profileIcon.png", 20, 15);

        cartIcon = new JLabel();
        UtilDisplayingDashboards.loadIcon(cartIcon, "ressources/cartIcon.png", 20, 15);

        profileIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainController.showProfileInfoView();
            }
        });

        cartIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainController.showCartView();
            }
        });

        rightPanel.add(profileIcon);
        rightPanel.add(cartIcon);

        // Add the right panel to the right side of the header
        add(rightPanel, BorderLayout.EAST);

        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, false));
    }
}
