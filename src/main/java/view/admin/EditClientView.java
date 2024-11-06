package view.admin;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.AdminController;
import model.Client;


public class EditClientView extends JPanel {
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField emailField;
	private JComboBox<String> roleMultiselect;
	
	public EditClientView(Client client) {
		setPreferredSize(new Dimension(600, 400));
		this.setLayout(null);
		
		JLabel firstNameLabel = new JLabel("First Name:");
		firstNameLabel.setBounds(50, 30, 80, 25);
		this.add(firstNameLabel);
		
		firstNameField = new JTextField(20);
		firstNameField.setBounds(150, 30, 150, 25);
		firstNameField.setText(client.getFirstName());

		this.add(firstNameField);
		
		JLabel lastNameLabel = new JLabel("Last Name:");
		lastNameLabel.setBounds(50, 70, 80, 25);
		this.add(lastNameLabel);

		lastNameField = new JTextField(20);
		lastNameField.setBounds(150, 70, 150, 25);
		lastNameField.setText(client.getLastName());
		this.add(lastNameField);

		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setBounds(50, 110, 80, 25);
		this.add(emailLabel);

		emailField = new JTextField(20);
		emailField.setBounds(150, 110, 160, 25);

		emailField.setText(client.getEmail());
		this.add(emailField);
		
		JLabel roleLabel = new JLabel("Role:");
		roleLabel.setBounds(50, 150, 80, 25);
		this.add(roleLabel);
		
		
		roleMultiselect = new JComboBox<>(new String[]{"CLIENT", "ADMIN"});
		roleMultiselect.setBounds(150, 150, 150, 25);
		roleMultiselect.setSelectedItem(client != null ? client.getRole() : "CLIENT");
        add(roleMultiselect);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(150, 200, 80, 25);
        add(saveButton);
        /*
        saveButton.addActionListener(e -> saveClient());
        
		*/
	}
}
