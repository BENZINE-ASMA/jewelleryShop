package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Pattern;

import controller.MainController;
import model.Client;

public class SignUpView extends JPanel {
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField emailField;
	private JPasswordField passwordField;
	private JLabel emailRequirementsLabel;
	private JLabel passwordRequirementsLabel;
	private JButton submitButton;
	private JButton closeButton;
	private MainController mainController;

	public SignUpView(MainController mainController) {
		this.mainController = mainController;
		this.setLayout(null);

		closeButton = new JButton("Close");
		closeButton.setBounds(5, 2, 70, 17);
		this.add(closeButton);

		closeButton.addActionListener(e -> mainController.showLoginView(false, ""));

		JLabel firstNameLabel = new JLabel("First Name:");
		firstNameLabel.setBounds(50, 30, 80, 25);
		this.add(firstNameLabel);

		firstNameField = new JTextField(20);
		firstNameField.setBounds(150, 30, 200, 25);
		this.add(firstNameField);

		JLabel lastNameLabel = new JLabel("Last Name:");
		lastNameLabel.setBounds(50, 70, 80, 25);
		this.add(lastNameLabel);

		lastNameField = new JTextField(20);
		lastNameField.setBounds(150, 70, 200, 25);
		this.add(lastNameField);

		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setBounds(50, 110, 80, 25);
		this.add(emailLabel);

		emailField = new JTextField(20);
		emailField.setBounds(150, 110, 200, 25);
		this.add(emailField);

		emailRequirementsLabel = new JLabel("<html><i>Must be a valid email (e.g., example@domain.com)</i></html>");
		emailRequirementsLabel.setBounds(150, 140, 250, 30);
		emailRequirementsLabel.setForeground(Color.RED);
		emailRequirementsLabel.setVisible(false);
		this.add(emailRequirementsLabel);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(50, 180, 80, 25);
		this.add(passwordLabel);

		passwordField = new JPasswordField(20);
		passwordField.setBounds(150, 180, 200, 25);
		this.add(passwordField);

		passwordRequirementsLabel = new JLabel("<html><i>At least 8 characters, with letters, numbers, and a special character</i></html>");
		passwordRequirementsLabel.setBounds(150, 210, 250, 40);
		passwordRequirementsLabel.setForeground(Color.RED);
		passwordRequirementsLabel.setVisible(false);
		this.add(passwordRequirementsLabel);

		submitButton = new JButton("Submit");
		submitButton.setBounds(150, 260, 100, 25);
		submitButton.setEnabled(false);
		this.add(submitButton);

		submitButton.addActionListener(e -> {
			String firstName = firstNameField.getText();
			String lastName = lastNameField.getText();
			String email = emailField.getText();
			String password = new String(passwordField.getPassword());

			if (!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
				Client newClient = new Client(firstName, lastName, email, password);

				if (mainController.getDbManager().addUser(newClient)) {
					JOptionPane.showMessageDialog(this, "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
					mainController.showLoginView(false, "");
				} else {
					JOptionPane.showMessageDialog(this, "Failed to create account. Email may already be in use.", "Registration Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Registration Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		addValidationListeners();
	}

	private void addValidationListeners() {
		emailField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String email = emailField.getText();
				if (isValidEmail(email)) {
					emailRequirementsLabel.setVisible(false);
				} else {
					emailRequirementsLabel.setVisible(true);
				}
				toggleSubmitButton();
			}
		});

		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String password = new String(passwordField.getPassword());
				if (isValidPassword(password)) {
					passwordRequirementsLabel.setVisible(false);
				} else {
					passwordRequirementsLabel.setVisible(true);
				}
				toggleSubmitButton();
			}
		});
	}

	private boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		Pattern pattern = Pattern.compile(emailRegex);
		return pattern.matcher(email).matches();
	}

	private boolean isValidPassword(String password) {

		String passwordRegex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$";
		Pattern pattern = Pattern.compile(passwordRegex);
		return pattern.matcher(password).matches();
	}

	private void toggleSubmitButton() {
		String email = emailField.getText();
		String password = new String(passwordField.getPassword());
		boolean validEmail = isValidEmail(email);
		boolean validPassword = isValidPassword(password);
		submitButton.setEnabled(validEmail && validPassword);
	}
}
