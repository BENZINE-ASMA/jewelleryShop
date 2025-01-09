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

        //JPanel filterPanel = createFilterPanel();
        JPanel filterPanel = new FilterDashbaordPanel(this.FilteredDashboard,this.getHeight(),mainController, this);
        add(filterPanel, BorderLayout.WEST);
       // revalidate();
        //repaint();


        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new BorderLayout());
        add(dashboardPanel, BorderLayout.CENTER);


        UtilDisplayingDashboards.loadDashbaordImages(mainController,FilteredDashboard, dashboardPanel);
    }
}
/*
    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        filterPanel.setPreferredSize(new Dimension(200, getHeight()));
        filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        filterPanel.setBackground(Color.white);

        filterPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel nameLabel = new JLabel("Name of product");
        filterPanel.add(nameLabel);
        JTextField nameField = new JTextField(5);
        nameField.setPreferredSize(new Dimension(5,10));
        filterPanel.add(nameField);

        filterPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel descLabel = new JLabel("Description");
        filterPanel.add(descLabel);
        JTextField descField = new JTextField(5);
        filterPanel.add(descField);

        filterPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel productTypeLabel = new JLabel("Type de produit");
        filterPanel.add(productTypeLabel);
        String[] productTypes = {"All", "Bagues", "Colliers"};
        JComboBox<String> productTypeComboBox = new JComboBox<>(productTypes);
        filterPanel.add(productTypeComboBox);


        filterPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel brandLabel = new JLabel("Brand");
        filterPanel.add(brandLabel);
        JTextField brandField = new JTextField(5);
        filterPanel.add(brandField);
        filterPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        JLabel productMatièreLabel = new JLabel("Matière");
        filterPanel.add(productMatièreLabel);

        String[] matière = {"All", "acier", "silver"};
        JComboBox<String> productMatièreComboBox = new JComboBox<>(matière);
        filterPanel.add(productMatièreComboBox);
        filterPanel.add(Box.createRigidArea(new Dimension(0, 10)));
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
    /*
            @Override
            public void actionPerformed(ActionEvent e) {
                FilteredDashboard.clear();
            mainController.fetchAllFilteredProducts(FilteredDashboard,nameField.getText(),descField.getText(),
                   brandField.getText(),(String) productTypeComboBox.getSelectedItem() ,minPriceField.getText(),
                    maxPriceField.getText(),(String) productMatièreComboBox.getSelectedItem());
                refreshDashboard();
            }
        });
        filterPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        filterPanel.add(filterButton);
        JPanel emptyPanel = new JPanel();
        emptyPanel.setPreferredSize(new Dimension(200, 200));
        emptyPanel.setBackground(Color.WHITE);
        filterPanel.add(emptyPanel);
        return filterPanel;
    }

    private void refreshDashboard() {
        JPanel dashboardPanel = (JPanel) getComponent(2);
        dashboardPanel.removeAll();
        UtilDisplayingDashboards.loadDashbaordImages(mainController, FilteredDashboard, dashboardPanel);
        dashboardPanel.revalidate();
        dashboardPanel.repaint();
    }


*/