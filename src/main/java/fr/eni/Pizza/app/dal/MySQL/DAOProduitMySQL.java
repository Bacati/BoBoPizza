package fr.eni.Pizza.app.dal.MySQL;

import fr.eni.Pizza.app.bo.Produit;
import fr.eni.Pizza.app.bo.TypeProduit;
import fr.eni.Pizza.app.dal.IDAOProduit;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Profile("MySQL")
@Repository
public class DAOProduitMySQL implements IDAOProduit {

    private JdbcTemplate jdbcTemplate;

    public DAOProduitMySQL(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    static final RowMapper<Produit> PRODUIT_ROW_MAPPER = new RowMapper<Produit>() {

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

    /**
     * Retourne la liste de l'ensemble des données présentes dans la table "produit" de la BDD "db_bobopizza".
     *
     * @return une liste de d'objets {@link fr.eni.Pizza.app.bo.Produit} triés par ordre alphabétique sur la base du {@link fr.eni.Pizza.app.bo.Produit#nom}
     */
    @Override
    public List<Produit> findAllProduits() {
        String sql = "SELECT * FROM produit INNER JOIN type_produit ON TYPE_PRODUIT_id_type_produit = id_type_produit";
        List<Produit> produits = jdbcTemplate.query(sql, PRODUIT_ROW_MAPPER);

        List<Produit> sortedProduits = produits.stream()
                .sorted((p1, p2) -> p1.getNom().compareTo(p2.getNom()))
                .collect(Collectors.toList());

        return sortedProduits;
    }

    /**
     * Retourne la liste de l'ensemble des données présentes dans table "produit" de la BDD "db_bobopizza" ayant un "TYPE_PRODUIT_id_type_produit" égal à {@code id_type_produit}
     *      *
     *      * @param id_type_produit : Long, identifiant du type d'objet {@link fr.eni.Pizza.app.bo.TypeProduit}; l'{@code id_type_produit} doit correspondre à une "id_type_produit" présente en table "type_produit" de la BDD "db_bobopizza"
     *      * @return une liste de d'objets {@link fr.eni.Pizza.app.bo.Produit} triés par ordre alphabétique sur la base du {@link fr.eni.Pizza.app.bo.Produit#nom} ou {@code null} en cas d'{@code id_type_produit} non valide
     */
    @Override
    public List<Produit> findAllProduitsByIdTypeProduit(Long id_type_produit) {
        String sql = "SELECT id_type_produit FROM type_produit";

        List <Long> ids = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Long.class));

        if (id_type_produit <= 0 || id_type_produit > ids.size()) {
            return null;
        }

        sql = "SELECT * FROM produit INNER JOIN type_produit ON TYPE_PRODUIT_id_type_produit = id_type_produit WHERE id_type_produit = ?";
        List<Produit> produits = jdbcTemplate.query(sql, PRODUIT_ROW_MAPPER, id_type_produit);

        List<Produit> sortedProduits = produits.stream()
                .sorted((p1, p2) -> p1.getNom().compareTo(p2.getNom()))
                .collect(Collectors.toList());

        return sortedProduits;
    }

    /**
     * Retourne l'objet {@link fr.eni.Pizza.app.bo.Produit} correspondant à l'{@code id_produit} passé en paramètre présent en table "produit" de la BDD "db_bobopizza"
     *
     * @param id_produit : Long, identifiant de l'objet {@link fr.eni.Pizza.app.bo.Produit}; l'{@code id_produit} doit correspondre à une "id_produit" présente en table "produit" de la BDD "db_bobopizza"
     * @return l'objet {@link fr.eni.Pizza.app.bo.Produit} ou {@code null} en cas d'{@code id_produit} non valide
     */
    @Override
    public Produit findProduitById(Long id_produit) {
        String sql = "SELECT id_produit FROM produit";

        List <Long> ids = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Long.class));

        if (id_produit <= 0 || id_produit > ids.size()) {
            return null;
        }

        sql = "SELECT * FROM produit INNER JOIN type_produit ON TYPE_PRODUIT_id_type_produit = id_type_produit WHERE id_produit = ?";

        return jdbcTemplate.query(sql, PRODUIT_ROW_MAPPER, id_produit).get(0);
    }
}
