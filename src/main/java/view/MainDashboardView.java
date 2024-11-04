package view;

import java.awt.BorderLayout;
import controller.MainController;
import shared.UtilDisplayingDashboards;

import javax.swing.JPanel;

public class MainDashboardView extends JPanel {
    private MainController mainController;

    public MainDashboardView(MainController mainController) {
        this.mainController = mainController;
        mainController.fetchAllProducts();
        setLayout(new BorderLayout());

        DashboardHeader header = new DashboardHeader(mainController);
        add(header, BorderLayout.NORTH);

       
        UtilDisplayingDashboards.loadDashbaordImages(mainController,mainController.getProducts(), this);
    }
}
