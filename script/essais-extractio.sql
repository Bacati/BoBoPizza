SELECT * FROM type_produit;
SELECT id_type_produit FROM type_produit;
SELECT * FROM type_produit WHERE id_type_produit = 1;
SELECT * FROM produit INNER JOIN type_produit ON TYPE_PRODUIT_id_type_produit = id_type_produit;
SELECT * FROM produit INNER JOIN type_produit ON TYPE_PRODUIT_id_type_produit = id_type_produit WHERE id_type_produit = 1;
SELECT * FROM produit INNER JOIN type_produit ON TYPE_PRODUIT_id_type_produit = id_type_produit WHERE id_produit = 3;