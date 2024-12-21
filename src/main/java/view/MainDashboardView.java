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

        JPanel filterPanel = createFilterPanel();
        add(filterPanel, BorderLayout.WEST);


        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new BorderLayout());
        add(dashboardPanel, BorderLayout.CENTER);


        UtilDisplayingDashboards.loadDashbaordImages(mainController,FilteredDashboard, dashboardPanel);
    }

    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        filterPanel.setPreferredSize(new Dimension(200, getHeight()));
        filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        filterPanel.setBackground(Color.white);


        JLabel productTypeLabel = new JLabel("Type de produit");
        filterPanel.add(productTypeLabel);

        String[] productTypes = {"All", "Bagues", "Colliers"};
        JComboBox<String> productTypeComboBox = new JComboBox<>(productTypes);
        filterPanel.add(productTypeComboBox);

        JLabel productMatièreLabel = new JLabel("Matière");
        filterPanel.add(productMatièreLabel);

        String[] matière = {"All", "acier", "silver"};
        JComboBox<String> productMatièreComboBox = new JComboBox<>(matière);
        filterPanel.add(productMatièreComboBox);
        JLabel priceLabel = new JLabel("Prix");
        filterPanel.add(priceLabel);

        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new FlowLayout());
        pricePanel.add(new JLabel("de"));
        JTextField minPriceField = new JTextField(5);
        pricePanel.add(minPriceField);
        pricePanel.add(new JLabel("à"));
        JTextField maxPriceField = new JTextField(5);
        pricePanel.add(maxPriceField);
        filterPanel.add(pricePanel);


        JButton filterButton = new JButton("Filtrer");
        filterButton.addActionListener(new ActionListener() {
            /*
            'Diamond Ring' and brand = 'Luxury' and type ='Ring' and description='A beautiful diamond ring with a sleek design.'
and price ='299.99' and material='Gold';
             */
            @Override
            public void actionPerformed(ActionEvent e) {
            mainController.fetchAllFilteredProducts(FilteredDashboard,"Diamond Ring","A beautiful diamond ring with a sleek design.",
                   "Luxury","Ring" ,"299.99","Gold");
                refreshDashboard();
            }
        });
        filterPanel.add(filterButton);

        return filterPanel;
    }

    private void refreshDashboard() {
        JPanel dashboardPanel = (JPanel) getComponent(2);
        dashboardPanel.removeAll();
        UtilDisplayingDashboards.loadDashbaordImages(mainController, FilteredDashboard, dashboardPanel);
        dashboardPanel.revalidate();
        dashboardPanel.repaint();
    }


}