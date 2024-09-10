use db_bobopizza;
    
-- Insertion Type produit
INSERT INTO type_produit (id_type_produit, libelle) VALUES (1, 'PIZZA');
INSERT INTO type_produit (id_type_produit, libelle) VALUES (2, 'BOISSON');

-- Insertion Produits pizza et boisson
INSERT INTO PRODUIT (nom, description, prix, image_url, TYPE_PRODUIT_id_type_produit) VALUES
('Margherita', 'Pizza avec sauce tomate, mozzarella et basilic', 14.00, '/images/pizza-margherita.jpg', 1),
('Pepperoni', 'Pizza avec sauce tomate, mozzarella et pepperoni', 20.00, '/images/pizza-pepperoni.jpg', 1),
('Quattro Formaggi', 'Pizza avec sauce tomate et quatre fromages', 18.00, '/images/pizza-4-fromages.jpg', 1);


INSERT INTO PRODUIT (nom, description, prix, image_url, TYPE_PRODUIT_id_type_produit) VALUES
('Coca-Cola', 'Boisson gazeuse', 4.00, '/images/cocacola-33cl.webp', 2),
('Sprite', 'Boisson gazeuse', 4.00, '/images/sprite-33cl.webp', 2),
('Eau Minérale Badoit', 'Eau minérale naturelle', 3.50, '/images/badoit-33cl.jpg', 2);

-- Insertion Etat
INSERT INTO ETAT (id_etat, libelle) VALUES
(1, 'CREEE'),
(2, 'PREPAREE'),
(3, 'EN LIVRAISON'),
(4, 'LIVREE'),
(5, 'PAYEE');

-- Insertion ROLE
INSERT INTO ROLE (id_role, libelle) VALUES
(1, 'PIZZAIOLO'),
(2, 'LIVREUR'),
(3, 'GERANT');

-- INSERT UTILISATEUR
INSERT INTO UTILISATEUR (nom, prenom, email, mot_de_passe) VALUES 
('DOE', 'John', 'john.doe@email.com', 'password'),
('LIEVRE', 'Lucas', 'lucas.lievre@email.com', 'password'),
('JOUAND', 'Nicolas', 'nicolas.jouand@email.com', 'password');

-- Insertion role_utilisateur
INSERT INTO ROLE_UTILISATEUR (UTILISATEUR_id_utilisateur, ROLE_id_role) VALUES
(1, 1),
(2, 2),
(3, 3);

-- Insertion client
INSERT INTO CLIENT (prenom, nom, rue, code_postal, ville) VALUES
('Stéphane', 'GOBIN', '2b rue Michael FARADAY', '44300', 'SAINT-HERBLAIN'); 