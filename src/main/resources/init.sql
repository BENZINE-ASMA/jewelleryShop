USE sys;

SET SQL_SAFE_UPDATES = 0;

DROP TABLE IF EXISTS Cart_Items;
DROP TABLE IF EXISTS Invoices;
DROP TABLE IF EXISTS Orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS client;

CREATE TABLE client (
    id INT PRIMARY KEY AUTO_INCREMENT,
    firstName VARCHAR(100) NOT NULL,
    lastName VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role ENUM('ADMIN', 'CLIENT') DEFAULT 'CLIENT'
);

INSERT INTO client (firstName, lastName, email, password, role)
VALUES ('admin', 'admin', 'admin', 'admin', 'ADMIN');
INSERT INTO client (firstName, lastName, email, password, role)
VALUES ('asma', 'as', 'asma.benzine010@gmail.com', '123', 'CLIENT');

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

CREATE TABLE Orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    client_id INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('EN_COURS', 'VALIDEE', 'LIVREE') DEFAULT 'EN_COURS',
    total_amount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (client_id) REFERENCES client(id)
);
CREATE TABLE Invoices (
    invoice_id INT PRIMARY KEY AUTO_INCREMENT,
    client_id INT NOT NULL,
    order_id INT NOT NULL,
    invoice_number VARCHAR(50) UNIQUE NOT NULL,
    file_path VARCHAR(255) NOT NULL,
    invoice_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    invoice_update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2) NOT NULL,
    status ENUM('Pending', 'Paid', 'Overdue','Updated') DEFAULT 'Pending',
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
    8,
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
    4, -- Default stock value, adjust if needed
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
    2, -- Default stock value, adjust if needed
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
INSERT INTO products (name, brand, type, description, price, material, size, length, stock, image_path)
VALUES (
    ' Lucky Alhambra Papillon',
    'Van Cleef & Arpels',
    'Necklace',
    'Collier Lucky Alhambra Van Cleef & Arpels de seconde main en or blanc 18 carats (750/1000). Un charmant motif de papillon orné de turquoise.

     Le motif iconique de la marque est ici décliné en taille plus grande et en forme de papillon entouré d’un contour perlé, élément distinctif de la collection Alhambra.

     Un délicat papillon au charme fou à suspendre à votre cou.

     Etat : Seconde Main de Catégorie A : Excellent Etat avec peu de traces d’usage visibles.

     Prix du neuf : 5 350 €',
    5350.00,
    'Or Blanc, Turquoise',
    NULL,  -- No size needed for necklaces
    42,  -- Estimated length of the necklace in cm (adjust if needed)
    6,  -- Default stock, adjust as needed
    'resources/jewelry/necklaces/lucky_alhambra_papillon_vca.png'  -- Ensure correct image path
);
INSERT INTO products (name, brand, type, description, price, material, size, length, stock, image_path)
VALUES (
    'Cartier d’Amour Cartier',
    'Cartier',
    'Necklace',
    'Collier Cartier d’Amour de seconde main en or blanc 18 carats (750/1000) et saphir. Une chaîne fine et délicate retenant un saphir bleu dans un double serti clos.

     Pureté du design, élégance intemporelle… Ce bijou tout en délicatesse porte en lui les valeurs chères à la maison Cartier.

     Portez le raffinement à l’état pur avec ce bijou léger et délicat.

     Etat : Seconde Main de Catégorie A : Excellent Etat avec peu de traces d’usage visibles.

     Prix du neuf : 562 €',
    562.00,
    'Or Blanc, Saphir',
    NULL,  -- No size needed for necklaces
    42,  -- Estimated length of the necklace in cm (adjust if needed)
    2,  -- Default stock, adjust as needed
    'resources/jewelry/necklaces/cartier_damour_saphir.png'  -- Ensure correct image path
);
INSERT INTO products (name, brand, type, description, price, material, size, length, stock, image_path)
VALUES (
    'Pendentif Jeux de Liens Chaumet',
    'Chaumet',
    'Necklace',
    'Pendentif Jeux de Liens Chaumet de seconde main en or blanc 750/1000 serti de nacre et d’un diamant taille brillant arrondi, monté sur une chaîne en or blanc.

     Une collection historique de Chaumet qui propose des pièces contemporaines, symbolisant le sentiment qui unit 2 êtres.

     Un peu, beaucoup, passionnément : le lien qui vous unit mérite bien son bijou.

     Etat : Seconde Main de Catégorie A : Excellent Etat avec peu de traces d’usage visibles.

     Prix du neuf : 1 880 €',
    1880.00,
    'Or Blanc, Nacre, Diamant',
    NULL,  -- No size needed for necklaces
    45,  -- Estimated length of the necklace in cm (adjust if needed)
    1,  -- Default stock, adjust as needed
    'resources/jewelry/necklaces/jeux_de_chaumet.png'  -- Ensure correct image path
);
INSERT INTO products (name, brand, type, description, price, material, size, length, stock, image_path)
VALUES (
    'Pendentif Lockit Key',
    'Louis Vuitton',
    'Necklace',
    'Pendentif Lockit Key Louis Vuitton de seconde main en or blanc 18 carats (750/1000) présentant deux clés habillées de diamants retenues par un anneau carré dont la tranche extérieure est sertie de diamants.

     Deux clés entièrement pavées de diamants qui rappellent les clés des emblématiques bagages Louis Vuitton, à porter en collier ou bracelet. Le modèle présenté est accompagné d’une chaîne délicate en or blanc.

     Deux clés tout en diamants qui symbolisent l’amour scellé. A offrir d’urgence.

     Etat : Seconde Main de Catégorie A : Excellent Etat avec peu de traces d’usage visibles.',
    2000.00,
    'Or Blanc, Diamant',
    NULL,
    45,
    3,
    'resources/jewelry/necklaces/lockit_key_louis_vuitton.png'
);

