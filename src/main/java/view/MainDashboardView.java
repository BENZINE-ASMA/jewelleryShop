package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.MainController;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainDashboardView extends JPanel {
    private JLabel profileIcon;
    private JLabel cartIcon;
    private MainController mainController;

    public MainDashboardView(MainController mainController) {
        this.mainController = mainController;
        setLayout(new BorderLayout());

        JPanel navBar = new JPanel();
        navBar.setLayout(new FlowLayout(FlowLayout.RIGHT)); 
        navBar.setBackground(Color.WHITE);

        profileIcon = new JLabel();
        loadIcon(profileIcon, "ressources/profileIcon.png", 20, 15); 

        cartIcon = new JLabel();
        loadIcon(cartIcon, "ressources/cartIcon.png", 20, 15);

        navBar.setBorder(BorderFactory.createLineBorder(Color.black, 2, false));

        profileIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainController.showProfileInfoView();
            }
        });
        cartIcon.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		mainController.fetchAllProducts();
        		
        	}
        });

        navBar.add(profileIcon);
        navBar.add(cartIcon);
        add(navBar, BorderLayout.NORTH);
    }

   
    private void loadIcon(JLabel label, String path, int width, int height) {
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream(path);
            if (input != null) {
                Image image = ImageIO.read(input);
                label.setIcon(new ImageIcon(image));
                label.setPreferredSize(new java.awt.Dimension(width, height)); 
            } else {
                System.out.println("Resource " + path + " not found in the classpath!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
