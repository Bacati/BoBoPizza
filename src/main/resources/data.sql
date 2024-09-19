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
(1, 'PANIER'),
(2, 'CREEE'),
(3, 'EN PREPARATION'),
(4, 'PREPAREE'),
(5, 'EN LIVRAISON'),
(6, 'LIVREE'),
(7, 'PAYEE');

-- Insertion ROLE
INSERT INTO ROLE (id_role, libelle) VALUES
(1, 'CLIENT'),
(2, 'PIZZAIOLO'),
(3, 'LIVREUR'),
(4, 'GERANT');

-- INSERT UTILISATEUR
INSERT INTO UTILISATEUR (classe, nom, prenom, rue, code_postal, ville, email, mot_de_passe) VALUES 
('E', 'LIEVRE', 'Lucas', 'Entreprise BoBoPizza', '44000', 'Nantes', 'lucas.lievre@email.com', '{bcrypt}$2a$10$is.uot1BQvaGXdAQd/NSaOWKraONCPdJ7/Ch9puRgNQfR8ya/BRYm'),
('E', 'JOUAND', 'Nicolas', 'Entreprise BoBoPizza', '44000', 'Nantes', 'nicolas.jouand@email.com', '{bcrypt}$2a$10$is.uot1BQvaGXdAQd/NSaOWKraONCPdJ7/Ch9puRgNQfR8ya/BRYm'),
('E', 'GOBIN', 'Stéphane', 'Entreprise BoBoPizza', '44000', 'Nantes', 'stephane.gobin@email.com', '{bcrypt}$2a$10$is.uot1BQvaGXdAQd/NSaOWKraONCPdJ7/Ch9puRgNQfR8ya/BRYm'),
('C', 'DUPONT', 'Martin',  '2b rue Michael FARADAY', '44300', 'SAINT-HERBLAIN', 'martin.dupont@email.com', '{bcrypt}$2a$10$is.uot1BQvaGXdAQd/NSaOWKraONCPdJ7/Ch9puRgNQfR8ya/BRYm'),
('C', 'DOE', 'John',  '6 rue de la Liberté', '44000', 'NANTES', 'john.doe@email.com', '{bcrypt}$2a$10$is.uot1BQvaGXdAQd/NSaOWKraONCPdJ7/Ch9puRgNQfR8ya/BRYm');

-- Insertion role_utilisateur
INSERT INTO ROLE_UTILISATEUR (UTILISATEUR_id_utilisateur, ROLE_id_role) VALUES
(1, 2),
(2, 3),
(3, 2),
(4, 3),
(5, 4),
(6, 1),
(7, 1);

-- Insertion commande & details commande associes
INSERT INTO commande (UTILISATEUR_id_client, date_heure_creation, ETAT_id_etat, UTILISATEUR_id_preparateur, UTILISATEUR_id_livreur, date_heure_livraison, livraison, prix_total, est_paye) VALUES
(6, '2024-10-05 11:30:00', 1, 1, 2, '2024-10-05 14:30:00', 0, 28, 0),
(7, '2024-10-05 11:40:00', 1, 1, 2, '2024-10-05 12:30:00', 0, 39, 0);

INSERT INTO detail_commande (quantite, COMMANDE_id_commande, PRODUIT_id_produit, PRODUIT_nom, PRODUIT_description, PRODUIT_image_url, PRODUIT_prix, TYPE_PRODUIT_id_type_produit) VALUES
(1, 1, 2, 'Pepperoni', 'Pizza avec sauce tomate, mozzarella et pepperoni', '/images/pizza-pepperoni.jpg', 20.00, 1),
(2, 1, 4, 'Coca-Cola', 'Boisson gazeuse', '/images/cocacola-33cl.webp', 4.00, 2),
(1, 2, 1, 'Margherita', 'Pizza avec sauce tomate, mozzarella et basilic', '/images/pizza-margherita.jpg', 14.00, 1),
(1, 2, 3, 'Quattro Formaggi', 'Pizza avec sauce tomate et quatre fromages', '/images/pizza-4-fromages.jpg', 18.00, 1),
(2, 2, 6, 'Eau Minérale Badoit', 'Eau minérale naturelle', '/images/badoit-33cl.jpg', 3.50, 2);