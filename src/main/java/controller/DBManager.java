package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.Getter;
import lombok.Setter;
import model.Client;

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
	
	public boolean authenticateUser(String email, String password) {
		String query ="SELECT * FROM client where email =? and password=?";
		try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				System.out.println("User authenticated successfully");
				return true;
			}else {
				System.out.println("Invalid email or password.");
                return false;
			}
		}catch (SQLException e) {
            System.out.println("Error during user authentication.");
            e.printStackTrace();
            return false;
        }
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
            System.out.println("Error inserting new user into the database.");
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
	
	
}
