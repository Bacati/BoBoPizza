package fr.eni.Pizza.app.dal.MySQL;

import fr.eni.Pizza.app.bo.*;
import fr.eni.Pizza.app.bo.Utilisateur;
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
public class DAOUtilisateur implements fr.eni.Pizza.app.dal.DAOUtilisateur {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DAOUtilisateur(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    static final RowMapper<Utilisateur> UTILISATEUR_ROW_MAPPER = new RowMapper<Utilisateur>() {

        @Override
        public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
            Utilisateur user = new Employe();

            if (rs.getString("classe").equals("E")){
                user = new Employe();
            }

            if (rs.getString("classe").equals("C")){
                user = new Client();
            }

            user.setId(rs.getLong("id_utilisateur"));
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            user.setRue(rs.getString("rue"));
            user.setCodePostal(rs.getString("code_postal"));
            user.setVille(rs.getString("ville"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("mot_de_passe"));
            Role role = new Role();
            role.setId(rs.getLong("id_role"));
            role.setLibelle(rs.getString("libelle"));
            user.setRole(role);
            user.setId_commande_en_cours(rs.getLong("id_commande_en_cours"));

            return user;
        }
    };

    /**
     * Retourne la liste d'instances de {@link Utilisateur} (qui seront soit des {@link Employe} soit des {@link Client}) construite sur la base de l'ensemble des données présentes dans la table
     * utilisateur, complétées des informations nécessaires des tables "role_utilisateur" et "role" de la BDD "db_bobopizza".
     *
     * @return la liste de tous les {@link Utilisateur} existants
     */
    @Override
    public List<Utilisateur> findAllUtilisateurs() {
        String sql = "SELECT * FROM utilisateur\n" +
                "INNER JOIN role_utilisateur ON UTILISATEUR_id_utilisateur = id_utilisateur\n" +
                "INNER JOIN role ON ROLE_id_role = id_role";

        List<Utilisateur> utilisateurs = jdbcTemplate.query(sql, UTILISATEUR_ROW_MAPPER);

        List<Utilisateur> sortedUtilisateurs = utilisateurs.stream()
                .sorted((u1, u2) -> {
                    int roleComparison = u1.getRole().getLibelle().compareTo(u2.getRole().getLibelle());
                    if (roleComparison != 0) {
                        return roleComparison;
                    } else {
                        return u1.getNom().compareTo(u2.getNom());
                    }
                })
                .collect(Collectors.toList());

        return sortedUtilisateurs;
    }


    @Override
    public List<Utilisateur> findAllUtilisateursByClass(char Class) {
        return List.of();
    }

    @Override
    public Utilisateur findUtilisateurById(Long id_utilisateur) {
        return null;
    }

    @Override
    public Utilisateur findUtilisateurByEmailAndPassword(String email, String password) {
        return null;
    }

    @Override
    public void saveUtilisateur(Utilisateur utilisateur) {

    }
}
