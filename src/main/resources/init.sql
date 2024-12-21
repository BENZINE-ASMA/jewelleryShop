-- Use the system database
USE sys;

-- Enable updates
SET SQL_SAFE_UPDATES = 0;

-- Drop tables if they exist
DROP TABLE IF EXISTS Invoices;
DROP TABLE IF EXISTS Orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS client;

-- Create the `client` table
CREATE TABLE client (
    id INT PRIMARY KEY AUTO_INCREMENT,
    firstName VARCHAR(100) NOT NULL,
    lastName VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role ENUM('ADMIN', 'CLIENT') DEFAULT 'CLIENT'
);

-- Insert initial admin client
INSERT INTO client (firstName, lastName, email, password, role)
VALUES ('admin', 'admin', 'admin.admin@dauphine.eu', 'admin', 'ADMIN');
INSERT INTO client (firstName, lastName, email, password, role)
VALUES ('asma', 'as', 'asma', '123', 'CLIENT');

-- Create the `products` table
CREATE TABLE products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    brand VARCHAR(100),
    type ENUM('Ring', 'Necklace') NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    material VARCHAR(100),
    size INT,
    length DOUBLE,
    stock INT NOT NULL,
    image_path VARCHAR(255)
);

-- Insert initial products
INSERT INTO products (name, brand, type, description, price, material, size, length, stock, image_path)
VALUES
    ('Diamond Ring', 'Luxury', 'Ring', 'A beautiful diamond ring with a sleek design.', 299.99, 'Gold', 7, NULL, 15, 'resources/Hotline.png'),
    ('Sapphire Ring', 'Prestige', 'Ring', 'A stunning sapphire ring set in white gold.', 199.99, 'White Gold', 6, NULL, 10, 'resources/Hotline.png'),
    ('Pearl Necklace', 'Elegance', 'Necklace', 'Elegant pearl necklace with silver chain.', 499.99, 'Silver', NULL, 18.5, 5, 'resources/Hotline.png'),
    ('Diamond Ring', 'Luxury', 'Ring', 'A beautiful diamond ring with a sleek design.', 299.99, 'Gold', 7, NULL, 15, 'resources/Hotline.png'),
    ('Diamond Ring', 'Luxury', 'Ring', 'A beautiful diamond ring with a sleek design.', 299.99, 'Gold', 7, NULL, 15, 'resources/Hotline.png'),
    ('Diamond Ring', 'Luxury', 'Ring', 'A beautiful diamond ring with a sleek design.', 299.99, 'Gold', 7, NULL, 15, 'resources/Hotline.png'),
    ('Diamond Ring', 'Luxury', 'Ring', 'A beautiful diamond ring with a sleek design.', 299.99, 'Gold', 7, NULL, 15, 'resources/Hotline.png'),
    ('Diamond Ring', 'Luxury', 'Ring', 'A beautiful diamond ring with a sleek design.', 299.99, 'Gold', 7, NULL, 15, 'resources/Hotline.png'),
    ('Diamond Ring', 'Luxury', 'Ring', 'A beautiful diamond ring with a sleek design.', 299.99, 'Gold', 7, NULL, 15, 'resources/Hotline.png'),
    ('Diamond Ring', 'Luxury', 'Ring', 'A beautiful diamond ring with a sleek design.', 299.99, 'Gold', 7, NULL, 15, 'resources/Hotline.png'),
    ('Diamond Ring', 'Luxury', 'Ring', 'A beautiful diamond ring with a sleek design.', 299.99, 'Gold', 7, NULL, 15, 'resources/Hotline.png'),
    ('Diamond Ring', 'Luxury', 'Ring', 'A beautiful diamond ring with a sleek design.', 299.99, 'Gold', 7, NULL, 15, 'resources/Hotline.png'),
    ('Diamond Ring', 'Luxury', 'Ring', 'A beautiful diamond ring with a sleek design.', 299.99, 'Gold', 7, NULL, 15, 'resources/Hotline.png'),
    ('Diamond Ring', 'Luxury', 'Ring', 'A beautiful diamond ring with a sleek design.', 299.99, 'Gold', 7, NULL, 15, 'resources/Hotline.png'),
    ('Diamond Ring', 'Luxury', 'Ring', 'A beautiful diamond ring with a sleek design.', 299.99, 'Gold', 7, NULL, 15, 'resources/Hotline.png'),
    ('Diamond Ring', 'Luxury', 'Ring', 'A beautiful diamond ring with a sleek design.', 299.99, 'Gold', 7, NULL, 15, 'resources/Hotline.png'),
    ('Diamond Ring', 'Luxury', 'Ring', 'A beautiful diamond ring with a sleek design.', 299.99, 'Gold', 7, NULL, 15, 'resources/Hotline.png'),
    ('Diamond Ring', 'Luxury', 'Ring', 'A beautiful diamond ring with a sleek design.', 299.99, 'Gold', 7, NULL, 15, 'resources/Hotline.png'),
    ('Diamond Ring', 'Luxury', 'Ring', 'A beautiful diamond ring with a sleek design.', 299.99, 'Gold', 7, NULL, 15, 'resources/Hotline.png'),
    ('Diamond Ring', 'Luxury', 'Ring', 'A beautiful diamond ring with a sleek design.', 299.99, 'Gold', 7, NULL, 15, 'resources/Hotline.png'),
    ('Diamond Ring', 'Luxury', 'Ring', 'A beautiful diamond ring with a sleek design.', 299.99, 'Gold', 7, NULL, 15, 'resources/Hotline.png'),
    ('Diamond Ring', 'Luxury', 'Ring', 'A beautiful diamond ring with a sleek design.', 299.99, 'Gold', 7, NULL, 15, 'resources/Hotline.png'),
    ('Diamond Ring', 'Luxury', 'Ring', 'A beautiful diamond ring with a sleek design.', 299.99, 'Gold', 7, NULL, 15, 'resources/Hotline.png'),
    ('Diamond Ring', 'Luxury', 'Ring', 'A beautiful diamond ring with a sleek design.', 299.99, 'Gold', 7, NULL, 15, 'resources/Hotline.png'),
    ('Diamond Ring', 'Luxury', 'Ring', 'A beautiful diamond ring with a sleek design.', 299.99, 'Gold', 7, NULL, 15, 'resources/Hotline.png'),
    ('Diamond Ring', 'Luxury', 'Ring', 'A beautiful diamond ring with a sleek design.', 299.99, 'Gold', 7, NULL, 15, 'resources/Hotline.png'),
    ('Diamond Ring', 'Luxury', 'Ring', 'A beautiful diamond ring with a sleek design.', 299.99, 'Gold', 7, NULL, 15, 'resources/Hotline.png'),
    ('Diamond Ring', 'Luxury', 'Ring', 'A beautiful diamond ring with a sleek design.', 299.99, 'Gold', 7, NULL, 15, 'resources/Hotline.png'),
    ('Diamond Ring', 'Luxury', 'Ring', 'A beautiful diamond ring with a sleek design.', 299.99, 'Gold', 7, NULL, 15, 'resources/Hotline.png');




-- Create the `Orders` table
CREATE TABLE Orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    client_id INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('en cours', 'validée', 'livrée') DEFAULT 'en cours',
    total_amount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (client_id) REFERENCES client(id)
);

-- Create the `Invoices` table
CREATE TABLE Invoices (
    invoice_id INT PRIMARY KEY AUTO_INCREMENT,
    client_id INT NOT NULL,
    order_id INT NOT NULL,
    invoice_number VARCHAR(50) UNIQUE NOT NULL,
    file_path VARCHAR(255) NOT NULL,
    invoice_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2) NOT NULL,
    status ENUM('Pending', 'Paid', 'Overdue') DEFAULT 'Pending',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES client(id),
    FOREIGN KEY (order_id) REFERENCES Orders(order_id)
);
