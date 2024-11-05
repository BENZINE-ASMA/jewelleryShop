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
import controller.MainController;
import model.Client;

public class SignUpView extends JPanel {
	 	private JTextField firstNameField;
	 	private JTextField lastNameField;
	 	private JTextField emailField;
	    private JPasswordField passwordField;
	    private JButton submitButton;
	    private MainController mainController;
	    private JButton closeButton;
	    
	    public SignUpView(MainController mainController) {
	    	this.mainController = mainController;
	    	
	        this.setLayout(null);
	        // setting the layout to null means that i will position manually all the components
	        
	        closeButton = new JButton("close");
			closeButton.setBounds(5, 2, 70, 17);
			this.add(closeButton);
			
			closeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mainController.showLoginView(false,"");
					
				}
			});

	        JLabel firstNameLabel = new JLabel("First Name:");
	        firstNameLabel.setBounds(50, 30, 80, 25);
	        this.add(firstNameLabel);

	        firstNameField = new JTextField(20);
	        firstNameField.setBounds(150, 30, 150, 25);
	        this.add(firstNameField);
	        
	        JLabel lastNameLabel = new JLabel("Last Name:");
	        lastNameLabel.setBounds(50, 70, 80, 25);
	        this.add(lastNameLabel);

	        lastNameField = new JTextField(20);
	        lastNameField.setBounds(150, 70, 150, 25);
	        this.add(lastNameField);
	        
	        JLabel emailLabel = new JLabel("Email:");
	        emailLabel.setBounds(50, 110, 80, 25);
	        this.add(emailLabel);

	        emailField = new JTextField(20);
	        emailField.setBounds(150, 110, 150, 25);
	        this.add(emailField);
	        
	        JLabel passwordLabel = new JLabel("Password:");
	        passwordLabel.setBounds(50, 150, 80, 25);
	        this.add(passwordLabel);

	        passwordField = new JPasswordField(20);
	        passwordField.setBounds(150, 150, 150, 25);
	        this.add(passwordField);

	        submitButton = new JButton("Submit");
	        submitButton.setBounds(110, 195, 100, 25);
	        this.add(submitButton);
	        
	        submitButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String password = new String(passwordField.getPassword()) ;
					if(!firstNameField.getText().isEmpty() &&! lastNameField.getText().isEmpty() && !emailField.getText().isEmpty() && !password.isEmpty() ) {
						Client newClient = new Client(firstNameField.getText(),lastNameField.getText(),emailField.getText(),password);
						
						if (mainController.getDbManager().addUser(newClient)) {
	                        System.out.println("User added successfully!");
	                    } else {
	                        System.out.println("Failed to add the user.");
	                    }
					}else {
						JOptionPane.showMessageDialog(null, "Please fill in all fields. None of the fields can be left empty", "Registration Error", JOptionPane.ERROR_MESSAGE);
	                }
					
					
				}
	        	
	        });

	    }
}
