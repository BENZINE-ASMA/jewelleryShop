package view.admin;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.AdminController;

public class ClientDashbaord extends JPanel{
	private JTable clientTable;
	private DefaultTableModel tableModel;
	private AdminController adminController;
	
	public ClientDashbaord(AdminController adminController) {
		this.adminController =adminController;
		setLayout(new BorderLayout());
		
		tableModel = new DefaultTableModel(new String[] {"ID", "Name", "Email", "Role"},0);
		clientTable = new JTable(tableModel);
		
		
	}
}
