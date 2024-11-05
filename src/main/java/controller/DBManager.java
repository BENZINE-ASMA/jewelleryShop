package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;
import model.Bijoux;
import model.Client;
import model.Invoice;
import model.Necklace;
import model.Order;
import model.Ring;

@Getter
@Setter
public class DBManager {
	private static final String url = "jdbc:mysql://localhost:3306/sys";
	private static final String user = "root";
	private static final String password = "7867";

	private Connection connection;

	public DBManager() {
		try {
			connection = DriverManager.getConnection(url, user, password);
			System.out.println("Database connection successful!");
		} catch (SQLException e) {
			System.out.println("Failed to connect to the database.");
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<Bijoux> getAllProducts(ArrayList<Bijoux> results){
		String query="Select * from products";
		Statement stmt;
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				Long id = rs.getLong("id");
				String type = rs.getString("type");
				String name = rs.getString("name");
				String brand = rs.getString("brand");
				String description = rs.getString("description");
				double price = rs.getDouble("price");
				String material = rs.getString("material");
				String imagePath = rs.getString("image_path");
				int stock = rs.getInt("stock");
				switch(type) {
				case "Ring":
					int size = rs.getInt("size");
					
					Ring ring = new Ring(id,name,brand,description,price,material,size,imagePath,stock);
					results.add(ring);
					
					break;
				case "Necklace":
					double length = rs.getDouble("length");
					Necklace necklace = new Necklace(id,name, brand,description,price, material,length, imagePath,stock);
					results.add(necklace);
					break;
				}
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
	return  null;
        
	}

	public Client authenticateUser(String email, String password) {
		String query = "SELECT * FROM client where email =? and password=?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				System.out.println("User authenticated successfully");

				Long id = resultSet.getLong("id");
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				String emailResult = resultSet.getString("email");
				String passwordResult = resultSet.getString("password");

				
				Client client = new Client(id, firstName, lastName, emailResult, passwordResult);
				
				return client;
			} else {
				System.out.println("Invalid email or password.");
				
			}
		} catch (SQLException e) {
			System.out.println("Error during user authentication.");
			e.printStackTrace();
			
		}
		return null;
		
	}

	public boolean addUser(Client c) {
		String query = "INSERT INTO client (firstName, lastName, email, password) VALUES (?, ?, ?, ?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, c.getFirstName());
			preparedStatement.setString(2, c.getLastName());
			preparedStatement.setString(3, c.getEmail());
			preparedStatement.setString(4, c.getPassword());

			int result = preparedStatement.executeUpdate();

			return result > 0;
		} catch (SQLException e) {
			System.out.println("Error inserting new client");
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteUser(Client c) {
		
		String query = "DELETE FROM client WHERE email = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, c.getEmail());

			 int rowsAffected = preparedStatement.executeUpdate();

			return rowsAffected != 0;
		} catch (SQLException e) {
			System.out.println("Error inserting new client");
			e.printStackTrace();
			return false;
		}
	}

	public boolean UpdateUser(Client c) {
		String query = "UPDATE client SET firstName = ?, lastName = ?, password = ? WHERE email = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, c.getFirstName());
			preparedStatement.setString(2, c.getLastName());
			preparedStatement.setString(3, c.getPassword());
			preparedStatement.setString(4, c.getEmail());

			int result = preparedStatement.executeUpdate();
			System.out.println(" updating user successfull " + (result > 0));
			

			return result > 0;
		} catch (SQLException e) {
			System.out.println("Error updating user");
			e.printStackTrace();
			return false;
		}
	}

	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
				System.out.println("Database connection closed.");
			} catch (SQLException e) {
				System.out.println("Failed to close the database connection.");
				e.printStackTrace();
			}
		}
	}
	
	public boolean addOrder(Order order) {
	    String query = "INSERT INTO Orders (client_id, status, total_amount) VALUES (?, ?, ?)";

	    try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
	        preparedStatement.setLong(1, order.getClient().getId()); 
	        preparedStatement.setString(2, order.getStatus().name());
	        preparedStatement.setDouble(3, order.getCartItems().getTotalPrice()); 

	        int result = preparedStatement.executeUpdate();

	    
	        if (result > 0) {
	            ResultSet rs = preparedStatement.getGeneratedKeys();
	            if (rs.next()) {
	                long orderId = rs.getLong(1);
	                order.setOrderId(orderId); 
	            }
	        }

	        return result > 0;
	    } catch (SQLException e) {
	        System.out.println("Error inserting new order");
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean addInvoice(Invoice invoice, Long orderId) {
	    String query = "INSERT INTO Invoices (client_id, order_id, invoice_number, file_path, total_amount, status) VALUES (?, ?, ?, ?, ?, ?)";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setLong(1, invoice.getClientId());
	        preparedStatement.setLong(2, orderId);
	        preparedStatement.setString(3, invoice.getInvoiceNumber());
	        preparedStatement.setString(4, invoice.getFilePath());
	        preparedStatement.setDouble(5, invoice.getTotalAmount());
	        preparedStatement.setString(6, invoice.getStatus());

	        int result = preparedStatement.executeUpdate();

	        return result > 0;
	    } catch (SQLException e) {
	        System.out.println("Error inserting new invoice");
	        e.printStackTrace();
	        return false;
	    }
	}

	
	public String getLastInvoiceNumberOfDB() {
		String query = "SELECT invoice_number from Invoices";
		try(PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()
				){
			
			 if (resultSet.next()) {
		            return resultSet.getString("invoice_number");
		        } else {
		            return null; 
		        }
		    } catch (SQLException e) {
		        System.out.println("Error retrieving last invoice number from the database");
		        e.printStackTrace();
		        return null;
		    }
	}


}
