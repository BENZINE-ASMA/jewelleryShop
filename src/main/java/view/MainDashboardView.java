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
    private MainController mainController;
    public MainDashboardView(MainController mainController) {
    	this.mainController = mainController;
        setLayout(new BorderLayout());

        JPanel navBar = new JPanel();
        navBar.setLayout(new FlowLayout(FlowLayout.RIGHT));

        
        profileIcon = new JLabel();
        loadProfileIcon("ressources/profileIcon.png");

       navBar.setBorder(BorderFactory.createLineBorder(Color.black, 2, false));
        //profileIcon.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
        
      
        profileIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	mainController.showProfileInfoView();
            }
        });

      
        navBar.add(profileIcon);
        add(navBar, BorderLayout.NORTH);
    }

    private void loadProfileIcon(String path) {
        try {
           
            InputStream input = getClass().getClassLoader().getResourceAsStream(path);
            if (input != null) {
                Image image = ImageIO.read(input);
                Image scaledImage = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH); 
                profileIcon.setIcon(new ImageIcon(scaledImage));
            } else {
                System.out.println("Resource "+path +"   not found in the classpath!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
