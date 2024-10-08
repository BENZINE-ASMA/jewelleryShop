package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.DBManager;

public class LoginView extends JPanel {
	
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signUpButton;
    private DBManager dbManager;
    

    public LoginView(MainView mainView) {
    	dbManager = new DBManager();
    	
        this.setLayout(null);
        // setting the layout to null means that i will position manually all the components

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 50, 80, 25);
        this.add(userLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(150, 50, 150, 25);
        this.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 100, 80, 25);
        this.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(150, 100, 150, 25);
        this.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(110, 150, 100, 25);
        this.add(loginButton);
        
        loginButton.addActionListener(new ActionListener() {
        	
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		System.out.println(new String(passwordField.getPassword()));
        		Boolean authenticated = dbManager.authenticateUser(usernameField.getText(),new String(passwordField.getPassword()));
        		if(authenticated ) {
        			mainView.showPanel("mainDashboardView");        			
        		}else {
        			JOptionPane.showMessageDialog(null, "Failed to authenticate", "Authentication Error", JOptionPane.ERROR_MESSAGE);
                }
        	}
        });
        
        signUpButton = new JButton("signUp");
        signUpButton.setBounds(230, 150, 100, 25);
        this.add(signUpButton);
        
        signUpButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		mainView.showPanel("Signup");
        	}
        });
    }
}
