package view.admin;

import java.awt.BorderLayout;
import java.util.ArrayList;

import controller.AdminController;
import controller.MainController;
import model.Bijoux;
import shared.UtilDisplayingDashboards;
import view.FilterDashbaordPanel;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainDashboardAdminView extends JPanel {
    private MainController mainController;
    private AdminController adminController;
    private ArrayList<Bijoux> FilteredDashboard;

    public MainDashboardAdminView(MainController mainController,AdminController adminController) {
        this.mainController = mainController;
        this.adminController=adminController;
        mainController.fetchAllProducts();
        FilteredDashboard = mainController.getProducts();
        setLayout(new BorderLayout());

        DashboardHeaderAdmin header = new DashboardHeaderAdmin(adminController);
        add(header, BorderLayout.NORTH);
        JPanel filterPanel = new FilterDashbaordPanel(this.FilteredDashboard,this.getHeight(),mainController, this,"ALL");
        add(filterPanel, BorderLayout.WEST);

        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new BorderLayout());
        add(dashboardPanel, BorderLayout.CENTER);

        UtilDisplayingDashboards.loadDashbaordImages(mainController,FilteredDashboard, dashboardPanel);
    }
}
