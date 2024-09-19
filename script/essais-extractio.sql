SELECT * FROM type_produit;
SELECT id_type_produit FROM type_produit;
SELECT * FROM type_produit WHERE id_type_produit = 1;
SELECT * FROM produit INNER JOIN type_produit ON TYPE_PRODUIT_id_type_produit = id_type_produit;
SELECT * FROM produit INNER JOIN type_produit ON TYPE_PRODUIT_id_type_produit = id_type_produit WHERE id_type_produit = 1;
SELECT * FROM produit INNER JOIN type_produit ON TYPE_PRODUIT_id_type_produit = id_type_produit WHERE id_produit = 3;

DELETE FROM produit WHERE id_produit = 6;

SELECT * FROM detail_commande
INNER JOIN commande ON COMMANDE_id_commande = id_commande
INNER JOIN produit ON PRODUIT_id_produit = id_produit
INNER JOIN type_produit ON TYPE_PRODUIT_id_type_produit = id_type_produit
INNER JOIN client ON CLIENT_id_client = id_client
INNER JOIN etat ON ETAT_id_etat = id_etat
INNER JOIN utilisateur ON utilisateur.id_utilisateur = commande.UTILISATEUR_id_utilisateur
INNER JOIN role_utilisateur ON role_utilisateur.UTILISATEUR_id_utilisateur = utilisateur.id_utilisateur
INNER JOIN role ON role.id_role = role_utilisateur.ROLE_id_role;

SELECT * 
FROM commande
INNER JOIN
	detail_commande
	ON commande.id_commande = detail_commande.COMMANDE_id_commande
INNER JOIN 
	produit
    ON detail_commande.PRODUIT_id_produit = produit.id_produit
INNER JOIN 
	type_produit
    ON produit.TYPE_PRODUIT_id_type_produit = type_produit.id_type_produit
WHERE commande.id_commande = 1;

SELECT * FROM commande 
INNER JOIN detail_commande 
ON commande.id_commande = detail_commande.COMMANDE_id_commande 
INNER JOIN  
produit 
	ON detail_commande.PRODUIT_id_produit = produit.id_produit 
INNER JOIN  
type_produit 
	ON produit.TYPE_PRODUIT_id_type_produit = type_produit.id_type_produit 
WHERE commande.id_commande = ?;

