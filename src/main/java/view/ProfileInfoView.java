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
import model.Client;

public class ProfileInfoView extends JPanel {
	private JTextField firstNameField;
 	private JTextField lastNameField;
 	private JTextField emailField;
    private JPasswordField passwordField;
    private JButton editProfileButton;
    private JButton deleteAccButton;
    private DBManager dbManager;
    
    public ProfileInfoView(MainView mainView) {
    	System.out.println("teeest"+ mainView.getLoggedInClient().toString());
    	this.dbManager = new DBManager();
    	Client loggedInClient = mainView.getLoggedInClient();
        this.setLayout(null);
        
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(50, 30, 80, 25);
        this.add(firstNameLabel);
        

        firstNameField = new JTextField(20);
        firstNameField.setBounds(150, 30, 150, 25);
        firstNameField.setText(loggedInClient.getFirstName());
       // firstNameField.setEnabled(false);
        this.add(firstNameField);
        
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(50, 70, 80, 25);
        this.add(lastNameLabel);

        lastNameField = new JTextField(20);
        lastNameField.setBounds(150, 70, 150, 25);
        firstNameField.setText(loggedInClient.getLastName());
       // lastNameField.setEnabled(false);
        this.add(lastNameField);
        
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 110, 80, 25);
        this.add(emailLabel);

        emailField = new JTextField(20);
        emailField.setBounds(150, 110, 150, 25);
       
        firstNameField.setText(loggedInClient.getEmail());
       // emailField.setEnabled(false);
        this.add(emailField);
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 150, 80, 25);
        this.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(150, 150, 150, 25);
      //  passwordField.setEnabled(false);
        this.add(passwordField);

        editProfileButton = new JButton("Edit Profile");
        editProfileButton.setBounds(110, 195, 100, 25);
        this.add(editProfileButton);
        
        editProfileButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String password = new String(passwordField.getPassword()) ;
				if(!firstNameField.getText().isEmpty() &&! lastNameField.getText().isEmpty() && !emailField.getText().isEmpty() && !password.isEmpty() ) {
					Client newClient = new Client(firstNameField.getText(),lastNameField.getText(),emailField.getText(),password);
					
					if (ProfileInfoView.this.dbManager.addUser(newClient)) {
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
