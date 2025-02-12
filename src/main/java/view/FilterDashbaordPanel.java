package view;

import controller.AdminController;
import controller.MainController;
import model.Bijoux;
import shared.UtilDisplayingDashboards;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FilterDashbaordPanel extends JPanel{
    private String parent;

    public FilterDashbaordPanel(ArrayList<Bijoux>FilteredDashboard, int panelHeight, MainController mainController, JPanel dashboard, String p){

        this.parent=p;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(200, panelHeight));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setBackground(Color.white);

        this.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel nameLabel = new JLabel("Name of product");
        this.add(nameLabel);
        JTextField nameField = new JTextField(5);
        nameField.setPreferredSize(new Dimension(5,10));
        this.add(nameField);

        this.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel descLabel = new JLabel("Description");
        this.add(descLabel);
        JTextField descField = new JTextField(5);
        this.add(descField);

        this.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel productTypeLabel = new JLabel("Type de produit");

        String[] productTypes = {"All", "Ring", "Necklace"};
        JComboBox<String> productTypeComboBox = new JComboBox<>(productTypes);

        if(parent.equals("RING")){
            productTypeComboBox.setSelectedItem("Ring");
        } else if (parent.equals("NECKLACE")) {
            productTypeComboBox.setSelectedItem("Necklace");
        }else{
            this.add(productTypeLabel);
            this.add(productTypeComboBox);

        }

        this.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel brandLabel = new JLabel("Brand");
        this.add(brandLabel);
        JTextField brandField = new JTextField(5);
        this.add(brandField);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        JLabel productMatièreLabel = new JLabel("Matière");
        this.add(productMatièreLabel);

        String[] matière = mainController.getDistinctMaterials().toArray(new String[0]);

        JComboBox<String> productMatièreComboBox = new JComboBox<>(matière);
        this.add(productMatièreComboBox);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        JLabel priceLabel = new JLabel("Prix");
        this.add(priceLabel);


        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new FlowLayout());
        pricePanel.add(new JLabel("de"));
        JTextField minPriceField = new JTextField(5);
        pricePanel.add(minPriceField);
        pricePanel.add(new JLabel("à"));
        JTextField maxPriceField = new JTextField(5);
        pricePanel.add(maxPriceField);
        this.add(pricePanel);


        JButton filterButton = new JButton("Filtrer");
        filterButton.addActionListener(new ActionListener() {
            /*
            'Diamond Ring' and brand = 'Luxury' and type ='Ring' and description='A beautiful diamond ring with a sleek design.'
and price ='299.99' and material='Gold';
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                FilteredDashboard.clear();
                mainController.fetchAllFilteredProducts(FilteredDashboard,nameField.getText(),descField.getText(),
                        brandField.getText(),(String) productTypeComboBox.getSelectedItem() ,minPriceField.getText(),
                        maxPriceField.getText(),(String) productMatièreComboBox.getSelectedItem());
               refreshDashboard(FilteredDashboard,mainController,dashboard);
            }
        });
        this.add(Box.createRigidArea(new Dimension(0, 10)));

        this.add(filterButton);
        JPanel emptyPanel = new JPanel();
        emptyPanel.setPreferredSize(new Dimension(200, 200));
        emptyPanel.setBackground(Color.WHITE);
        this.add(emptyPanel);
        setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, Color.LIGHT_GRAY));

        nameLabel.setHorizontalAlignment(JLabel.LEFT);
        descLabel.setHorizontalAlignment(JLabel.LEFT);
        productTypeLabel.setHorizontalAlignment(JLabel.LEFT);
        brandLabel.setHorizontalAlignment(JLabel.LEFT);
        productMatièreLabel.setHorizontalAlignment(JLabel.LEFT);
        priceLabel.setHorizontalAlignment(JLabel.LEFT);

    }

    private void refreshDashboard(ArrayList<Bijoux>FilteredDashboard,MainController mainController,JPanel dashboard) {
        JPanel dashboardPanel = (JPanel) dashboard.getComponent(2);
        dashboardPanel.removeAll();
        UtilDisplayingDashboards.loadDashbaordImages(mainController, FilteredDashboard, dashboardPanel,"client");
        dashboardPanel.revalidate();
        dashboardPanel.repaint();
    }
}

