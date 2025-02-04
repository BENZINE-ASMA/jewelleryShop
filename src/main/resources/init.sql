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
VALUES
(
    'Bague Double Sens Dinh Van',
    'Dinh Van',
    'Ring',
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
),
(
    'Bague Move Romane Messika',
    'Messika',
    'Ring',
    'Bague Move Romane Messika de seconde main en or jaune 18 carats (750/1000). 3 diamants mobiles se baladent entre ces lignes graphiques.

    Un design hypnotique créé par Valérie Messika, qui invente la nouvelle joaillerie : moderne, inspirante, légère et assumée.

    Adoptez ce nouveau classique de la joaillerie par Messika.

    Etat : Seconde Main Rénové : Excellent Etat avec peu ou pas de traces d’usage visibles.

    Prix du neuf : 3850 €',
    2850.00,
    'Or Rose, Diamant',
    55,
    NULL,
    1,
    'resources/jewelry/rings/move_romane_messika.png'
),
(
    'Bague Atlas Tiffany & Co',
    'Tiffany & Co',
    'Ring',
    'Bague Atlas Tiffany & Co de seconde main en or rose 18 carats (750/1000). L’anneau travaillé de chiffres romains est, sur une face, réhaussé de 3 diamants alignés. Une collection riche de sens qui met à l’honneur l’héritage historique de la maison. Signature discrète pour un grand nom. A s’offrir d’urgence.

    Etat : Seconde Main de Catégorie A : Excellent Etat avec peu de traces d’usage visibles.',
    850.00,
    'Or Rose, Diamant',
    48,
    NULL,
    1,
    'resources/jewelry/rings/atlas_tiffany_co.png'
),
(
    'Alliance Sertis Château',
    'Graff',
    'Ring',
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
),
(
    'Attrape moi si tu m’aimes',
    'Chaumet',
    'Necklace',
    'Pendentif Attrape moi si tu m’aimes Chaumet de seconde main en or jaune 18 carats (750/1000), avec pendentif sphérique figurant une toile d’araignée intégralement pavée de diamants. Au centre, un diamant plus important. Monté sur triple chaîne maille forçat en or jaune. Fermoir mousqueton. Non réglable.

    Attrape moi si tu m’aimes : dans ce nom tout est dit. Un long pendentif qui habille n’importe quelle tenue.

    Laissez-vous charmer. Tombez dans ses filets de diamants.

    Etat : Seconde Main de Catégorie A : Excellent Etat avec peu de traces d’usage visibles.

    Prix du neuf : 14 500 €',
    10125.00,
    'Or Jaune, Diamant',
    NULL,
    50,
    1,
    'resources/jewelry/necklaces/attrape_moi_chaumet.png'
),
(
    'Jeux de Liens Chaumet',
    'Chaumet',
    'Necklace',
    'Pendentif Jeux de Liens Chaumet de seconde main en or blanc 750/1000 serti de nacre et d’un diamant taille brillant arrondi, monté sur une chaîne en or blanc.

    Une collection historique de Chaumet qui propose des pièces contemporaines, symbolisant le sentiment qui unit 2 êtres.

    Un peu, beaucoup, passionnément : le lien qui vous unit mérite bien son bijou.

    Etat : Seconde Main de Catégorie A : Excellent Etat avec peu de traces d’usage visibles.',
    1880.00,
    'Or Blanc, Nacre, Diamant',
    NULL,
    45,
    1,
    'resources/jewelry/necklaces/jeux_de_liens_chaumet.png'
),
(
    'Calibre Diamants Fred',
    'Fred',
    'Necklace',
    'Pendentif Calibre Diamants de Fred de seconde main, en or blanc 18 carats (750/1000), serti d’un diamant. Un design créatif et innovant, une grille de calibrage de diamants devient un pendentif pour homme ou femme. Passez ce pendentif totalement inédit, et signé Fred, à votre cou.

    Etat : Seconde Main Rénové : Excellent Etat avec peu ou pas de traces d’usage visibles.',
    3450.00,
    'Or Blanc, Diamant',
    NULL,
    50,
    0,
    'resources/jewelry/necklaces/calibre_diamants_fred.png'
),
(
    'Pendentif Lien Chaumet',
    'Chaumet',
    'Necklace',
    'Pendentif Lien signé Chaumet en or rose 18 carats (750/1000), de seconde main : 2 liens croisés délicatement pavés de diamants montés sur chaîne maille forçat.

    Une collection historique de Chaumet qui propose des pièces contemporaines, symbolisant le sentiment qui unit 2 êtres.

    Un peu, beaucoup, passionnément : le lien qui vous unit mérite bien son bijou.',
    3380.00,
    'Or Rose, Diamant',
    NULL,
    45,
    1,
    'resources/jewelry/necklaces/lien_chaumet.png'
);
INSERT INTO products (name, brand, type, description, price, material, size, length, stock, image_path)
VALUES (
    'M’ama non M’ama Pomellato',
    'Pomellato',
    'Ring', -- Ensure this matches the ENUM type if applicable
    'Bague M’ama non M’ama Pomellato de seconde main en or rose 18 carats (750/1000). Un cabochon de pierre de lune retenu par un anneau fil rond en or rose.

     L’icône de Pomellato ici en version pierre de lune. A associer à d’autres bagues de la maison pour une combinaison pleine de peps.

     La pierre rien que la pierre ! Adoptez cette beauté brute, minimaliste et pourtant tellement chic.

     Etat : Seconde Main Rénové : Excellent Etat avec peu ou pas de traces d’usage visibles.

     Prix du neuf : 1 500 €',
    900.00,
    'Or Rose, Pierre de Lune',
    55,
    NULL,
    1,
    'resources/jewelry/rings/mama_non_mama_pomellato.png' -- Adjust with actual image path
);
INSERT INTO products (name, brand, type, description, price, material, size, length, stock, image_path)
VALUES (
    'Bague Empreinte Louis Vuitton',
    'Louis Vuitton',
    'Ring', -- Ensure this matches the ENUM type if applicable
    'Bague bandeau Louis Vuitton en or jaune 18 carats (750/1000) de seconde main, avec 5 empreintes espacées et les initiales LV en relief au centre de l’anneau.

     Cette bague au design épuré avec une signature centrale est issue de la collection « Empreinte », la première collection de joaillerie lancée par Louis Vuitton.

     Empreinte : un design brut, qui magnifie la matière. Une pépite à porter tous les jours.

     Etat : Seconde Main Rénové : Excellent Etat avec peu ou pas de traces d’usage visibles.

     Prix du neuf : 2 210 €',
    1600.00,
    'Or Blanc',
    59, -- Assuming size T59 corresponds to 59
    NULL, -- Length is not applicable for rings
    1, -- Default stock value, adjust if needed
    'resources/jewelry/rings/empreinte_louis_vuitton.png' -- Adjust with actual image path
);
INSERT INTO products (name, brand, type, description, price, material, size, length, stock, image_path)
VALUES (
    'Bague LV Volt Louis Vuitton',
    'Louis Vuitton',
    'Ring', -- Ensure this matches the ENUM type if applicable
    'Bague LV Volt Louis Vuitton de seconde main en or blanc 18 carats (750/1000). Un anneau au motif LV stylisé, dont les lettres L et V sont gravées et répétées à intervalles réguliers.

     Une collection qui met à l’honneur l’énergie de la maison Vuitton dans une précision inégalée.

     Vous aussi, craquez pour les initiales Louis Vuitton en mouvement.

     Etat : Seconde Main de Catégorie AB : Bon Etat avec quelques traces d’usage ou patine visibles.

     Prix du neuf : 3 950 €',
    2800.00,
    'Or Blanc',
    58, -- Assuming size T58 corresponds to 58
    NULL, -- Length is not applicable for rings
    1, -- Default stock value, adjust if needed
    'resources/jewelry/rings/lv_volt_louis_vuitton.png' -- Adjust with actual image path
);
INSERT INTO products (name, brand, type, description, price, material, size, length, stock, image_path)
VALUES (
    'Pendentif Infinity Tiffany & Co',
    'Tiffany & Co',
    'Necklace', -- Ensure this matches the ENUM type if applicable
    'Pendentif Infinity de Tiffany & Co de seconde main en Platine 950/1000 et Diamants, sur chaîne platine fine. Motif fixe de forme du symbole infini orné de brillants.

     La double chaîne délicatement attachée au symbole infini souligne toute la portée de ce bijou, symbole d’amour ou d’amitié éternels.

     Premier bijou, cadeau d’amitié ou preuve d’amour infini? Offrez ce pendentif infiniment symbolique par Tiffany & Co.

     Etat : Seconde Main de Catégorie AB : Bon Etat avec quelques traces d’usage ou patine visibles.

     Prix du neuf : 2 500 €',
    1500.00,
    'Platine, Diamant',
    NULL, -- Size is not applicable for necklaces
    45, -- Invented length for the necklace (45 cm)
    1, -- Default stock value, adjust if needed
    'resources/jewelry/necklaces/infinity_tiffany_co.png' -- Adjust with actual image path
);
INSERT INTO products (name, brand, type, description, price, material, size, length, stock, image_path)
VALUES (
    'Collier Menottes R10',
    'Dinh Van',
    'Necklace', -- Ensure this matches the ENUM type if applicable
    'Collier Menottes R10 Dinh Van de seconde main en or blanc 18 carats (750/1000) sur une chaîne maille cheval en or blanc. Un motif de doubles menottes entrelacées, dont l’une est entièrement pavée de diamants.

     Le motif représente une paire de menottes faisant office de fermoir : une création iconique et intemporelle signée Dinh Van.

     Choisissez ce bijou qui bouscule les codes de la joaillerie : des menottes symbole d’amour et d’attachement pour homme ou pour femme.

     Etat : Seconde Main de Catégorie A : Excellent Etat avec peu de traces d’usage visibles.

     Prix du neuf : 4 770 €',
    3125.00,
    'Or Blanc, Diamant',
    NULL, -- Size is not applicable for necklaces
    50, -- Invented length for the necklace (50 cm)
    1, -- Default stock value, adjust if needed
    'resources/jewelry/necklaces/menottes_r10_dinh_van.png' -- Adjust with actual image path
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
