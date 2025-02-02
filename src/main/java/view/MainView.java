package view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.AdminController;
import controller.DBManager;
import controller.MainController;
import model.Client;
import model.Order;
import view.admin.*;

import java.awt.*;

public class MainView extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private static Client loggedInClient;
    private MainController mainController;



    private AdminController adminController;
    public MainView() {


        setTitle("Vente de bijoux");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        DBManager dbManager = new DBManager();
        mainController = new MainController(this, dbManager);
        adminController= new AdminController(dbManager,this);

        this.loadMaindashboardView();
        this.mainController.initializeDatabase();


        add(mainPanel);

        showPanel("MainDashboard");

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
        if (panelName.equals("loginView") || panelName.equals("ProfileInfo") ) {
            setPreferredSize(new Dimension(400, 300));
        }else if(panelName.equals("Signup")){
            setPreferredSize(new Dimension(430, 350));
        }else {
            this.setPreferredSize(new Dimension(1000, 700));
    }
    pack();
    cardLayout.show(mainPanel, panelName);
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
        if(loggedInClient !=null){
            ProfileInfoView profileInfoView = new ProfileInfoView(mainController);
            mainPanel.add(profileInfoView, "ProfileInfo");
            this.showPanel("ProfileInfo");
        }else {
            LoginView loginView = new LoginView(mainController);
            mainPanel.add(loginView, "loginView");
            this.showPanel("loginView");

        }


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
    	MainDashboardAdminView mainDashboardAdminView = new MainDashboardAdminView(mainController, adminController);
    	mainPanel.add(mainDashboardAdminView,"mainDashboardAdminView");
        System.out.println("this is the admin interface showing");
    }
    public void loadInvoiceDashbaordView(){
        InvoiceDashbaord invoiceDashbaord = new InvoiceDashbaord(adminController);
        mainPanel.add(invoiceDashbaord,"InvoiceDashbaord");
    }
    public AdminController getAdminController() {
        return this.adminController;
    }
    public void loadClientsDashbaordView(){
        ClientDashbaord clientDashbaord = new ClientDashbaord(adminController);
        mainPanel.add(clientDashbaord,"ClientDashbaord");
    }
    public void loadProductsDashbaordView(){
        ProductDashboard productDashboard = new ProductDashboard(adminController);
        mainPanel.add(productDashboard,"ProductDashbaord");
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