INSERT INTO products (name, brand, type, description, price, material, size, length, stock, image_path)
VALUES (
    'Bague Jonc Vintage Poiray',
    'Poiray',
    'Ring',
    'Bague Jonc Vintage Poiray de seconde main en or jaune 18 carats (750/1000). Bague présentant une pierre Améthyste en serti clos sur un jonc légèrement bombé.

     Une bague indémodable avec un joli sertissage de la pierre qui souligne sa couleur et lui donne une élégance classique.

     Si vous deviez choisir un jonc intemporel signé par une grande maison, ce serait celui-ci.

     Etat : Seconde Main de Catégorie A : Excellent Etat avec peu de traces d’usage visibles.',
    850.00,
    'Or Jaune, Améthyste',
    50,
    NULL,
    1,
    'resources/jewelry/rings/jonc_vintage_poiray.png'
);




INSERT INTO products (name, brand, type, description, price, material, size, length, stock, image_path)
VALUES (
    'Pendentif Bean Elsa Peretti',
    'Tiffany & Co',
    'Necklace',
    'Pendentif Bean Tiffany & Co de seconde main en argent 925/1000 dessiné par Elsa Peretti. Le motif en forme de pépite ou haricot est glissé sur une chaîne argent maille forçat.

     Le motif « Bean » est à la fois une ode au design le plus pur et un hommage à la nature : simplicité de la forme, élégance naturelle, beauté de la nature. Signé Tiffany & Co et Elsa Peretti.

     Si simple et pourtant si chic, une fois autour de votre cou vous ne pourrez plus le quitter.

     Etat : Seconde Main Rénové : Excellent Etat avec peu ou pas de traces d’usage visibles.

     Prix du neuf : 560 €',
    340.00,
    'Argent 925',
    NULL,
    45,
    5,
    'resources/jewelry/necklaces/bean_elsa_peretti_tiffany.png'  -- Adjust the image path
);
INSERT INTO products (name, brand, type, description, price, material, size, length, stock, image_path)
VALUES (
    'Sautoir Vintage Alhambra ',
    'Van Cleef & Arpels',
    'Necklace',
    'Sautoir Vintage Alhambra signé Van Cleef & Arpels, en or jaune 18 carats (750/1000). 20 motifs fleurs en forme de trèfle ornés d’un délicat contour de perles d’or.

     À porter en double ou simple tour. Le motif est non amovible. La taille du motif (15 mm) est la taille classique Alhambra Vintage. Le bracelet et les puces d’oreilles assortis sont également proposés à la vente.

     Une pièce magnifique, indémodable, inégalable. L’icône des icônes.

     Prix du neuf : 19 200 €',
    19200.00,
    'Or Jaune',
    NULL,  -- No size needed for necklaces
    86,  -- Standard length of Van Cleef & Arpels Alhambra sautoirs (adjust if needed)
    3,  -- Default stock, adjust as needed
    'resources/jewelry/necklaces/vintage_alhambra_vca.png'  -- Ensure the image exists in this path
);
INSERT INTO products (name, brand, type, description, price, material, size, length, stock, image_path)
VALUES (
    ' Tu es le Sel de ma Vie ',
    'Mauboussin',
    'Ring',
    'Bague Tu es le sel de ma vie Mauboussin de seconde main en or blanc 18 carats (750/1000). Le solitaire présente un diamant central réhaussé par 6 petits diamants sur le corps de bague.

     Misant sur la simplicité absolue, la bague Tu es le sel de ma vie est le solitaire intemporel par excellence. Mauboussin célèbre à travers lui l’amour simple qui dure toute une vie.

     Adoptez la simplicité d’un intemporel Mauboussin pour célébrer vos sentiments.

     Etat : Seconde Main Rénové : Excellent Etat avec peu ou pas de traces d’usage visibles.

     Prix du neuf : 950 €',
    580.00,
    'Or Blanc, Diamant',
    48,  -- Size T48 (French sizing)
    NULL,  -- Length is not applicable for rings
    6,  -- Default stock, adjust if needed
    'resources/jewelry/rings/tu_es_le_sel_mauboussin.png'  -- Adjust the image path
);
INSERT INTO products (name, brand, type, description, price, material, size, length, stock, image_path)
VALUES (
    'Bague Trinity Cartier',
    'Cartier',
    'Ring',
    'Bague Trinity Cartier de seconde main avec 3 anneaux entrelacés en Or Rose, Or Blanc et Or Jaune 18 carats (750/1000). Petit modèle.

     3 anneaux simples qui ensemble créent une forme singulière immédiatement reconnaissable comme iconique de la maison Cartier.

     Ne passez pas à côté de l’iconique Trinity Cartier. Passez cette bague culte à votre doigt.

     Etat : Seconde Main Rénové : Excellent Etat avec peu ou pas de traces d’usage visibles.

     Prix du neuf : 1 540 €',
    1200.00,
    'Or Rose, Or Blanc, Or Jaune',
    52,  -- Size T52 (French sizing)
    NULL,  -- Length is not applicable for rings
    5,  -- Default stock, adjust if needed
    'resources/jewelry/rings/trinity_cartier.png'  -- Ensure correct image path
);

ALTER TABLE Orders ADD FOREIGN KEY (client_id) REFERENCES client(id);
ALTER TABLE Cart_Items ADD FOREIGN KEY (product_id) REFERENCES products(id);
ALTER TABLE Orders ADD CONSTRAINT fk_client_orders FOREIGN KEY (client_id) REFERENCES client(id) ON DELETE CASCADE;
ALTER TABLE Cart_Items ADD CONSTRAINT fk_cart_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE;