SELECT
    co.id_commande AS co_id_commande,
    co.UTILISATEUR_id_client AS co_UTILISATEUR_id_client,
    co.date_heure_creation AS co_date_heure_creation,
    co.ETAT_id_etat AS co_ETAT_id_etat,
    co.UTILISATEUR_id_preparateur AS co_UTILISATEUR_id_preparateur,
    co.date_heure_preparation AS co_date_heure_preparation,
    co.UTILISATEUR_id_livreur AS co_UTILISATEUR_id_livreur,
    co.date_heure_livraison AS co_date_heure_livraison,
    co.livraison AS co_livraison,
    co.prix_total AS co_prix_total,
    co.est_paye AS co_est_paye,
    ul_cl.id_utilisateur AS cl_id_client,
    ul_cl.classe AS cl_classe,
    ul_cl.nom AS cl_nom,
    ul_cl.prenom AS cl_prenom,
    ul_cl.rue AS cl_rue,
    ul_cl.code_postal AS cl_code_postal,
    ul_cl.ville AS cl_ville,
    ul_cl.email AS cl_email,
    ul_cl.mot_de_passe AS cl_mot_de_passe,
    ul_cl.id_commande_en_cours AS cl_id_commande_en_cours,
    ru_client.UTILISATEUR_id_utilisateur AS ru_client_UTILISATEUR_id_utilisateur,
    ru_client.ROLE_id_role AS ru_client_ROLE_id_role,
    ro_client.id_role AS ro_client_id_role,
    ro_client.libelle AS ro_client_libelle,
    et.id_etat AS et_id_etat,
    et.libelle AS et_libelle,
    ul_pr.id_utilisateur AS pr_id_preparateur,
    ul_pr.classe AS pr_classe,
    ul_pr.nom AS pr_nom,
    ul_pr.prenom AS pr_prenom,
    ul_pr.rue AS pr_rue,
    ul_pr.code_postal AS pr_code_postal,
    ul_pr.ville AS pr_ville,
    ul_pr.email AS pr_email,
    ul_pr.mot_de_passe AS pr_mot_de_passe,
    ul_pr.id_commande_en_cours AS pr_id_commande_en_cours,
    ru_preparateur.UTILISATEUR_id_utilisateur AS ru_preparateur_UTILISATEUR_id_utilisateur,
    ru_preparateur.ROLE_id_role AS ru_preparateur_ROLE_id_role,
    ro_preparateur.id_role AS ro_preparateur_id_role,
    ro_preparateur.libelle AS ro_preparateur_libelle,
    ul_lr.id_utilisateur AS lr_id_preparateur,
    ul_lr.classe AS lr_classe,
    ul_lr.nom AS lr_nom,
    ul_lr.prenom AS lr_prenom,
    ul_lr.rue AS lr_rue,
    ul_lr.code_postal AS lr_code_postal,
    ul_lr.ville AS lr_ville,
    ul_lr.email AS lr_email,
    ul_lr.mot_de_passe AS lr_mot_de_passe,
    ul_lr.id_commande_en_cours AS lr_id_commande_en_cours,
    ru_livreur.UTILISATEUR_id_utilisateur AS ru_livreur_UTILISATEUR_id_utilisateur,
    ru_livreur.ROLE_id_role AS ru_livreur_ROLE_id_role,
    ro_livreur.id_role AS ro_livreur_id_role,
    ro_livreur.libelle AS ro_livreur_libelle
FROM
    commande co
INNER JOIN
    utilisateur ul_cl ON co.UTILISATEUR_id_client = ul_cl.id_utilisateur
INNER JOIN
    role_utilisateur ru_client ON ru_client.UTILISATEUR_id_utilisateur = ul_cl.id_utilisateur
INNER JOIN
    role ro_client ON ru_client.ROLE_id_role = ro_client.id_role
INNER JOIN
    etat et ON co.ETAT_id_etat = et.id_etat
INNER JOIN
    utilisateur ul_pr ON co.UTILISATEUR_id_preparateur = ul_pr.id_utilisateur
INNER JOIN
    role_utilisateur ru_preparateur ON ru_preparateur.UTILISATEUR_id_utilisateur = ul_pr.id_utilisateur
INNER JOIN
    role ro_preparateur ON ru_preparateur.ROLE_id_role = ro_preparateur.id_role
INNER JOIN
    utilisateur ul_lr ON co.UTILISATEUR_id_livreur = ul_lr.id_utilisateur
INNER JOIN
    role_utilisateur ru_livreur ON ru_livreur.UTILISATEUR_id_utilisateur = ul_lr.id_utilisateur
INNER JOIN
    role ro_livreur ON ru_livreur.ROLE_id_role = ro_livreur.id_role
WHERE
    co.id_commande = 1;
    
SELECT * FROM utilisateur
INNER JOIN role_utilisateur ON UTILISATEUR_id_utilisateur = id_utilisateur
INNER JOIN role ON ROLE_id_role = id_role
WHERE classe = 'C';

SELECT email, mot_de_passe, 1 FROM utilisateur WHERE email= 'nicolas.jouand@email.com';
SELECT utilisateur.email, role.libelle
FROM utilisateur 
INNER JOIN role_utilisateur ON utilisateur.id_utilisateur = role_utilisateur.UTILISATEUR_id_utilisateur
INNER JOIN role ON role.id_role = role_utilisateur.ROLE_id_role
WHERE email = 'nicolas.jouand@email.com';