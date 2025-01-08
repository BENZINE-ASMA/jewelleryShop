package view.admin;

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

import controller.AdminController;
import controller.MainController;
import shared.UtilDisplayingDashboards;

public class DashboardHeaderAdmin extends JPanel {
    private JLabel hotlineIcon;
    private JButton catalogue,products,clients,invoices;
    private AdminController adminController;

    public DashboardHeaderAdmin(AdminController adminController) {
        this.adminController=adminController;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(Color.WHITE);

        this.catalogue = new JButton("Catalogue");
        catalogue.addActionListener(e->{
            adminController.ShowMaindashboardAdminView();
        });

        this.products = new JButton("PRODUCTS");
        products.addActionListener(e->{
            adminController.showProductsDashbaordView();
        });
        this.clients = new JButton("CLIENTS");
        clients.addActionListener(e -> {
           adminController.showClientsDashbaordView();
        });

        this.invoices  = new JButton("INVOICES");
        invoices.addActionListener(e -> {
            adminController.showInvoiceView();
        });


        leftPanel.add(catalogue);
        leftPanel.add(products);
        leftPanel.add(clients);
        leftPanel.add(invoices);

        add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(Color.WHITE);



        hotlineIcon = new JLabel();
        UtilDisplayingDashboards.loadIcon(hotlineIcon, "resources/hotlineIcon.png", 20, 15);






        hotlineIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //mainController.showCartView();
            }
        });
        rightPanel.add(hotlineIcon);

        add(rightPanel, BorderLayout.EAST);

        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, false));
    }
}
