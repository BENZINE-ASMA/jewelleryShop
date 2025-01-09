package view.admin;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
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
		
		tableModel = new DefaultTableModel(new String[] {"ID", "FirstName","LastName", "Email", "Role","Password"},0);
		clientTable = new JTable(tableModel);
		this.loadClientData();
		add(new JScrollPane(clientTable), BorderLayout.CENTER);

		DashboardHeaderAdmin header = new DashboardHeaderAdmin(adminController);
		header.setBorder(new EmptyBorder(0, 0, 5, 0));
		add(header, BorderLayout.NORTH);
		JPanel buttonPanel = new JPanel();
		JButton addClient = new JButton("Add");
		addClient.addActionListener(e->{
			 this.openEditClientView(null);
	        loadClientData(); 
		});	
		JButton editClient = new JButton("Edit");
		editClient.addActionListener(e->{
			int selectedRow = clientTable.getSelectedRow();
			 if (selectedRow != -1) {
	                Client selectedClient = getClientFromTable(selectedRow);
	                openEditClientView(selectedClient); 
	            } else {
	                JOptionPane.showMessageDialog(this, "Please select a client to edit.");
	            }
		});	
		JButton deleteClient = new JButton("Delete");
		deleteClient.addActionListener(e->{
			this.deteletclient();
			
		});	
		buttonPanel.add(addClient);
		buttonPanel.add(editClient);
		buttonPanel.add(deleteClient);
		
		this.add(buttonPanel,BorderLayout.SOUTH);
/*
		JButton closeButton = new JButton("close");
		closeButton.addActionListener(e -> {
			adminController.ShowMaindashboardAdminView();
			this.setVisible(false);
		});
		JPanel closePanel = new JPanel(new BorderLayout());
		closePanel.add(closeButton, BorderLayout.WEST);
		closePanel.setBorder(new EmptyBorder(1, 0, 5, 1));
		this.add(closePanel, BorderLayout.NORTH);
		*/

	}
	
	private void loadClientData() {
		
		this.tableModel.setRowCount(0);
		for(Client client: this.adminController.fetchAllClients()) {
			tableModel.addRow(new Object[]{client.getId(), client.getFirstName(), client.getLastName(),client.getEmail(),client.getRole(),client.getPassword()
		});
	}
}
	
	private Client getClientFromTable(int rowIndex) {
        Long id  = (Long) tableModel.getValueAt(rowIndex, 0);
        String firstName = (String) tableModel.getValueAt(rowIndex, 1);
        String lastName = (String) tableModel.getValueAt(rowIndex, 2);
        String email = (String) tableModel.getValueAt(rowIndex, 3);
        String role = (String) tableModel.getValueAt(rowIndex, 4);
        String password = (String) tableModel.getValueAt(rowIndex, 5);
        
        return new Client(id, firstName, lastName, email, role,password);
    }
	 private void openEditClientView(Client client) {
		
		 ClientDialogView dialog = new ClientDialogView(client, adminController);
		 	dialog.setSize(400, 300);
		 	dialog.setLocationRelativeTo(null); 
		 	dialog.setModal(true); 
		 	dialog.setVisible(true); 
	      
	    }
	 private void deteletclient() {
		 int selectedRow = clientTable.getSelectedRow();
		 if (selectedRow >= 0) {
			 Long id = (Long) tableModel.getValueAt(selectedRow,0);
			 adminController.deleteClient(id);
	            loadClientData();
		 }else {
	            JOptionPane.showMessageDialog(this, "Please select a client to delete.");
	        }
		
	 }
}
