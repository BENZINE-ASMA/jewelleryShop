-- Use the system database
USE sys;

-- Enable updates
SET SQL_SAFE_UPDATES = 0;

-- Drop tables if they exist
DROP TABLE IF EXISTS Cart_Items;
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
VALUES ('admin', 'admin', 'admin', 'admin', 'ADMIN');
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
INSERT INTO products (name, brand, type, description, price, material, size, length, stock, image_path)
VALUES (
    'Bague Double Sens Dinh Van',
    'Dinh Van',
    'Ring',  -- Ensure this matches an ENUM type if applicable
    'Bague Double Sens Dinh Van de seconde main en or blanc 18 carats (750/1000). Monté sur un anneau large, le motif central rappelle le yin et le yang : il est arrondi et ajouré pour laisser entrevoir la peau.

     La collection Double Sens rend hommage à la dualité et la complémentarité du Yin et du Yang. Les deux courbes s’allient harmonieusement dans un élan de sensualité pour laisser la place à la sérénité.

     Parez-vous de cette bague sculpturale et graphique qui affirme un style singulier.

     Etat : Seconde Main de Catégorie AB : Bon Etat avec quelques traces d’usage ou patine visibles.

     Prix du neuf : 880 €',
    880.00,
    'Or Blanc',
    55,
    NULL,
    1,
    'resources/jewelry/rings/double_sens_dinh_van.png'
);
INSERT INTO products (name, brand, type, description, price, material, size, length, stock, image_path)
VALUES (
    'Bague Move Romane Messika',
    'Messika',
    'Ring', -- Ensure this matches the ENUM type if applicable
    'Bague Move Romane Messika de seconde main en or jaune 18 carats (750/1000). 3 diamants mobiles se baladent entre ces lignes graphiques.

     Un design hypnotique créé par Valérie Messika, qui invente la nouvelle joaillerie : moderne, inspirante, légère et assumée.

     Adoptez ce nouveau classique de la joaillerie par Messika.

     Etat : Seconde Main Rénové : Excellent Etat avec peu ou pas de traces d’usage visibles.

     Prix du neuf : 3850 €',
    2850.00,
    'Or Rose, Diamant',
    55, -- Assuming size T55 corresponds to 55
    NULL, -- Assuming length is not applicable for rings
    1, -- Default stock value, adjust if needed
    'resources/jewelry/rings/move_romane_messika.png' -- Adjust with actual image path
);
INSERT INTO products (name, brand, type, description, price, material, size, length, stock, image_path)
VALUES (
    'Bague Atlas Tiffany & Co',
    'Tiffany & Co',
    'Ring', -- Ensure this matches the ENUM type if applicable
    'Bague Atlas Tiffany & Co de seconde main en or rose 18 carats (750/1000). L’anneau travaillé de chiffres romains est, sur une face, réhaussé de 3 diamants alignés. Une collection riche de sens qui met à l’honneur l’héritage historique de la maison. Signature discrète pour un grand nom. A s’offrir d’urgence.

     Etat : Seconde Main de Catégorie A : Excellent Etat avec peu de traces d’usage visibles.',
    850.00,
    'Or Rose, Diamant',
    48, -- Assuming size T48 corresponds to 48
    NULL, -- Assuming length is not applicable for rings
    1, -- Default stock value, adjust if needed
    'resources/jewelry/rings/atlas_tiffany_co.png' -- Adjust with actual image path
);
INSERT INTO products (name, brand, type, description, price, material, size, length, stock, image_path)
VALUES (
    'Alliance Sertis Château',
    'Graff',
    'Ring', -- Ensure this matches the ENUM type if applicable
    'Alliance Graff de seconde main en platine 950/1000 et diamants : un anneau en platine entièrement pavé de diamants de forme rondes au serti château.

     Graff, une maison qui a su faire du diamant sa spécialité et sa renommée, signe ici un classique.

     Une alliance au sertissage identitaire qui symbolisera magnifiquement un amour éternel.

     Etat : Seconde Main de Catégorie A : Excellent Etat avec peu de traces d’usage visibles.

     Prix du neuf : 13 400 €',
    8000.00,
    'Platine, Diamant',
    49,
    NULL,
    1,
    'resources/jewelry/rings/diamants_ronds_graff.png'
);

INSERT INTO products (name, brand, type, description, price, material, size, length, stock, image_path)
VALUES (
    ' Attrape moi si tu m’aimes',
    'Chaumet',
    'Necklace', 
    'Pendentif Attrape moi si tu m’aimes Chaumet de seconde main en or jaune 18 carats (750/1000), avec pendentif sphérique figurant une toile d’araignée intégralement pavée de diamants. Au centre, un diamant plus important. Monté sur triple chaîne maille forçat en or jaune. Fermoir mousqueton. Non réglable.

     Attrape moi si tu m’aimes : dans ce nom tout est dit. Un long pendentif qui habille n’importe quelle tenue.

     Laissez vous charmer. Tombez dans ses filets de diamants.

     Etat : Seconde Main de Catégorie A : Excellent Etat avec peu de traces d’usage visibles.

     Prix du neuf : 14 500 €',
    10125.00,
    'Or Jaune, Diamant',
    NULL, -- Size is not applicable for necklaces
    NULL, -- Length not provided in description
    1, -- Default stock value, adjust if needed
    'resources/jewelry/necklaces/attrape_moi_chaumet.png' -- Adjust with actual image path
);



-- Insert initial


-- Create the `Orders` table
CREATE TABLE Orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    client_id INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('EN_COURS', 'VALIDEE', 'LIVREE') DEFAULT 'EN_COURS',
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
    invoice_update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2) NOT NULL,
    status ENUM('Pending', 'Paid', 'Overdue') DEFAULT 'Pending',
    FOREIGN KEY (client_id) REFERENCES client(id),
    FOREIGN KEY (order_id) REFERENCES Orders(order_id)
);

CREATE TABLE Cart_Items (
    cart_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT,
    FOREIGN KEY (order_id) REFERENCES Orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);
