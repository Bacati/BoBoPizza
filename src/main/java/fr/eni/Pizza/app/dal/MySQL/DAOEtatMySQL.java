package fr.eni.Pizza.app.dal.MySQL;

import fr.eni.Pizza.app.bo.Commande;
import fr.eni.Pizza.app.bo.Etat;
import fr.eni.Pizza.app.dal.DAOEtat;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Profile("MySQL")
@Repository
public class DAOEtatMySQL implements DAOEtat {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DAOEtatMySQL(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    static final RowMapper<Etat> ETAT_ROW_MAPPER = new RowMapper<Etat>() {

        @Override
        public Etat mapRow(ResultSet rs, int rowNum) throws SQLException {
            Etat etat = new Etat();
            etat.setId(rs.getLong("id_etat"));
            etat.setLibelle(rs.getString("libelle"));

            return etat;
        }
    };

    /**
     * Retourne la liste de l'ensemble des données présentes dans la table "etat" de la BDD "db_bobopizza".
     *
     * @return une liste de d'objets {@link Etat} triés par ordre d'id sur la base du {@link Etat#id}
     */
    @Override
    public List<Etat> findAllEtats() {
        List<Etat> etats = jdbcTemplate.query("SELECT * FROM etat", ETAT_ROW_MAPPER);

        List<Etat> sortedEtats = etats.stream()
                .sorted(Comparator.comparing(Etat::getId))
                .collect(Collectors.toList());

        return sortedEtats;
    }

    /**
     * Retourne la data {@link Etat} correspondant à l'{@code id_etat} passé en paramètre présent en table "etat" de la BDD "db_bobopizza"
     *
     * @param id_etat : Long, identifiant de l'objet {@link Etat}; l'{@code id_etat} doit correspondre à une "id_etat" présente en table "etat" de la BDD "db_bobopizza"
     *
     * @return l'objet {@link Etat} ou {@code null} en cas d'{@code id_etat} non valide
     */
    @Override
    public Etat findEtatById(Long id_etat) {
        String sql = "SELECT id_etat FROM etat";

        List<Long> ids = jdbcTemplate.queryForList(sql, Long.class);

        if (id_etat <= 0 || id_etat > ids.size()){
            return null;
        }

        sql = "SELECT * FROM etat WHERE id_etat = ?";

        List <Etat> etats = jdbcTemplate.query(sql, ETAT_ROW_MAPPER, id_etat);

        if(etats.isEmpty()){
            return null;
        }

        return etats.get(0);
    }
}
