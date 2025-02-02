package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

import controller.MainController;

public class LoginView extends JPanel {

	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton loginButton;
	private JButton signUpButton;
	private MainController mainController;

	public LoginView(MainController mainController) {
		this.mainController = mainController;
		setPreferredSize(new Dimension(400, 300));
		this.setLayout(null);

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

		loginButton.addActionListener(e -> {
			if (usernameField.getText().isEmpty() || new String(passwordField.getPassword()).isEmpty()) {
				JOptionPane.showMessageDialog(this, "Please enter both username and password.", "Login Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			mainController.authenticateUser(usernameField.getText(), new String(passwordField.getPassword()));
		});

		signUpButton = new JButton("Sign Up");
		signUpButton.setBounds(230, 150, 100, 25);
		this.add(signUpButton);

		signUpButton.addActionListener(e -> mainController.showSignUpView());
	}
}
