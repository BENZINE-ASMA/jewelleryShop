package view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.DBManager;

import java.awt.CardLayout;

public class MainView extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
   

    public MainView() {
    	
    	
        setTitle("Vente de bijoux");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

      
        LoginView loginView = new LoginView(this);
        mainPanel.add(loginView, "Login");
        
        MainDashboardView mainDashboardView = new MainDashboardView(this);
        mainPanel.add(mainDashboardView, "mainDashboardView");

        SignUpView signUpView = new SignUpView(this);
        mainPanel.add(signUpView, "Signup");
        
    
        add(mainPanel);

      
        cardLayout.show(mainPanel, "Login");

        setVisible(true);
    }

  
    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    public static void main(String[] args) {
        new MainView();
    }
}
