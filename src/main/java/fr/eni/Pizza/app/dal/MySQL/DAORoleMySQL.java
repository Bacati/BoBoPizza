package fr.eni.Pizza.app.dal.MySQL;

import fr.eni.Pizza.app.bo.Role;
import fr.eni.Pizza.app.bo.TypeProduit;
import fr.eni.Pizza.app.dal.DAORole;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Profile("MySQL")
@Repository
public class DAORoleMySQL implements DAORole {

    private JdbcTemplate jdbcTemplate;

    public DAORoleMySQL(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    static final RowMapper<Role> ROLE_ROW_MAPPER = new RowMapper<Role>() {

        @Override
        public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
            Role role = new Role();
            role.setId(rs.getLong("id_role"));
            role.setLibelle(rs.getString("libelle"));

            return role;
        }
    };

    /**
     * Retourne la liste de l'ensemble des données présentes dans la table "role" de la BDD "db_bobopizza".
     *
     * @return une liste de d'objets {@link Role} triés par ordre d'id sur la base du {@link Role#getId()}
     */
    @Override
    public List<Role> findAllRoles() {
        List<Role> roles = jdbcTemplate.query("SELECT * FROM role", ROLE_ROW_MAPPER );

        List<Role> sortedRoles = roles.stream()
                .sorted(Comparator.comparing(Role::getId))
                .collect(Collectors.toList());

        return sortedRoles;
    }

    /**
     * Retourne la data {@link Role} correspondant à l'{@code id_role} passé en paramètre présent en table "role" de la BDD "db_bobopizza"
     *
     * @param id_role : Long, identifiant de l'objet {@link Role}; l'{@code id_role} doit correspondre à une "id_role" présente en table "role" de la BDD "db_bobopizza"
     *
     * @return l'objet {@link Role} ou {@code null} en cas d'{@code id_role} non valide
     */
    @Override
    public Role findRoleById(Long id_role) {

        if (!idRoleExist(id_role)){
            return null;
        }

        String sql = "SELECT * FROM role WHERE id_role = ?";

        List<Role> roles = jdbcTemplate.query(sql, ROLE_ROW_MAPPER, id_role);

        if(roles.isEmpty()){
            return null;
        }

        return roles.get(0);
    }

    @Override
    public boolean idRoleExist(Long id_role) {
        String sql = "SELECT COUNT(*)\n" +
                "FROM role\n" +
                "WHERE id_role = ?";

        Long count = jdbcTemplate.queryForObject(sql, Long.class, id_role);

        if (count == null || count == 0) {
            System.out.println("id_role inexistant");
            return false;
        }
        return true;
    }
}
