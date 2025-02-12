package view.admin;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.AdminController;
import model.Client;

public class ClientDialogView extends JDialog {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField passwordField;
    private JComboBox<String> roleMultiselect;
    private Client client;
    private AdminController adminController;

    public ClientDialogView(Client client, AdminController adminController) {
        System.out.println("ssss" + client);
        this.client = client;
        this.adminController = adminController;
        this.setLayout(null);

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(50, 30, 80, 25);
        this.add(firstNameLabel);

        firstNameField = new JTextField(20);
        firstNameField.setBounds(150, 30, 150, 25);
        firstNameField.setText(client!=null? client.getFirstName():"");
        this.add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(50, 70, 80, 25);
        this.add(lastNameLabel);

        lastNameField = new JTextField(20);
        lastNameField.setBounds(150, 70, 150, 25);
        lastNameField.setText(client!=null? client.getLastName():"");
        this.add(lastNameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 110, 80, 25);
        this.add(emailLabel);

        emailField = new JTextField(20);
        emailField.setBounds(150, 110, 150, 25);
        emailField.setText(client!=null? client.getEmail():"");
        this.add(emailField);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(50, 140, 80, 25);
        this.add(roleLabel);

        roleMultiselect = new JComboBox<>(new String[]{"CLIENT", "ADMIN"});
        roleMultiselect.setBounds(150, 140, 150, 25);  
        roleMultiselect.setSelectedItem(client != null ? client.getRole() : "CLIENT");
        add(roleMultiselect);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 170, 80, 25);
        this.add(passwordLabel);

        passwordField = new JTextField(20);
        passwordField.setBounds(150, 170, 150, 25);
        passwordField.setText(client!=null? client.getPassword():"");
        add(passwordField);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(80, 230, 80, 25);
        saveButton.addActionListener(e -> saveClient());
        add(saveButton);
    }

    private void saveClient() {
        String firstname = firstNameField.getText();
        String lastname = lastNameField.getText();
        String email = emailField.getText();
        String role = (String) roleMultiselect.getSelectedItem();
        String password = passwordField.getText();

        if (password.trim().isEmpty() && client != null) {
            password = client.getPassword();
        }

        if (client == null) {
            if (password.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Password cannot be empty for new clients!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            adminController.addClient(new Client(firstname, lastname, email, password, role));
        } else {
            client.setFirstName(firstname);
            client.setLastName(lastname);
            client.setEmail(email);
            client.setRole(role);
            client.setPassword(password);
            adminController.updateClient(client);
        }

        this.dispose();
    }

}
