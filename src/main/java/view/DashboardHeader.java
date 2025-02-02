package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import controller.MainController;
import shared.UtilDisplayingDashboards;

public class DashboardHeader extends JPanel {
    private JLabel profileIcon;
    private JLabel cartIcon;
    private JLabel hotlineIcon;
    private JLabel logoutIcon;
    private JLabel logoIcon;
    private MainController mainController;

    public DashboardHeader(MainController mainController) {
        this.mainController = mainController;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 3, 7));
        leftPanel.setBackground(Color.WHITE);

        logoIcon = new JLabel();
        UtilDisplayingDashboards.loadIcon(logoIcon, "resources/logo.png", 120, 20);
        logoIcon.setVerticalAlignment(SwingConstants.CENTER);

        JButton allButton = new JButton("All");
        allButton.addActionListener(e -> mainController.showMainDashboardView());

        JButton ringButton = new JButton("Ring");
        ringButton.addActionListener(e -> mainController.showRingDashboardsView());

        JButton necklaceButton = new JButton("Necklace");
        necklaceButton.addActionListener(e -> mainController.showNecklaceDashboardsView());

        allButton.setMargin(new Insets(3, 13, 3, 13));
        ringButton.setMargin(new Insets(3, 10, 3, 10));
        necklaceButton.setMargin(new Insets(3, 10, 3, 10));

        leftPanel.add(logoIcon);
        leftPanel.add(Box.createHorizontalStrut(15));
        leftPanel.add(allButton);
        leftPanel.add(Box.createHorizontalStrut(15));
        leftPanel.add(ringButton);
        leftPanel.add(Box.createHorizontalStrut(15));
        leftPanel.add(necklaceButton);

        add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        rightPanel.setBackground(Color.WHITE);

        profileIcon = new JLabel();
        UtilDisplayingDashboards.loadIcon(profileIcon, "resources/profileIcon.png", 20, 20);

        cartIcon = new JLabel();
        UtilDisplayingDashboards.loadIcon(cartIcon, "resources/cartIcon.png", 20, 20);

        hotlineIcon = new JLabel();
        UtilDisplayingDashboards.loadIcon(hotlineIcon, "resources/hotlineIcon.png", 20, 20);

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

        if (mainController.getLoggedInClient() != null) {
            logoutIcon = new JLabel();
            UtilDisplayingDashboards.loadIcon(logoutIcon, "resources/logout.png", 15, 15);

            logoutIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    mainController.logout();
                }
            });

            rightPanel.add(logoutIcon);
        }

        add(rightPanel, BorderLayout.EAST);

        setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.LIGHT_GRAY));
    }
}
