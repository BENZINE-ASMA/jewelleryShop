package view;

import java.awt.BorderLayout;
import controller.MainController;
import model.Bijoux;
import shared.UtilDisplayingDashboards;

import javax.swing.JPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventListener;

public class MainDashboardView extends JPanel {
    private MainController mainController;
    private ArrayList<Bijoux> FilteredDashboard;

    public MainDashboardView(MainController mainController) {
        this.mainController = mainController;
        mainController.fetchAllProducts();
        FilteredDashboard = mainController.getProducts();
        setLayout(new BorderLayout());

        DashboardHeader header = new DashboardHeader(mainController);
        add(header, BorderLayout.NORTH);

        JPanel filterPanel = new FilterDashbaordPanel(this.FilteredDashboard, this.getHeight(), mainController, this, "ALL");
        add(filterPanel, BorderLayout.WEST);

        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new BorderLayout());
        dashboardPanel.setBorder(null); // Ensure no border on this panel
        add(dashboardPanel, BorderLayout.CENTER);

        UtilDisplayingDashboards.loadDashbaordImages(mainController, FilteredDashboard, dashboardPanel);
    }
}