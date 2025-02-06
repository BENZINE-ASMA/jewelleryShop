package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import model.*;
import shared.UtilDisplayingDashboards;
import view.MainDashboardView;

@Getter
@Setter
public class DBManager {
	private static final String url = "jdbc:mysql://localhost:3306/sys";
	private static final String user = "root";
	private static final String password = "7867";

	private Connection connection;

	public DBManager() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL driver
			connect(); // Call the connect method
		} catch (ClassNotFoundException e) {
			System.out.println("MySQL JDBC Driver not found!");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Failed to connect to the database.");
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public void connect() throws SQLException {
		if (connection == null || connection.isClosed()) {
			connection = DriverManager.getConnection(url, user, password);
			System.out.println("Database connected!");
		}
	}

	public void executeSQLScript(String scriptFile) {
		InputStream inputStream = null;
		BufferedReader reader = null;
		Statement statement = null;

		try {

			inputStream = DBManager.class.getClassLoader().getResourceAsStream(scriptFile);
			if (inputStream == null) {
				System.err.println("SQL script file not found: " + scriptFile);
				return;
			}
			reader = new BufferedReader(new InputStreamReader(inputStream));
			StringBuilder sqlScript = new StringBuilder();
			String line;

			while ((line = reader.readLine()) != null) {
				if (line.trim().startsWith("--") || line.trim().isEmpty()) {
					continue;
				}
				sqlScript.append(line).append("\n");
			}

			statement = connection.createStatement();
			String[] sqlStatements = sqlScript.toString().split(";");
			for (String sql : sqlStatements) {
				if (!sql.trim().isEmpty()) {
					statement.executeUpdate(sql.trim());
				}
			}

			System.out.println("SQL script executed successfully.");
		} catch (IOException | SQLException e) {
			System.err.println("Error executing SQL script: " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException | SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void close() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
			System.out.println("Database connection closed.");
		}
	}


	public ArrayList<Bijoux> getAllFilteredProducts(ArrayList<Bijoux> results, String name2, String description2,
													String brand2, String type2, String pricemin2, String pricemax2, String material2) {


		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select * from products where 1=1 ");
		if (!name2.isEmpty()) {
			queryBuilder.append("AND name LIKE ? ");
		}
		if (!description2.isEmpty()) {
			queryBuilder.append("AND description LIKE ? ");
		}
		if (!brand2.isEmpty()) {
			queryBuilder.append("AND brand = ? ");
		}
		if (!type2.isEmpty() && !type2.equals("All")) {
			queryBuilder.append("AND type = ? ");
		}
		if (!pricemin2.isEmpty()) {
			queryBuilder.append("AND price >= ? ");
		}
		if (!pricemax2.isEmpty()) {
			queryBuilder.append("AND price <= ? ");
		}
		if (!material2.isEmpty() && !material2.equals("All")) {
			queryBuilder.append("AND material = ? ");
		}
		queryBuilder.append(";");
		String query = queryBuilder.toString();
		System.out.println("totest : , " + query);
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			int paramIndex = 1;
			if (!name2.isEmpty()) {
				stmt.setString(paramIndex++, "%" + name2 + "%");
			}
			if (!description2.isEmpty()) {
				stmt.setString(paramIndex++, "%" + description2 + "%");
			}
			if (!brand2.isEmpty()) {
				stmt.setString(paramIndex++, brand2);
			}
			if (!type2.isEmpty() && !type2.equals("All")) {
				stmt.setString(paramIndex++, type2);
			}
			if (!pricemin2.isEmpty()) {
				stmt.setDouble(paramIndex++, Double.parseDouble(pricemin2));
			}
			if (!pricemax2.isEmpty()) {
				stmt.setDouble(paramIndex++, Double.parseDouble(pricemax2));
			}
			if (!material2.isEmpty() && !material2.equals("All")) {
				stmt.setString(paramIndex++, material2);
			}
			System.out.println("tes stm 2 " + stmt.toString());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				System.out.println("orkinng , " + rs.getString("name"));
				Long id = rs.getLong("id");
				String type = rs.getString("type");
				String name = rs.getString("name");
				String brand = rs.getString("brand");
				String description = rs.getString("description");
				double price = rs.getDouble("price");
				String material = rs.getString("material");
				String imagePath = rs.getString("image_path");
				int stock = rs.getInt("stock");
				switch (type) {
					case "Ring":
						int size = rs.getInt("size");

						Ring ring = new Ring(id, name, brand, type, description, price, material, size, imagePath, stock);
						if(ring.getStock()>0){
							results.add(ring);
						}


						break;
					case "Necklace":
						double length = rs.getDouble("length");
						Necklace necklace = new Necklace(id, name, brand, type, description, price, material, length, imagePath, stock);
						if(necklace.getStock()>0){
							results.add(necklace);
						}
						break;
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;

	}

	public ArrayList<Bijoux> getAllProducts(ArrayList<Bijoux> results) {
		String query = "SELECT * FROM products WHERE stock > 0"; // Ensures only products with stock > 0 are retrieved

		try (Statement stmt = connection.createStatement();
			 ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				Long id = rs.getLong("id");
				String type = rs.getString("type");
				String name = rs.getString("name");
				String brand = rs.getString("brand");
				String description = rs.getString("description");
				double price = rs.getDouble("price");
				String material = rs.getString("material");
				String imagePath = rs.getString("image_path");
				int stock = rs.getInt("stock");

				switch (type) {
					case "Ring":
						int size = rs.getInt("size");
						results.add(new Ring(id, name, brand, type, description, price, material, size, imagePath, stock));
						break;
					case "Necklace":
						double length = rs.getDouble("length");
						results.add(new Necklace(id, name, brand, type, description, price, material, length, imagePath, stock));
						break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
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
				String role = resultSet.getString("role");


				Client client = new Client(id, firstName, lastName, emailResult, passwordResult, role);

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


	public String getLastInvoiceNumberOfDB(Long clientId) {
		String query = "SELECT invoice_number FROM Invoices WHERE client_id = ? ORDER BY invoice_id DESC LIMIT 1";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setLong(1, clientId);


			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getString("invoice_number");
				} else {
					return null;
				}
			}
		} catch (SQLException e) {
			System.out.println("Error retrieving last invoice number from the database");
			e.printStackTrace();
			return null;
		}
	}


	public void getClients(ArrayList<Client> clients) {
		String query = "SELECT * from client";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query);
			 ResultSet result = preparedStatement.executeQuery()) {
			while (result.next()) {
				long id = result.getLong("id");
				String firstName = result.getString("firstName");
				String lastName = result.getString("lastName");
				String email = result.getString("email");
				String password = result.getString("password");
				String role = result.getString("role");

				Client client = new Client(id, firstName, lastName, email, password, role);
				clients.add(client);
				//Long id,String firstName, String lastName, String email , String password ,String role 

			}

		} catch (SQLException e) {
			System.out.println("Error retrieving clients from the database");
			e.printStackTrace();
		}

	}
	public boolean saveCart(Cart cart, Long orderId) {
		String query = "INSERT INTO Cart_Items (order_id, product_id, quantity) VALUES (?, ?, ?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			// Iterate over the cart items and insert them into the Cart_Items table
			for (Map.Entry<Bijoux, Integer> entry : cart.getCart().entrySet()) {
				Bijoux bijoux = entry.getKey();
				Integer quantity = entry.getValue();

				preparedStatement.setLong(1, orderId);
				preparedStatement.setLong(2, bijoux.getId());
				preparedStatement.setInt(3, quantity);

				int result = preparedStatement.executeUpdate();
				if (result <= 0) {
					return false;
				}
			}
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	//--------------------------------------------------------ADMIN---------------------------------------------------------------------------------
	public boolean updateClient(Client c) {
		String query = "UPDATE client SET firstName = ?,lastName = ? , email = ?, role = ?,password =? WHERE id = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, c.getFirstName());
			preparedStatement.setString(2, c.getLastName());
			preparedStatement.setString(3, c.getEmail());
			preparedStatement.setString(4, c.getRole());
			preparedStatement.setString(5, c.getPassword());
			preparedStatement.setLong(6, c.getId());


			int rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Client updated successfully.");
				return true;
			} else {
				System.out.println("No client found with the specified ID.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean addClient(Client c) {
		String query = "INSERT INTO client (firstName, lastName, email, role,password) VALUES (?, ?, ?, ?,?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, c.getFirstName());
			preparedStatement.setString(2, c.getLastName());
			preparedStatement.setString(3, c.getEmail());
			preparedStatement.setString(4, c.getRole());
			preparedStatement.setString(5, c.getPassword());

			int result = preparedStatement.executeUpdate();

			return result > 0;
		} catch (SQLException e) {
			System.out.println("Error inserting new client");
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteCLient(Long id) {

		String query = "DELETE FROM client WHERE id = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setLong(1, id);

			int rowsAffected = preparedStatement.executeUpdate();

			return rowsAffected != 0;
		} catch (SQLException e) {
			System.out.println("Error inserting new client");
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateProduct(Bijoux b) {
		String query = "UPDATE products SET name = ?, type = ?, description = ?, price = ?, material = ?, size = ?, length = ?, stock = ?, image_path = ? WHERE id = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, b.getName());
			preparedStatement.setString(2, b.getCategory());
			preparedStatement.setString(3, b.getDescription());
			preparedStatement.setDouble(4, b.getPrice());
			preparedStatement.setString(5, b.getMateriel());


			if ("Ring".equalsIgnoreCase(b.getCategory())) {
				preparedStatement.setDouble(6, ((Ring) b).getSize());
				preparedStatement.setNull(7, java.sql.Types.DOUBLE);
			} else if ("Necklace".equalsIgnoreCase(b.getCategory())) {
				preparedStatement.setNull(6, java.sql.Types.DOUBLE);
				preparedStatement.setDouble(7, ((Necklace) b).getLength());
			}
			preparedStatement.setInt(8, b.getStock());
			preparedStatement.setString(9, b.getImagePath());
			preparedStatement.setLong(10, b.getId());

			int rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Product updated successfully.");
				return true;
			} else {
				System.out.println("No product found with the specified ID.");
			}
		} catch (SQLException e) {
			System.out.println("Error updating product");
			e.printStackTrace();
		}
		return false;
	}

	public boolean addProduct(Bijoux b) {
		String query = "INSERT INTO products (name, type, description, price, material, size, length, stock, image_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, b.getName());
			preparedStatement.setString(2, b.getCategory());
			preparedStatement.setString(3, b.getDescription());
			preparedStatement.setDouble(4, b.getPrice());
			preparedStatement.setString(5, b.getMateriel());

			if (b.getCategory().equals("Ring")) {
				preparedStatement.setDouble(6, ((Ring) b).getSize());
				preparedStatement.setNull(7, java.sql.Types.DOUBLE);
			} else if (b.getCategory().equals("Necklace")) {
				preparedStatement.setNull(6, java.sql.Types.DOUBLE);
				preparedStatement.setDouble(7, ((Necklace) b).getLength());
			} else {
				preparedStatement.setNull(6, java.sql.Types.DOUBLE);
				preparedStatement.setNull(7, java.sql.Types.DOUBLE);
			}

			preparedStatement.setInt(8, b.getStock());
			preparedStatement.setString(9, b.getImagePath());

			int result = preparedStatement.executeUpdate();

			return result > 0;
		} catch (SQLException e) {
			System.out.println("Error inserting new product");
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteProduct(Long id) {

		String query = "DELETE FROM products WHERE id = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setLong(1, id);

			int rowsAffected = preparedStatement.executeUpdate();

			return rowsAffected != 0;
		} catch (SQLException e) {
			System.out.println("Error inserting new client");
			e.printStackTrace();
			return false;
		}
	}
	//public boolean updateInvoice()

	public void getAllInvoices(ArrayList<Invoice> invoices) {
		String query = "select * from Invoices";
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);

			while (rs.next()) {
				Long id = rs.getLong("invoice_id");
				Long client_id = rs.getLong("client_id");
				Long order_id = rs.getLong("order_id");
				String invoice_number = rs.getString("invoice_number");
				String file_path = rs.getString("file_path");
				Timestamp invoice_date = rs.getTimestamp("invoice_date");
				Timestamp invoice_update_date = rs.getTimestamp("invoice_update_date");
				Double total_amount = rs.getDouble("total_amount");
				String status = rs.getString("status");
				//public Invoice(Long clientId, String invoiceNumber, String filePath, double totalAmount, String status,Timestamp invoiceDate,Timestamp updateDate) {
				Invoice invoice = new Invoice(id, client_id, order_id, invoice_number, file_path, total_amount, status, invoice_date, invoice_update_date);
				invoices.add(invoice);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Order fetchOrderById(Long id) {
		String query = "SELECT * FROM Orders WHERE order_id = ?";
		Order order = null;

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				Long orderId = resultSet.getLong("order_id");
				Long clientId = resultSet.getLong("client_id");
				Timestamp orderDate = resultSet.getTimestamp("order_date");
				OrderStatus status = OrderStatus.valueOf(resultSet.getString("status"));

				Cart cartOfOrder = this.fetchCart(orderId);
				order = new Order(orderId, clientId, cartOfOrder,orderDate, status);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return order;
	}

	private Invoice mapInvoice(ResultSet rs) throws SQLException {
		Long invoiceId         = rs.getLong("invoice_id");
		Long clientId          = rs.getLong("client_id");
		Long orderId           = rs.getLong("order_id");
		String invoiceNumber   = rs.getString("invoice_number");
		String filePath        = rs.getString("file_path");
		Timestamp invoiceDate  = rs.getTimestamp("invoice_date");
		Timestamp updateDate   = rs.getTimestamp("invoice_update_date");
		Double totalAmount     = rs.getDouble("total_amount");
		String status          = rs.getString("status");

		return new Invoice(invoiceId, clientId, orderId, invoiceNumber, filePath,
				totalAmount, status, invoiceDate, updateDate);
	}


	public Invoice fetchInvoiceDetailsById(Long id) {
		String query = "SELECT * FROM Invoices WHERE invoice_id = ?";
		Invoice invoice = null;

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, id);
			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					invoice = mapInvoice(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return invoice;
	}
	public String fetchInvoiceById(Long id) {
		String query = "SELECT client_id FROM Invoices WHERE invoice_id = ?";
		String clientId = null;

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, id);
			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					clientId = rs.getString("client_id");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return clientId;
	}

	public void getAllInvoices(ArrayList<Invoice> invoices, Long clientId) {
		String query = "SELECT * FROM Invoices WHERE client_id = ?";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, clientId);
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					Invoice invoice = mapInvoice(rs);
					invoices.add(invoice);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}



	public Cart fetchCart(Long orderId) {
		String query = "SELECT product_id, quantity FROM Cart_Items WHERE order_id = ?";
		Cart cart = new Cart();

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setLong(1, orderId); 
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Long productId = resultSet.getLong("product_id");
				Integer quantity = resultSet.getInt("quantity");


				Bijoux bijoux = fetchBijouxById(productId);
				cart.getCart().put(bijoux, quantity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cart;
	}

	public Bijoux fetchBijouxById(Long productId){
		String query = "select * from products where id = ?";
		try(PreparedStatement statement = connection.prepareStatement(query)){
			statement.setLong(1,productId);

			ResultSet rs = statement.executeQuery();
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
				switch (type) {
					case "Ring":
						int size = rs.getInt("size");
						Ring ring = new Ring(id, name, brand, type, description, price, material, size, imagePath, stock);
						return ring;
					case "Necklace":
						double length = rs.getDouble("length");
						Necklace necklace = new Necklace(id, name, brand, type, description, price, material, length, imagePath, stock);
						return necklace;
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
return null;
	}
	public boolean updateCart(Long orderId, Long productId , int newQuantity){

		String query = "update Cart_Items\n" +
				"set quantity = ? \n" +
				"where order_id= ? and product_id=? ";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1,newQuantity);
			statement.setLong(2,orderId);
			statement.setLong(3,productId);

			int result = statement.executeUpdate();

			return result > 0;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	public boolean deleteFromCart(Long orderId, Long productId){

		String query = "delete from Cart_Items where order_id =? and product_id=? ";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1,orderId);
			statement.setLong(2,productId);

			int result = statement.executeUpdate();

			return result > 0;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateOrder(Long orderId, double totalPrice){
		String query = "update orders\n" +
				"set total_amount = ? where order_id= ?";

		try(PreparedStatement statement = connection.prepareStatement(query)){
			statement.setLong(2,orderId);
			statement.setDouble(1,totalPrice);
			return statement.executeUpdate()>0;

		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}

public boolean updateInvoice(Long invoiceId, Double newTotal){
		String query= "UPDATE Invoices SET total_amount =?, invoice_update_date = CURRENT_TIMESTAMP WHERE invoice_id = ?";
		try(PreparedStatement statement = connection.prepareStatement(query)){
			statement.setLong(2,invoiceId);
			statement.setDouble(1,newTotal);

			return statement.executeUpdate() > 0;
		}catch (SQLException e){
			e.printStackTrace();
		}
		return false;
}
	public boolean deleteInvoice(Long invoiceId) {
		String findOrdersQuery = "SELECT order_id FROM Invoices WHERE invoice_id = ?";
		String findCartItemsQuery = "SELECT product_id, quantity FROM Cart_Items WHERE order_id = ?";
		String deleteInvoiceQuery = "DELETE FROM Invoices WHERE invoice_id = ?";
		String deleteCartQuery = "DELETE FROM Cart_Items WHERE order_id = ?";
		String deleteOrdersQuery = "DELETE FROM Orders WHERE order_id = ?";
		String updateStockQuery = "UPDATE products SET stock = stock + ? WHERE id = ?";

		try {
			connection.setAutoCommit(false); // Start transaction

			// Step 1: Find all orders linked to this invoice
			List<Long> orderIds = new ArrayList<>();
			try (PreparedStatement findOrdersStmt = connection.prepareStatement(findOrdersQuery)) {
				findOrdersStmt.setLong(1, invoiceId);
				try (ResultSet rs = findOrdersStmt.executeQuery()) {
					while (rs.next()) {
						orderIds.add(rs.getLong("order_id"));
					}
				}
			}

			// Step 2: Retrieve all products and quantities in the cart before deletion
			Map<Long, Integer> productQuantities = new HashMap<>();
			try (PreparedStatement findCartStmt = connection.prepareStatement(findCartItemsQuery)) {
				for (Long orderId : orderIds) {
					findCartStmt.setLong(1, orderId);
					try (ResultSet rs = findCartStmt.executeQuery()) {
						while (rs.next()) {
							Long productId = rs.getLong("product_id");
							int quantity = rs.getInt("quantity");
							productQuantities.put(productId, productQuantities.getOrDefault(productId, 0) + quantity);
						}
					}
				}
			}

			// Step 3: Delete the invoice first to prevent foreign key constraint issues
			try (PreparedStatement deleteInvoiceStmt = connection.prepareStatement(deleteInvoiceQuery)) {
				deleteInvoiceStmt.setLong(1, invoiceId);
				deleteInvoiceStmt.executeUpdate();
			}

			// Step 4: Delete all cart items linked to these orders
			try (PreparedStatement deleteCartStmt = connection.prepareStatement(deleteCartQuery)) {
				for (Long orderId : orderIds) {
					deleteCartStmt.setLong(1, orderId);
					deleteCartStmt.executeUpdate();
				}
			}

			// Step 5: Restore stock for all products that were in the deleted cart
			try (PreparedStatement updateStockStmt = connection.prepareStatement(updateStockQuery)) {
				for (Map.Entry<Long, Integer> entry : productQuantities.entrySet()) {
					updateStockStmt.setInt(1, entry.getValue());
					updateStockStmt.setLong(2, entry.getKey());
					updateStockStmt.executeUpdate();
				}
			}

			// Step 6: Delete all orders related to this invoice
			try (PreparedStatement deleteOrdersStmt = connection.prepareStatement(deleteOrdersQuery)) {
				for (Long orderId : orderIds) {
					deleteOrdersStmt.setLong(1, orderId);
					deleteOrdersStmt.executeUpdate();
				}
			}

			connection.commit(); // Commit transaction
			return true;
		} catch (SQLException e) {
			try {
				connection.rollback(); // Rollback on error
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				connection.setAutoCommit(true); // Restore auto-commit
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}


	public ArrayList<Order> fetchOrdersByClient(Long clientId) {
		String query = "SELECT * FROM Orders WHERE client_id = ?";
		ArrayList<Order> orders = new ArrayList<>();

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setLong(1, clientId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Long orderId = rs.getLong("order_id");
				Timestamp orderDate = rs.getTimestamp("order_date");
				OrderStatus status = OrderStatus.valueOf(rs.getString("status"));
				Cart cart = fetchCart(orderId);

				orders.add(new Order(orderId, clientId, cart, orderDate, status));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return orders;
	}
	public Client fetchClientById(Long clientId) {
		String query = "SELECT * FROM client WHERE id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setLong(1, clientId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return new Client(
						rs.getLong("id"),
						rs.getString("firstName"),
						rs.getString("lastName"),
						rs.getString("email"),
						rs.getString("password"),
						rs.getString("role")
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public Client fetchClientByEmail(String email) {
		String query = "SELECT * FROM client WHERE email = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return new Client(
						rs.getLong("id"),
						rs.getString("firstName"),
						rs.getString("lastName"),
						rs.getString("email"),
						rs.getString("password"),
						rs.getString("role")
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<Client> fetchClientsByName(String name) {
		ArrayList<Client> clients = new ArrayList<>();
		String query = "SELECT * FROM client WHERE firstName LIKE ? OR lastName LIKE ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, "%" + name + "%");
			stmt.setString(2, "%" + name + "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				clients.add(new Client(rs.getLong("id"), rs.getString("firstName"), rs.getString("lastName"),
						rs.getString("email"), rs.getString("password"), rs.getString("role")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clients;
	}
	public boolean saveInvoice(Invoice invoice) {
		String query = "UPDATE Invoices " +
				"SET invoice_number = ?, file_path = ?, total_amount = ?, status = ?, invoice_update_date = ?"+
				"WHERE invoice_id = ?";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			// Set parameters for the prepared statement
			statement.setString(1, invoice.getInvoiceNumber());
			statement.setString(2, invoice.getFilePath());
			statement.setDouble(3, invoice.getTotalAmount());
			statement.setString(4, invoice.getStatus());
			statement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			statement.setLong(6, invoice.getInvoiceId());


			int rowsUpdated = statement.executeUpdate();
			return rowsUpdated > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false; // Return false if an error occurred
		}
	}
	public List<String> getDistinctMaterials() {
		String query = "SELECT DISTINCT(material) FROM products";
		List<String> materials = new ArrayList<>();
		materials.add("All");

		try (PreparedStatement statement = connection.prepareStatement(query);
			 ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				materials.add(resultSet.getString("material"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return materials;
	}


	public void incrementStock(Long productId, int quantity) {
		String query = "UPDATE products SET stock = stock + ? WHERE id = ?";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, quantity);
			statement.setLong(2, productId);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public boolean decrementStock(Long productId, int quantity) {
		String query = "UPDATE products SET stock = GREATEST(stock - ?, 0) WHERE id = ? AND stock >= ?";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, quantity);
			statement.setLong(2, productId);
			statement.setInt(3, quantity);

			int rowsAffected = statement.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}


}
