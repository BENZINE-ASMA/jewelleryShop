package view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.AdminController;
import controller.DBManager;
import controller.MainController;
import model.Client;
import view.admin.ClientDashbaord;
import view.admin.MainDashboardAdminView;
import view.admin.ProductDashboard;

import java.awt.CardLayout;

public class MainView extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private static Client loggedInClient;
    private MainController mainController;
    private MainController admiNController;

    public MainView() {
   

        setTitle("Vente de bijoux");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        DBManager dbManager = new DBManager(); 
        mainController = new MainController(this, dbManager); 
      
        LoginView loginView = new LoginView(mainController);
        mainPanel.add(loginView, "Login");



        this.mainController.initializeDatabase();
        
    
        add(mainPanel);

      
        cardLayout.show(mainPanel, "Login");

        setVisible(true);
    }


  
    public CardLayout getCardLayout() {
		return cardLayout;
	}


	public void setCardLayout(CardLayout cardLayout) {
		this.cardLayout = cardLayout;
	}


	public JPanel getMainPanel() {
		return mainPanel;
	}


	public void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}


	public void showPanel(String panelName) {
		if(panelName=="MainDashboard"||panelName=="cart" || panelName== "ringsDashbaord" || panelName=="necklacesDashbaord" ||
				panelName=="mainDashboardAdminView" ||panelName=="client") {
			setSize(1000,700);
			cardLayout.show(mainPanel, panelName);
		}else {
			setSize(400, 300);
			cardLayout.show(mainPanel, panelName);
			
		}
    }

    public static void main(String[] args) {
        new MainView();
    }


	public Client getLoggedInClient() {
		return loggedInClient;
	}


	public void setLoggedInClient(Client loggedInClient) {
		this.loggedInClient = loggedInClient;
	}
	
    
    public void loadMaindashboardView() {
    	MainDashboardView mainDashboardView = new MainDashboardView(mainController);
        mainPanel.add(mainDashboardView, "MainDashboard");
    }
    public void loadProfileInfoView() {
    	
        ProfileInfoView profileInfoView = new ProfileInfoView(mainController);
        mainPanel.add(profileInfoView, "ProfileInfo");
    }
    public void showAuthenticationError() {
        JOptionPane.showMessageDialog(this, "Failed to authenticate", "Authentication Error", JOptionPane.ERROR_MESSAGE);
    }
    public void showEditPofileSucess() {
        JOptionPane.showMessageDialog(this, "Profile updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    public void showEditProfileError() {
        JOptionPane.showMessageDialog(this, "Failed to update Profile", " Error updating profile", JOptionPane.ERROR_MESSAGE);
    }
    public void loadSignUpView() {
    	SignUpView SignUpView = new SignUpView(mainController);
        mainPanel.add(SignUpView, "Signup");
		
	}
    public void loadCartView() {
    	CartView cartView = new CartView(mainController);
        mainPanel.add(cartView, "cart");
		
	}
    
    public void loadRingsDashboardView() {
    	RingsDashboardView ringsDashboardView = new RingsDashboardView(mainController);
    	mainPanel.add(ringsDashboardView,"ringsDashbaord");
    }
    
    public void loadNecklacesDashboardView() {
    	NecklacesDashboardView necklacesDashboardView = new NecklacesDashboardView(mainController);
    	mainPanel.add(necklacesDashboardView,"necklacesDashbaord");
    }
    public void loadMainDashboardAdminView() {
    	MainDashboardAdminView mainDashboardAdminView = new MainDashboardAdminView(mainController);
    	mainPanel.add(mainDashboardAdminView,"mainDashboardAdminView");
    }
    
    public void loadClientDashbaordView() {
    	ClientDashbaord mainDashboardAdminView = new ClientDashbaord(new AdminController(this.mainController.getDbManager()));
    	mainPanel.add(mainDashboardAdminView,"client");
    }
    
    public void loadProductDashbaordView() {
    	ProductDashboard mainDashboardAdminView = new ProductDashboard(new AdminController(this.mainController.getDbManager()));
    	mainPanel.add(mainDashboardAdminView,"product");
    }

    public void loadLoginView(boolean displayOptionPanes ,String toDisplay) {
    	if(displayOptionPanes) {
    		LoginView loginView = new LoginView(mainController);
        	mainPanel.add(loginView,"loginView");
            JOptionPane.showMessageDialog(this, toDisplay, "Success", JOptionPane.INFORMATION_MESSAGE);

        	
    	}
    	LoginView loginView = new LoginView(mainController);
    	mainPanel.add(loginView,"loginView");
    }
	
}
