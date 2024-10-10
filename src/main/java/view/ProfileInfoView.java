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

public class ProfileInfoView extends JPanel {
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField emailField;
	private JTextField passwordField;
	private JButton editProfileButton;
	private JButton deleteAccButton;
	private MainController mainController;

	public ProfileInfoView(MainController mainController) {
		this.mainController = mainController;

		Client loggedInClient = mainController.getLoggedInClient();
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
		lastNameField.setText(loggedInClient.getLastName());
		// lastNameField.setEnabled(false);
		this.add(lastNameField);

		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setBounds(50, 110, 80, 25);
		this.add(emailLabel);

		emailField = new JTextField(20);
		emailField.setBounds(150, 110, 150, 25);

		emailField.setText(loggedInClient.getEmail());
		// emailField.setEnabled(false);
		this.add(emailField);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(50, 150, 80, 25);
		this.add(passwordLabel);

		passwordField = new JPasswordField(20);
		passwordField.setBounds(150, 150, 150, 25);
		passwordField.setText(loggedInClient.getPassword());
		// passwordField.setEnabled(false);
		this.add(passwordField);

		editProfileButton = new JButton("Edit Profile");
		editProfileButton.setBounds(110, 195, 100, 25);
		this.add(editProfileButton);

		editProfileButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Long id = ProfileInfoView.this.mainController.getLoggedInClient().getId();
				Client updatedClient = new Client(id, ProfileInfoView.this.getFirstNameField().getText(),
						ProfileInfoView.this.getLastNameField().getText(),
						ProfileInfoView.this.getEmailField().getText(),
						ProfileInfoView.this.getPasswordField().getText());
				
				ProfileInfoView.this.mainController.getDbManager().UpdateUser(updatedClient);
				

			}
			

		});

	}

	public JTextField getFirstNameField() {
		return firstNameField;
	}

	public void setFirstNameField(JTextField firstNameField) {
		this.firstNameField = firstNameField;
	}

	public JTextField getLastNameField() {
		return lastNameField;
	}

	public void setLastNameField(JTextField lastNameField) {
		this.lastNameField = lastNameField;
	}

	public JTextField getEmailField() {
		return emailField;
	}

	public void setEmailField(JTextField emailField) {
		this.emailField = emailField;
	}

	public JTextField getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(JTextField passwordField) {
		this.passwordField = passwordField;
	}

}
