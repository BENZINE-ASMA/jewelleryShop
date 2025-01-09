package view.admin;

import java.awt.BorderLayout;
import controller.MainController;
import shared.UtilDisplayingDashboards;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainDashboardAdminView extends JPanel {
    private MainController mainController;

    public MainDashboardAdminView(MainController mainController) {
        this.mainController = mainController;
        mainController.fetchAllProducts();
        setLayout(new BorderLayout());

        DashboardHeaderAdmin header = new DashboardHeaderAdmin(mainController);
        add(header, BorderLayout.NORTH);

        System.out.println("hilo");
        UtilDisplayingDashboards.loadDashbaordImages(mainController,mainController.getProducts(), this);
    }
}
