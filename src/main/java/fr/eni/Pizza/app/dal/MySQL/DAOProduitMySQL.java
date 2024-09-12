package fr.eni.Pizza.app.dal.MySQL;

import fr.eni.Pizza.app.bo.Produit;
import fr.eni.Pizza.app.bo.TypeProduit;
import fr.eni.Pizza.app.dal.DAOProduit;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Profile("MySQL")
@Repository
public class DAOProduitMySQL implements DAOProduit {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DAOProduitMySQL(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    static final RowMapper<Produit> PRODUIT_FROM_PRODUIT_ROW_MAPPER = new RowMapper<Produit>() {

        @Override
        public Produit mapRow(ResultSet rs, int rowNum) throws SQLException {
            Produit produit = new Produit();
            produit.setId(rs.getLong("id_produit"));
            produit.setNom(rs.getString("nom"));
            TypeProduit typeProduit = new TypeProduit();
            typeProduit.setId(rs.getLong("id_type_produit"));
            typeProduit.setLibelle(rs.getString("libelle"));
            produit.setType(typeProduit);
            produit.setUrlImage(rs.getString("image_url"));
            produit.setDescription(rs.getString("description"));
            produit.setPrixUnitaire(rs.getDouble("prix"));
            produit.setQuantite(1);

            return produit;
        }
    };

    static final RowMapper<Produit> PRODUIT_FROM_DETAIL_COMMANDE_ROW_MAPPER = new RowMapper<Produit>() {

        @Override
        public Produit mapRow(ResultSet rs, int rowNum) throws SQLException {
            Produit produit = new Produit();
            produit.setId(rs.getLong("PRODUIT_id_produit"));
            produit.setNom(rs.getString("PRODUIT_nom"));
            TypeProduit typeProduit = new TypeProduit();
            typeProduit.setId(rs.getLong("id_type_produit"));
            typeProduit.setLibelle(rs.getString("libelle"));
            produit.setType(typeProduit);
            produit.setDescription(rs.getString("PRODUIT_description"));
            produit.setPrixUnitaire(rs.getDouble("PRODUIT_prix"));
            produit.setUrlImage(rs.getString("PRODUIT_image_url"));
            produit.setQuantite(rs.getInt("quantite"));

            return produit;
        }
    };

    /**
     * Supprime -si il existe - le produit correspondant à l'{@code id_produit} passé en paramètre présent en table "produit" de la BDD "db_bobopizza"
     *
     * @param id_produit : Long, identifiant de l'objet {@link Produit}; l'{@code id_produit} doit correspondre à une "id_produit" présente en table "produit" de la BDD "db_bobopizza"
     *
     */
    @Override
    public void deleteProduitById(Long id_produit) {
        String sql = "SELECT id_produit FROM produit";

        List <Long> ids = jdbcTemplate.queryForList(sql, Long.class);

        if (id_produit <= 0 || id_produit > ids.size()) {
            System.out.println("id_produit incorrect : impossible de deleteProduitById(Long id_produit)");
            return ;
        }

        sql = "DELETE FROM produit WHERE id_produit = ?";

        jdbcTemplate.update(sql, id_produit);

        System.out.println("Supression du produit n°" + id_produit + " en table produit de la BDD db_bobopizza");
    }

    /**
     * Retourne la liste de l'ensemble des données présentes dans la table "produit" de la BDD "db_bobopizza".
     *
     * @return une liste de d'objets {@link Produit} triés par ordre alphabétique sur la base du {@link Produit#nom}
     */
    @Override
    public List<Produit> findAllProduits() {
        String sql = "SELECT * FROM produit INNER JOIN type_produit ON TYPE_PRODUIT_id_type_produit = id_type_produit";
        List<Produit> produits = jdbcTemplate.query(sql, PRODUIT_FROM_PRODUIT_ROW_MAPPER);

        List<Produit> sortedProduits = produits.stream()
                .sorted(Comparator.comparing(Produit::getNom))
                .collect(Collectors.toList());

        return sortedProduits;
    }

    /**
     * Retourne la liste de l'ensemble des données présentes dans table "produit" de la BDD "db_bobopizza" ayant un "TYPE_PRODUIT_id_type_produit" égal à {@code id_type_produit}
     *
     * @param id_type_produit : Long, identifiant du type d'objet {@link TypeProduit}; l'{@code id_type_produit} doit correspondre à une "id_type_produit" présente en table "type_produit" de la BDD "db_bobopizza"
     * @return une liste de d'objets {@link Produit} triés par ordre alphabétique sur la base du {@link Produit#nom} ou {@code null} en cas d'{@code id_type_produit} non valide
     */
    @Override
    public List<Produit> findAllProduitsByIdTypeProduit(Long id_type_produit) {
        String sql = "SELECT id_type_produit FROM type_produit";

        List <Long> ids = jdbcTemplate.queryForList(sql, Long.class);

        if (id_type_produit <= 0 || id_type_produit > ids.size()) {
            return null;
        }

        
        sql = "SELECT * FROM produit INNER JOIN type_produit ON TYPE_PRODUIT_id_type_produit = id_type_produit WHERE id_type_produit = ?";
        List<Produit> produits = jdbcTemplate.query(sql, PRODUIT_FROM_PRODUIT_ROW_MAPPER, id_type_produit);

        List<Produit> sortedProduits = produits.stream()
                .sorted(Comparator.comparing(Produit::getNom))
                .collect(Collectors.toList());

        return sortedProduits;
    }

    /**
     * Retourne la liste de l'ensemble des données présentes dans table "details_commandes" de la BDD "db_bobopizza" ayant un "COMMANDE_id_commande" égal à {@code id_commande}
     *
     * @param id_commande : Long, identifiant du type d'objet {@link Commande}; l'{@code id_commande} doit correspondre à une "COMMANDE_id_commande" présente en table "details_commandes" de la BDD "db_bobopizza"
     *
     * @return une liste de d'objets {@link Produit} triés par ordre alphabétique sur la base du {@link Produit#nom} ou {@code null} en cas d'{@code id_commande} non valide
     */
    @Override
    public List<Produit> findAllProduitsByIdCommande(Long id_commande) {
        String sql = "SELECT id_commande FROM commande";

        List <Long> ids = jdbcTemplate.queryForList(sql, Long.class);

        if (id_commande <= 0 || id_commande > ids.size()) {
            return null;
        }

        sql = "SELECT * FROM detail_commande INNER JOIN type_produit ON TYPE_PRODUIT_id_type_produit = id_type_produit WHERE COMMANDE_id_commande = ?";
        List<Produit> produits = jdbcTemplate.query(sql, PRODUIT_FROM_DETAIL_COMMANDE_ROW_MAPPER, id_commande);


        List<Produit> sortedProduits = produits.stream()
                .sorted(Comparator.comparing(Produit::getNom))
                .collect(Collectors.toList());

        return sortedProduits;
    }

    /**
     * Retourne l'objet {@link Produit} correspondant à l'{@code id_produit} passé en paramètre présent en table "produit" de la BDD "db_bobopizza"
     *
     * @param id_produit : Long, identifiant de l'objet {@link Produit}; l'{@code id_produit} doit correspondre à une "id_produit" présente en table "produit" de la BDD "db_bobopizza"
     *
     * @return l'objet {@link Produit} ou {@code null} en cas d'{@code id_produit} non valide
     */
    @Override
    public Produit findProduitById(Long id_produit) {
        String sql = "SELECT id_produit FROM produit";

        List <Long> ids = jdbcTemplate.queryForList(sql, Long.class);

        if (id_produit <= 0 || id_produit > ids.size()) {
            return null;
        }

        sql = "SELECT * FROM produit INNER JOIN type_produit ON TYPE_PRODUIT_id_type_produit = id_type_produit WHERE id_produit = ?";

        return jdbcTemplate.query(sql, PRODUIT_FROM_PRODUIT_ROW_MAPPER, id_produit).get(0);
    }

    /**+
     * Ajoute ou met à jour la table "produit" de la BDD "db_bobopizza" avec {@code produit}
     *
     * @param produit : {@link Produit}; si son {@link Produit#id} est non {@code null} et présent dans la table "produit" de la BDD "db_bobopizza", alors mise à jour ; sinon insertion d'une nouvelle entrée en base de donnée
     */
    @Override
    public void saveProduit(Produit produit) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("myID", produit.getId());
        params.addValue("myNom", produit.getNom());
        params.addValue("myDescription", produit.getDescription());
        params.addValue("myPrixUnitaire", produit.getPrixUnitaire());
        params.addValue("myURLImage", produit.getUrlImage());
        params.addValue("myTypeProduitId", produit.getType().getId());

        String sql;
        if (produit.getId() != null && findProduitById(produit.getId()) != null) {
            sql = "UPDATE PRODUIT SET nom= :myNom, description= :myDescription, prix= :myPrixUnitaire, image_url= :myURLImage, TYPE_PRODUIT_id_type_produit= :myTypeProduitId WHERE id_produit = :myID";
            System.out.println("Produit " + produit.getNom() + " mis à jour en table produit de la BDD db_bobopizza :" + produit);
        } else {
            sql = "INSERT INTO PRODUIT (nom, description, prix, image_url, TYPE_PRODUIT_id_type_produit) VALUES (:myNom, :myDescription, :myPrixUnitaire, :myURLImage, :myTypeProduitId)";
            System.out.println("Produit inséré en table produit de la BDD db_bobopizza :" + produit);
        }
        namedParameterJdbcTemplate.update(sql, params);

    }
}
