package view.admin;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.AdminController;
import model.Client;

public class ClientDashbaord extends JPanel{
	private JTable clientTable;
	private DefaultTableModel tableModel;
	private AdminController adminController;
	
	public ClientDashbaord(AdminController adminController) {
		this.adminController =adminController;
		setLayout(new BorderLayout());
		
		tableModel = new DefaultTableModel(new String[] {"ID", "FirstName","LastName", "Email", "Role"},0);
		clientTable = new JTable(tableModel);
		this.loadClientData();
		add(new JScrollPane(clientTable), BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		JButton addClient = new JButton("Add");
		addClient.addActionListener(e->{
			int selectedRow = clientTable.getSelectedRow();
			 if (selectedRow != -1) {
	                Client selectedClient = getClientFromTable(selectedRow);
	                openEditClientView(selectedClient); // Pass selectedClient to AddClientView for editing
	            } else {
	                JOptionPane.showMessageDialog(this, "Please select a client to edit.");
	            }
			
			
		});	
		JButton editClient = new JButton("Edit");
		addClient.addActionListener(e->{
			
		});	
		JButton deleteClient = new JButton("Delete");
		addClient.addActionListener(e->{
			
		});	
		buttonPanel.add(addClient);
		buttonPanel.add(editClient);
		buttonPanel.add(deleteClient);
		
		this.add(buttonPanel,BorderLayout.SOUTH);
	}
	
	private void loadClientData() {
		this.tableModel.setRowCount(0);
		for(Client client: this.adminController.fetchAllClients()) {
			tableModel.addRow(new Object[]{client.getId(), client.getFirstName(), client.getLastName(),client.getEmail(),client.getRole()
		});
	}
}
	
	private Client getClientFromTable(int rowIndex) {
        Long id  = (Long) tableModel.getValueAt(rowIndex, 0);
        String firstName = (String) tableModel.getValueAt(rowIndex, 1);
        String lastName = (String) tableModel.getValueAt(rowIndex, 2);
        String email = (String) tableModel.getValueAt(rowIndex, 3);
        String role = (String) tableModel.getValueAt(rowIndex, 4);
        
        return new Client(id, firstName, lastName, email, role);
    }
	 private void openEditClientView(Client client) {
		 EditClientView EditClientView = new EditClientView(client); // Pass client instance to constructor
		 
	        JDialog dialog = new JDialog();
	        dialog.setTitle("Client Details");
	        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	        dialog.add(EditClientView);
	        dialog.pack();
	        dialog.setLocationRelativeTo(null);
	        dialog.setVisible(true);
	    }
}
