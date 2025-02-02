package view.admin;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import controller.AdminController;
import shared.UtilDisplayingDashboards;

public class DashboardHeaderAdmin extends JPanel {
    private JLabel hotlineIcon;
    private JLabel logoutIcon;
    private JButton catalogue, products, clients, invoices;
    private AdminController adminController;

    public DashboardHeaderAdmin(AdminController adminController) {
        this.adminController = adminController;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        leftPanel.setBackground(Color.WHITE);

        this.catalogue = new JButton("Catalogue");
        catalogue.addActionListener(e -> adminController.ShowMaindashboardAdminView());

        this.products = new JButton("PRODUCTS");
        products.addActionListener(e -> adminController.showProductsDashbaordView());

        this.clients = new JButton("CLIENTS");
        clients.addActionListener(e -> adminController.showClientsDashbaordView());

        this.invoices = new JButton("INVOICES");
        invoices.addActionListener(e -> adminController.showInvoiceView());

        leftPanel.add(catalogue);
        leftPanel.add(Box.createHorizontalStrut(15));
        leftPanel.add(products);
        leftPanel.add(Box.createHorizontalStrut(15));
        leftPanel.add(clients);
        leftPanel.add(Box.createHorizontalStrut(15));
        leftPanel.add(invoices);

        add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        rightPanel.setBackground(Color.WHITE);

        hotlineIcon = new JLabel();
        UtilDisplayingDashboards.loadIcon(hotlineIcon, "resources/hotlineIcon.png", 20, 15);

        hotlineIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });

        rightPanel.add(hotlineIcon);

        if (adminController.getLoggedInAdmin() != null) {
            logoutIcon = new JLabel();
            UtilDisplayingDashboards.loadIcon(logoutIcon, "resources/logout.png", 20, 20);

            logoutIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    adminController.logout();
                }
            });

            rightPanel.add(logoutIcon);
        }

        add(rightPanel, BorderLayout.EAST);

        setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.LIGHT_GRAY));
    }
}
