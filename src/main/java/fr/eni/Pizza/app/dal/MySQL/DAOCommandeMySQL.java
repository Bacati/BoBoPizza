package fr.eni.Pizza.app.dal.MySQL;

import fr.eni.Pizza.app.bo.*;
import fr.eni.Pizza.app.dal.DAOCommande;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Profile("MySQL")
@Repository
public class DAOCommandeMySQL implements DAOCommande {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DAOCommandeMySQL(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    static final RowMapper<Commande> COMMANDE_ROW_MAPPER = new RowMapper<Commande>() {

        @Override
        public Commande mapRow(ResultSet rs, int rowNum) throws SQLException {
            Commande commande = new Commande();
            commande.setId(rs.getLong("co_id_commande"));

            Utilisateur client = new Client();
            client.setId(rs.getLong("cl_id_client"));
            client.setNom(rs.getString("cl_nom"));
            client.setPrenom(rs.getString("cl_prenom"));
            client.setRue(rs.getString("cl_rue"));
            client.setCodePostal(rs.getString("cl_code_postal"));
            client.setVille(rs.getString("cl_ville"));
            client.setEmail(rs.getString("cl_email"));
            client.setPassword(rs.getString("cl_mot_de_passe"));
            Role role = new Role();
            role.setId(rs.getLong("ro_client_id_role"));
            role.setLibelle(rs.getString("ro_client_libelle"));
            client.setRole(role);
            commande.setClient(client);

            //ATTENTION : la BLL qui interrogera cette instance this de DAOCommandeMySQL doit systématiquement gérer l'ajout de la liste de produits à l'objet Commande
            commande.setProduits(null);

            // Convertir DATETIME en LocalDate
            LocalDateTime dateTime = rs.getObject("co_date_heure_creation", LocalDateTime.class);
            if (dateTime != null) {
                commande.setDateHeureCreation(dateTime);
            }

            Etat etat = new Etat();
            etat.setId(rs.getLong("et_id_etat"));
            etat.setLibelle(rs.getString("et_libelle"));
            commande.setEtat(etat);

            Utilisateur preparateur = new Employe();
            preparateur.setId(rs.getLong("pr_id_preparateur"));
            preparateur.setNom(rs.getString("pr_nom"));
            preparateur.setPrenom(rs.getString("pr_prenom"));
            preparateur.setRue(rs.getString("pr_rue"));
            preparateur.setCodePostal(rs.getString("pr_code_postal"));
            preparateur.setVille(rs.getString("pr_ville"));
            preparateur.setEmail(rs.getString("pr_email"));
            preparateur.setPassword(rs.getString("pr_mot_de_passe"));
            role = new Role();
            role.setId(rs.getLong("ro_preparateur_id_role"));
            role.setLibelle(rs.getString("ro_preparateur_libelle"));
            preparateur.setRole(role);
            commande.setPreparateur(preparateur);

            // Convertir DATETIME en LocalDate
            dateTime = rs.getObject("co_date_heure_preparation", LocalDateTime.class);
            if (dateTime != null) {
                commande.setDateHeurePreparation(dateTime);
            }

            Utilisateur livreur = new Employe();
            livreur.setId(rs.getLong("lr_id_preparateur"));
            livreur.setNom(rs.getString("lr_nom"));
            livreur.setPrenom(rs.getString("lr_prenom"));
            livreur.setRue(rs.getString("lr_rue"));
            livreur.setCodePostal(rs.getString("lr_code_postal"));
            livreur.setVille(rs.getString("lr_ville"));
            livreur.setEmail(rs.getString("lr_email"));
            livreur.setPassword(rs.getString("lr_mot_de_passe"));
            role = new Role();
            role.setId(rs.getLong("ro_livreur_id_role"));
            role.setLibelle(rs.getString("ro_livreur_libelle"));
            livreur.setRole(role);
            commande.setLivreur(livreur);

            // Convertir DATETIME en LocalDate
            dateTime = rs.getObject("co_date_heure_livraison", LocalDateTime.class);
            if (dateTime != null) {
                commande.setDateHeureLivraison(dateTime);
            }

            commande.setEstLivree(false);
            commande.setEstPayee(false);

            if (etat.getLibelle().equals("LIVREE") || etat.getLibelle().equals("PAYEE")) {
                commande.setEstLivree(true);
            }

            if (etat.getLibelle().equals("PAYEE")){
                commande.setEstPayee(true);
            }

            commande.setPrixTotal(rs.getDouble("co_prix_total"));

            return commande;
        }
    };

    /**
     * Supprime - si elle existe - la commande correspondant à l'{@code id_commande} passé en paramètre présent en table "commande" ,
     * ainsi que tous les références associées dans la table "detail_commande" de la BDD "db_bobopizza".
     *
     * @param id_commande : Long, identifiant de l'objet {@link Commande}; l'{@code id_commande} doit correspondre à une "id_commande" présente en table "commande" de la BDD "db_bobopizza"
     */
    @Override
    public void deleteCommandeById(Long id_commande) {
        String sql = "SELECT id_commande FROM commande";

        List <Long> ids = jdbcTemplate.queryForList(sql, Long.class);

        if (id_commande <= 0 || id_commande > ids.size()) {
            System.out.println("id_commande incorrect");
            return ;
        }

        sql = "DELETE FROM detail_commande WHERE COMMANDE_id_commande = ?";

        jdbcTemplate.update(sql, id_commande);

        sql = "DELETE FROM commande WHERE id_commande = ?";

        jdbcTemplate.update(sql, id_commande);

        System.out.println("Supression de la commande n°" + id_commande + " de la BDD" );
    }

    /**
     * Retourne la liste d'instances de {@link Commande} construite sur la base de l'ensemble des données présentes dans les tables
     * commande, client, etat, utilisateur, role_utilisateur et role de la BDD "db_bobopizza".
     * ATTENTION - l'attribut {@link Commande#produits} est null
     *
     * @return la liste de toutes les {@link Commande} existantes
     */
    @Override
    public List<Commande> findAllCommandes() {
        String sql = "\n" +
                "SELECT\n" +
                "    co.id_commande AS co_id_commande,\n" +
                "    co.UTILISATEUR_id_client AS co_UTILISATEUR_id_client,\n" +
                "    co.date_heure_creation AS co_date_heure_creation,\n" +
                "    co.ETAT_id_etat AS co_ETAT_id_etat,\n" +
                "    co.UTILISATEUR_id_preparateur AS co_UTILISATEUR_id_preparateur,\n" +
                "    co.date_heure_preparation AS co_date_heure_preparation,\n" +
                "    co.UTILISATEUR_id_livreur AS co_UTILISATEUR_id_livreur,\n" +
                "    co.date_heure_livraison AS co_date_heure_livraison,\n" +
                "    co.livraison AS co_livraison,\n" +
                "    co.prix_total AS co_prix_total,\n" +
                "    co.est_paye AS co_est_paye,\n" +
                "    ul_cl.id_utilisateur AS cl_id_client,\n" +
                "    ul_cl.classe AS cl_classe,\n" +
                "    ul_cl.nom AS cl_nom,\n" +
                "    ul_cl.prenom AS cl_prenom,\n" +
                "    ul_cl.rue AS cl_rue,\n" +
                "    ul_cl.code_postal AS cl_code_postal,\n" +
                "    ul_cl.ville AS cl_ville,\n" +
                "    ul_cl.email AS cl_email,\n" +
                "    ul_cl.mot_de_passe AS cl_mot_de_passe,\n" +
                "    ul_cl.id_commande_en_cours AS cl_id_commande_en_cours,\n" +
                "    ru_client.UTILISATEUR_id_utilisateur AS ru_client_UTILISATEUR_id_utilisateur,\n" +
                "    ru_client.ROLE_id_role AS ru_client_ROLE_id_role,\n" +
                "    ro_client.id_role AS ro_client_id_role,\n" +
                "    ro_client.libelle AS ro_client_libelle,\n" +
                "    et.id_etat AS et_id_etat,\n" +
                "    et.libelle AS et_libelle,\n" +
                "    ul_pr.id_utilisateur AS pr_id_preparateur,\n" +
                "    ul_pr.classe AS pr_classe,\n" +
                "    ul_pr.nom AS pr_nom,\n" +
                "    ul_pr.prenom AS pr_prenom,\n" +
                "    ul_pr.rue AS pr_rue,\n" +
                "    ul_pr.code_postal AS pr_code_postal,\n" +
                "    ul_pr.ville AS pr_ville,\n" +
                "    ul_pr.email AS pr_email,\n" +
                "    ul_pr.mot_de_passe AS pr_mot_de_passe,\n" +
                "    ul_pr.id_commande_en_cours AS pr_id_commande_en_cours,\n" +
                "    ru_preparateur.UTILISATEUR_id_utilisateur AS ru_preparateur_UTILISATEUR_id_utilisateur,\n" +
                "    ru_preparateur.ROLE_id_role AS ru_preparateur_ROLE_id_role,\n" +
                "    ro_preparateur.id_role AS ro_preparateur_id_role,\n" +
                "    ro_preparateur.libelle AS ro_preparateur_libelle,\n" +
                "    ul_lr.id_utilisateur AS lr_id_preparateur,\n" +
                "    ul_lr.classe AS lr_classe,\n" +
                "    ul_lr.nom AS lr_nom,\n" +
                "    ul_lr.prenom AS lr_prenom,\n" +
                "    ul_lr.rue AS lr_rue,\n" +
                "    ul_lr.code_postal AS lr_code_postal,\n" +
                "    ul_lr.ville AS lr_ville,\n" +
                "    ul_lr.email AS lr_email,\n" +
                "    ul_lr.mot_de_passe AS lr_mot_de_passe,\n" +
                "    ul_lr.id_commande_en_cours AS lr_id_commande_en_cours,\n" +
                "    ru_livreur.UTILISATEUR_id_utilisateur AS ru_livreur_UTILISATEUR_id_utilisateur,\n" +
                "    ru_livreur.ROLE_id_role AS ru_livreur_ROLE_id_role,\n" +
                "    ro_livreur.id_role AS ro_livreur_id_role,\n" +
                "    ro_livreur.libelle AS ro_livreur_libelle\n" +
                "FROM\n" +
                "    commande co\n" +
                "INNER JOIN\n" +
                "    utilisateur ul_cl ON co.UTILISATEUR_id_client = ul_cl.id_utilisateur\n" +
                "INNER JOIN\n" +
                "    role_utilisateur ru_client ON ru_client.UTILISATEUR_id_utilisateur = ul_cl.id_utilisateur\n" +
                "INNER JOIN\n" +
                "    role ro_client ON ru_client.ROLE_id_role = ro_client.id_role\n" +
                "INNER JOIN\n" +
                "    etat et ON co.ETAT_id_etat = et.id_etat\n" +
                "INNER JOIN\n" +
                "    utilisateur ul_pr ON co.UTILISATEUR_id_preparateur = ul_pr.id_utilisateur\n" +
                "INNER JOIN\n" +
                "    role_utilisateur ru_preparateur ON ru_preparateur.UTILISATEUR_id_utilisateur = ul_pr.id_utilisateur\n" +
                "INNER JOIN\n" +
                "    role ro_preparateur ON ru_preparateur.ROLE_id_role = ro_preparateur.id_role\n" +
                "INNER JOIN\n" +
                "    utilisateur ul_lr ON co.UTILISATEUR_id_livreur = ul_lr.id_utilisateur\n" +
                "INNER JOIN\n" +
                "    role_utilisateur ru_livreur ON ru_livreur.UTILISATEUR_id_utilisateur = ul_lr.id_utilisateur\n" +
                "INNER JOIN\n" +
                "    role ro_livreur ON ru_livreur.ROLE_id_role = ro_livreur.id_role";
        List<Commande> commandes = jdbcTemplate.query(sql, COMMANDE_ROW_MAPPER);

        List<Commande> sortedCommandes = commandes.stream()
                .sorted(Comparator.comparing(Commande::getDateHeureCreation).reversed())
                .collect(Collectors.toList());

        return sortedCommandes;
    }

    /**
     * Retourne la liste d'instances de {@link Commande} construites sur la base de l'ensemble des données présentes dans les tables
     * commande, client, etat, utilisateur, role_utilisateur et role de la BDD "db_bobopizza"
     * filtrée sur la base de l' {@code id_etat} présent en table "etat" de la même BDD.
     * ATTENTION - l'attribut {@link Commande#produits} est null
     *
     * @return la liste de toutes les {@link Commande} existantes ayant un {@link Etat#id} égal à {code id_etat}
     */
    @Override
    public List<Commande> findAllCommandesByEtat(Long id_etat) {
        String sql = "SELECT id_etat FROM etat";

        List <Long> ids = jdbcTemplate.queryForList(sql, Long.class);

        if (id_etat <= 0 || id_etat > ids.size()) {
            return null;
        }

        List <Commande> commandes = findAllCommandes();

        List<Commande> sortedCommandes = commandes.stream()
                .filter(commande -> commande.getEtat().getId() == id_etat)
                .sorted(Comparator.comparing(Commande::getDateHeureCreation).reversed())
                .collect(Collectors.toList());

        return sortedCommandes;
    }

    /**
     * Retourne l'objet {@link Commande} correspondant à l'{@code id_commande} passé en paramètre présent en table "commande" de la BDD "db_bobopizza"
     * ATTENTION - l'attribut {@link Commande#produits} est null
     * @param id_commande : Long, identifiant de l'objet {@link Commande}; l'{@code id_commande} doit correspondre à une "id_commande" présente en table "commande" de la BDD "db_bobopizza"
     * @return l'objet {@link Commande} ou {@code null} en cas d'{@code id_commande} non valide
     */
    @Override
    public Commande findCommandeById(Long id_commande) {
        String sql = "SELECT id_commande FROM commande";

        List <Long> ids = jdbcTemplate.queryForList(sql, Long.class);

        if (id_commande <= 0 || id_commande > ids.size()) {
            return null;
        }

        sql = "\n" +
                "SELECT\n" +
                "    co.id_commande AS co_id_commande,\n" +
                "    co.UTILISATEUR_id_client AS co_UTILISATEUR_id_client,\n" +
                "    co.date_heure_creation AS co_date_heure_creation,\n" +
                "    co.ETAT_id_etat AS co_ETAT_id_etat,\n" +
                "    co.UTILISATEUR_id_preparateur AS co_UTILISATEUR_id_preparateur,\n" +
                "    co.date_heure_preparation AS co_date_heure_preparation,\n" +
                "    co.UTILISATEUR_id_livreur AS co_UTILISATEUR_id_livreur,\n" +
                "    co.date_heure_livraison AS co_date_heure_livraison,\n" +
                "    co.livraison AS co_livraison,\n" +
                "    co.prix_total AS co_prix_total,\n" +
                "    co.est_paye AS co_est_paye,\n" +
                "    ul_cl.id_utilisateur AS cl_id_client,\n" +
                "    ul_cl.classe AS cl_classe,\n" +
                "    ul_cl.nom AS cl_nom,\n" +
                "    ul_cl.prenom AS cl_prenom,\n" +
                "    ul_cl.rue AS cl_rue,\n" +
                "    ul_cl.code_postal AS cl_code_postal,\n" +
                "    ul_cl.ville AS cl_ville,\n" +
                "    ul_cl.email AS cl_email,\n" +
                "    ul_cl.mot_de_passe AS cl_mot_de_passe,\n" +
                "    ul_cl.id_commande_en_cours AS cl_id_commande_en_cours,\n" +
                "    ru_client.UTILISATEUR_id_utilisateur AS ru_client_UTILISATEUR_id_utilisateur,\n" +
                "    ru_client.ROLE_id_role AS ru_client_ROLE_id_role,\n" +
                "    ro_client.id_role AS ro_client_id_role,\n" +
                "    ro_client.libelle AS ro_client_libelle,\n" +
                "    et.id_etat AS et_id_etat,\n" +
                "    et.libelle AS et_libelle,\n" +
                "    ul_pr.id_utilisateur AS pr_id_preparateur,\n" +
                "    ul_pr.classe AS pr_classe,\n" +
                "    ul_pr.nom AS pr_nom,\n" +
                "    ul_pr.prenom AS pr_prenom,\n" +
                "    ul_pr.rue AS pr_rue,\n" +
                "    ul_pr.code_postal AS pr_code_postal,\n" +
                "    ul_pr.ville AS pr_ville,\n" +
                "    ul_pr.email AS pr_email,\n" +
                "    ul_pr.mot_de_passe AS pr_mot_de_passe,\n" +
                "    ul_pr.id_commande_en_cours AS pr_id_commande_en_cours,\n" +
                "    ru_preparateur.UTILISATEUR_id_utilisateur AS ru_preparateur_UTILISATEUR_id_utilisateur,\n" +
                "    ru_preparateur.ROLE_id_role AS ru_preparateur_ROLE_id_role,\n" +
                "    ro_preparateur.id_role AS ro_preparateur_id_role,\n" +
                "    ro_preparateur.libelle AS ro_preparateur_libelle,\n" +
                "    ul_lr.id_utilisateur AS lr_id_preparateur,\n" +
                "    ul_lr.classe AS lr_classe,\n" +
                "    ul_lr.nom AS lr_nom,\n" +
                "    ul_lr.prenom AS lr_prenom,\n" +
                "    ul_lr.rue AS lr_rue,\n" +
                "    ul_lr.code_postal AS lr_code_postal,\n" +
                "    ul_lr.ville AS lr_ville,\n" +
                "    ul_lr.email AS lr_email,\n" +
                "    ul_lr.mot_de_passe AS lr_mot_de_passe,\n" +
                "    ul_lr.id_commande_en_cours AS lr_id_commande_en_cours,\n" +
                "    ru_livreur.UTILISATEUR_id_utilisateur AS ru_livreur_UTILISATEUR_id_utilisateur,\n" +
                "    ru_livreur.ROLE_id_role AS ru_livreur_ROLE_id_role,\n" +
                "    ro_livreur.id_role AS ro_livreur_id_role,\n" +
                "    ro_livreur.libelle AS ro_livreur_libelle\n" +
                "FROM\n" +
                "    commande co\n" +
                "INNER JOIN\n" +
                "    utilisateur ul_cl ON co.UTILISATEUR_id_client = ul_cl.id_utilisateur\n" +
                "INNER JOIN\n" +
                "    role_utilisateur ru_client ON ru_client.UTILISATEUR_id_utilisateur = ul_cl.id_utilisateur\n" +
                "INNER JOIN\n" +
                "    role ro_client ON ru_client.ROLE_id_role = ro_client.id_role\n" +
                "INNER JOIN\n" +
                "    etat et ON co.ETAT_id_etat = et.id_etat\n" +
                "INNER JOIN\n" +
                "    utilisateur ul_pr ON co.UTILISATEUR_id_preparateur = ul_pr.id_utilisateur\n" +
                "INNER JOIN\n" +
                "    role_utilisateur ru_preparateur ON ru_preparateur.UTILISATEUR_id_utilisateur = ul_pr.id_utilisateur\n" +
                "INNER JOIN\n" +
                "    role ro_preparateur ON ru_preparateur.ROLE_id_role = ro_preparateur.id_role\n" +
                "INNER JOIN\n" +
                "    utilisateur ul_lr ON co.UTILISATEUR_id_livreur = ul_lr.id_utilisateur\n" +
                "INNER JOIN\n" +
                "    role_utilisateur ru_livreur ON ru_livreur.UTILISATEUR_id_utilisateur = ul_lr.id_utilisateur\n" +
                "INNER JOIN\n" +
                "    role ro_livreur ON ru_livreur.ROLE_id_role = ro_livreur.id_role\n" +
                "WHERE\n" +
                "    co.id_commande = ?";

        List <Commande> commandes = jdbcTemplate.query(sql, COMMANDE_ROW_MAPPER, id_commande);

        if(commandes.isEmpty()){
            return null;
        }

        return commandes.get(0);
    }

    @Override
    public Long obtainIDFromLastCreatedCommande() {
        return jdbcTemplate.queryForObject("SELECT MAX(id_commande) FROM commande", Long.class);
    }

    /**
     * Insère une commande en tables "commande" et "detail_commande" de la BDD "db_bobopizza" avec {@code commande}
     *
     * @param commande : {@link Commande} qui va être inserée en nouvelle entrée en BDD
     */
    @Override
    public void saveCommande(Commande commande) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idCommande", commande.getId());
        params.addValue("idClient", commande.getClient().getId());
        params.addValue("dateHeureCreation", commande.getDateHeureCreation());
        params.addValue("idEtat", commande.getEtat().getId());
        params.addValue("idPreparateur", (commande.getPreparateur() != null ? commande.getPreparateur().getId() : null));
        params.addValue("dateHeurePreparation", commande.getDateHeurePreparation());
        params.addValue("idLivreur", (commande.getLivreur() != null ? commande.getLivreur().getId() : null));
        params.addValue("dateHeureLivraison", commande.getDateHeureLivraison());
        params.addValue("estLivree", (commande.getEstLivree() ? 1 : 0));
        params.addValue("prixTotal", commande.getPrixTotal());
        params.addValue("estPayee", (commande.getEstPayee() ? 1 : 0));

        String sql;
        if (commande.getId() != null && findCommandeById(commande.getId()) != null) {
            sql = "UPDATE commande " +
                    "SET UTILISATEUR_id_client = :idClient, date_heure_creation = :dateHeureCreation, ETAT_id_etat = :idEtat, " +
                    "UTILISATEUR_id_preparateur = :idPreparateur, date_heure_preparation = :dateHeurePreparation, UTILISATEUR_id_livreur = :idLivreur, " +
                    "date_heure_livraison = :dateHeureLivraison, livraison = :estLivree, prix_total = :prixTotal, est_paye = :estPayee " +
                    "WHERE id_commande = :idCommande";
            System.out.println("Commande du " + commande.getDateHeureCreation() + " du client " + commande.getClient().getNom() + " " + commande.getClient().getPrenom() + " mis à jour en table commande de la BDD db_bobopizza");
        } else {
            sql = "INSERT INTO commande (UTILISATEUR_id_client, date_heure_creation, ETAT_id_etat, livraison, prix_total, est_paye) \n" +
                    "VALUES(:idClient, :dateHeureCreation, :idEtat, :estLivree, :prixTotal, :estPayee);";

            System.out.println("Commande du " + commande.getDateHeureCreation() + " du client " + commande.getClient().getNom() + " " + commande.getClient().getPrenom() + " ajoutée en table commande de la BDD db_bobopizza");
        }
        namedParameterJdbcTemplate.update(sql, params);

        /**
         List<Produit> produits = commande.getProduits();

         if (!produits.isEmpty()) {
         for (Produit produit : produits) {
         MapSqlParameterSource map = new MapSqlParameterSource();
         map.addValue("quantite", produit.getQuantite());
         map.addValue("idProduit", produit.getId());
         map.addValue("nom", produit.getNom());
         map.addValue("description", produit.getDescription());
         map.addValue("prixUnitaire", produit.getPrixUnitaire());
         map.addValue("imageURL", produit.getUrlImage());
         map.addValue("idTypeProduit", produit.getType().getId());

         sql = "INSERT INTO detail_commande (quantite, COMMANDE_id_commande, PRODUIT_id_produit, PRODUIT_nom, PRODUIT_description, PRODUIT_prix, PRODUIT_image_url, TYPE_PRODUIT_id_type_produit) VALUES\n" +
         "(:quantite, LAST_INSERT_ID(), :idProduit, :nom, :description, :prixUnitaire, :imageURL, :idTypeProduit)";
         namedParameterJdbcTemplate.update(sql, map);
         System.out.println(" - quantité " + produit.getQuantite() + " de " + produit.getNom() + " inséré en table detail_commande de la BDD db_bobopizza");
         }
         }
         **/
    }
}