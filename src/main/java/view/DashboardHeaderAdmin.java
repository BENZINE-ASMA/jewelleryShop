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

public class DashboardHeaderAdmin extends JPanel {
    private JLabel profileIcon;
    private JLabel cartIcon;
    private JLabel hotlineIcon;
    private JTextField searchField;
    private MainController mainController;

    public DashboardHeaderAdmin(MainController mainController) {
        this.mainController = mainController;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(Color.WHITE);

        JButton allButton = new JButton("All");
        allButton.addActionListener(e->{
        	mainController.showMainDashboardView();
        });
        JButton ringButton = new JButton("Ring");
        ringButton.addActionListener(e -> {
            mainController.showRingDashboardsView();
        });

        JButton necklaceButton = new JButton("Necklace");
        necklaceButton.addActionListener(e -> {
            mainController.showNecklaceDashboardsView();
        });
        
        JButton invoices = new JButton("Invoices");
        invoices.addActionListener(e -> {
            mainController.showNecklaceDashboardsView();
        });
        JButton clients = new JButton("Clients");
        clients.addActionListener(e -> {
            mainController.showNecklaceDashboardsView();
        });
        
        JButton orders = new JButton("Orders");
        orders.addActionListener(e -> {
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
        
        leftPanel.add(allButton);
        leftPanel.add(ringButton);
        leftPanel.add(necklaceButton);
        leftPanel.add(searchField);
        leftPanel.add(searchButton);

        add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(Color.WHITE);

        profileIcon = new JLabel();
        UtilDisplayingDashboards.loadIcon(profileIcon, "ressources/profileIcon.png", 20, 15);

        cartIcon = new JLabel();
        UtilDisplayingDashboards.loadIcon(cartIcon, "ressources/cartIcon.png", 20, 15);
        
        hotlineIcon = new JLabel();
        UtilDisplayingDashboards.loadIcon(hotlineIcon, "ressources/hotlineIcon.png", 20, 15);


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
        
        hotlineIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainController.showCartView();
            }
        });
        
        rightPanel.add(profileIcon);
        rightPanel.add(cartIcon);
        rightPanel.add(hotlineIcon);

        add(rightPanel, BorderLayout.EAST);

        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, false));
    }
}
