package fr.eni.Pizza.app.dal.MySQL;

import fr.eni.Pizza.app.bo.TypeProduit;
import fr.eni.Pizza.app.dal.DAOTypeProduit;
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
public class DAOTypeProduitMySQL implements DAOTypeProduit {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DAOTypeProduitMySQL(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    static final RowMapper<TypeProduit> TYPE_PRODUIT_ROW_MAPPER = new RowMapper<TypeProduit>() {

        @Override
        public TypeProduit mapRow(ResultSet rs, int rowNum) throws SQLException {
            TypeProduit typeProduit = new TypeProduit();
            typeProduit.setId(rs.getLong("id_type_produit"));
            typeProduit.setLibelle(rs.getString("libelle"));

            return typeProduit;
        }

    };

    /**
     * Supprime -si il existe - la donnée {@link TypeProduit} correspondant à l'{@code id_type_produit} passé en paramètre présent en table "type_produit" de la BDD "db_bobopizza"
     *
     * @param id_type_produit : Long, identifiant de l'objet {@link TypeProduit}; l'{@code id_type_produit} doit correspondre à une "id-type_produit" présente en table "type_produit" de la BDD "db_bobopizza"
     *
     */
    @Override
    public void deleteTypeProduitById(Long id_type_produit) {

        if (!idTypeProduitExist(id_type_produit)) {
            return ;
        }

        String sql = "DELETE FROM type_produit WHERE id_type_produit = ?";

        jdbcTemplate.update(sql, id_type_produit);

        System.out.println("Supression du Type Produit n°" + id_type_produit + " en table type_produit de la BDD db_bobopizza");
    }

    /**
     * Retourne la liste de l'ensemble des données présentes dans la table "type_produit" de la BDD "db_bobopizza".
     *
     * @return une liste de d'objets {@link TypeProduit} triés par ordre alphabétique sur la base du {@link TypeProduit#getLibelle()}
     */
    @Override
    public List<TypeProduit> findAllTypeProduits() {
        List<TypeProduit> typeProduits = jdbcTemplate.query("SELECT * FROM type_produit", TYPE_PRODUIT_ROW_MAPPER);

        List<TypeProduit> sortedTypeProduits = typeProduits.stream()
                .sorted(Comparator.comparing(TypeProduit::getLibelle))
                .collect(Collectors.toList());

        return sortedTypeProduits;
    }

    /**
     * Retourne la data {@link TypeProduit} correspondant à l'{@code id_type_produit} passé en paramètre présent en table "type_produit" de la BDD "db_bobopizza"
     *
     * @param id_type_produit : Long, identifiant de l'objet {@link TypeProduit}; l'{@code id_type_produit} doit correspondre à une "id_type_produit" présente en table "type_produit" de la BDD "db_bobopizza"
     *
     * @return l'objet {@link TypeProduit} ou {@code null} en cas d'{@code id_type_produit} non valide
     */
    @Override
    public TypeProduit findTypeProduitById(Long id_type_produit) {

        if (!idTypeProduitExist(id_type_produit)) {
            return null;
        }

        String sql = "SELECT * FROM type_produit WHERE id_type_produit = ?";

        List<TypeProduit> typeproduits = jdbcTemplate.query(sql, TYPE_PRODUIT_ROW_MAPPER, id_type_produit);

        if(typeproduits.isEmpty()) {
            return null;
        }

        return typeproduits.get(0);
    }

    @Override
    public boolean idTypeProduitExist(Long id_type_produit) {
        String sql = "SELECT COUNT(*)\n" +
                "FROM type_produit\n" +
                "WHERE id_type_produit = ?";

        Long count = jdbcTemplate.queryForObject(sql, Long.class, id_type_produit);

        if (count == null || count == 0) {
            System.out.println("id_type_produit inexistant");
            return false;
        }
        return true;
    }

    /**+
     * Ajoute ou met à jour la table "type_produit" de la BDD "db_bobopizza" avec {@code typeProduit}
     *
     * @param typeProduit : {@link TypeProduit}; si son {@link TypeProduit#getId()} est non {@code null} et présent dans la table "type_produit" de la BDD "db_bobopizza", alors mise à jour ; sinon insertion d'une nouvelle entrée en base de donnée
     */
    @Override
    public void saveTypeProduit(TypeProduit typeProduit) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("myID", typeProduit.getId());
        params.addValue("myLibelle", typeProduit.getLibelle());

        String sql;
        if (typeProduit.getId() != null && findTypeProduitById(typeProduit.getId()) != null) {
            sql = "UPDATE TYPE_PRODUIT SET id_type_produit = :myID, libelle = :myLibelle WHERE id_type_produit = :myID";
            System.out.println("TypeProduit n°" + typeProduit.getId() + " mis à jour en table type_produit de la BDD db_bobopizza");
        } else {
            sql = "INSERT INTO TYPE_PRODUIT (id_type_produit, libelle) VALUES (:myID, :myLibelle)";
            System.out.println("TypeProduit inséré en table type_produit de la BDD db_bobopizza :" + typeProduit);
        }
        namedParameterJdbcTemplate.update(sql, params);

    }
}
