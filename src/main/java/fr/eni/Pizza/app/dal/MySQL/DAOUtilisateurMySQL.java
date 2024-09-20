package fr.eni.Pizza.app.dal.MySQL;

import fr.eni.Pizza.app.bo.*;
import fr.eni.Pizza.app.bo.Utilisateur;
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
public class DAOUtilisateurMySQL implements fr.eni.Pizza.app.dal.DAOUtilisateur {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public DAOUtilisateurMySQL(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
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
            user.setId_commande_en_cours(rs.getLong("id_commande_en_cours") == 0?  null : rs.getLong("id_commande_en_cours"));

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

    /**
     *Retourne la liste de l'ensemble des données présentes dans table "utilisateur" de la BDD "db_bobopizza" ayant un "classe" égal à {@code c}
     *
     * @param c : char, classe du type d'objet {@link Utilisateur}; le {@code c} doit correspondre à une "classe" présente en table "utilisateur" de la BDD "db_bobopizza"
     *          ATTENTION - c doit valoir 'C' pour Client ou 'E' pour Employe
     * @return une liste de d'objets {@link Utilisateur} triés par Role puis par Ordre alphabétique sur la base du {@link Utilisateur#getRole()} et de {@link Utilisateur#getNom()} ou {@code null} en cas de {@code c} non valide
     */
    @Override
    public List<Utilisateur> findAllUtilisateursByClass(char c) {

        if (c != 'E' && c != 'C') {
            return null;
        }

        String classe = ""+c;

        String sql = "SELECT * FROM utilisateur\n" +
                "INNER JOIN role_utilisateur ON UTILISATEUR_id_utilisateur = id_utilisateur\n" +
                "INNER JOIN role ON ROLE_id_role = id_role\n" +
                "WHERE classe = ?";

        List<Utilisateur> utilisateurs = jdbcTemplate.query(sql, UTILISATEUR_ROW_MAPPER, classe);

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

    /**
     *Retourne la liste de l'ensemble des données présentes dans table "utilisateur" de la BDD "db_bobopizza" ayant un "libelle" issu de la table role égal à {@code libelleRole}
     *
     * @param libelleRole : String, libelle du type d'objet {@link Utilisateur#getRole()} ; le {@code libelleRole} doit correspondre à un "libelle" présent en table "role" de la BDD "db_bobopizza"
     *          ATTENTION - libelleRole doit correspondre soit à "CLIENT", "PIZZAIOLO", "LIVREUR" ou "GERANT"
     * @return une liste de d'objets {@link Utilisateur} triés par Ordre alphabétique sur la base du {@link Utilisateur#getNom()} ou {@code null} en cas de {@code c} non valide
     */
    @Override
    public List<Utilisateur> findAllUtilisateursByRole(String libelleRole) {
        String sql = "SELECT libelle FROM role";

        List<String> libellesRoles = jdbcTemplate.queryForList(sql, String.class);

        boolean libelleRoleInBDD = false;

        for (String libelle : libellesRoles) {
            if (libelle.equals(libelleRole.trim().toUpperCase())) {
                libelleRoleInBDD = true;
                break;
            }
        }

        if (!libelleRoleInBDD) {
            return null;
        }

        sql = "SELECT * FROM utilisateur\n" +
                "INNER JOIN role_utilisateur ON UTILISATEUR_id_utilisateur = id_utilisateur\n" +
                "INNER JOIN role ON ROLE_id_role = id_role\n" +
                "WHERE libelle = ?";

        List<Utilisateur> utilisateurs = jdbcTemplate.query(sql, UTILISATEUR_ROW_MAPPER, libelleRole);

        List<Utilisateur> sortedUtilisateurs = utilisateurs.stream()
                .sorted(Comparator.comparing(Utilisateur::getNom))
                .collect(Collectors.toList());

        return sortedUtilisateurs;
    }

    /**
     * Retourne la data {@link Utilisateur} correspondant à l'{@code id_utilisateur} passé en paramètre présent en table "utilisateur" de la BDD "db_bobopizza"
     *
     * @param id_utilisateur : Long, identifiant de l'objet {@link Utilisateur}; l'{@code id_utilisateur} doit correspondre à une "id_utilisateur" présente en table "utilisateur" de la BDD "db_bobopizza"
     *
     * @return l'objet {@link Utilisateur} ou {@code null} en cas d'{@code id_utilisateur} non valide
     */
    @Override
    public Utilisateur findUtilisateurById(Long id_utilisateur) {

        if (!idUtilisateurExist(id_utilisateur)) {
            return null;
        }

        String sql = "SELECT * FROM utilisateur\n" +
                "INNER JOIN role_utilisateur ON UTILISATEUR_id_utilisateur = id_utilisateur\n" +
                "INNER JOIN role ON ROLE_id_role = id_role " +
                "WHERE id_utilisateur = ?";

        List<Utilisateur> utilisateurs = jdbcTemplate.query(sql, UTILISATEUR_ROW_MAPPER, id_utilisateur);

        if(utilisateurs.isEmpty()) {
            return null;
        }

        return utilisateurs.get(0);
    }

    @Override
    public Utilisateur findUtilisateurByEmail(String email) {
        String sql = "SELECT * FROM utilisateur\n" +
                "INNER JOIN role_utilisateur ON UTILISATEUR_id_utilisateur = id_utilisateur\n" +
                "INNER JOIN role ON ROLE_id_role = id_role\n" +
                "WHERE email = ? ";

        List<Utilisateur> users = jdbcTemplate.query(sql, UTILISATEUR_ROW_MAPPER, email);

        //si on ne trouve aucun member avec cet id alors on retourne null
        if (users.isEmpty()) {
            return null;
        }

        return users.get(0);
    }

    @Override
    public boolean idUtilisateurExist(Long id_utilisateur) {
        String sql = "SELECT COUNT(*)\n" +
                "FROM utilisateur\n" +
                "WHERE id_utilisateur = ?";

        Long count = jdbcTemplate.queryForObject(sql, Long.class, id_utilisateur);

        if (count == null || count == 0) {
            System.out.println("id_utilisateur inexistant");
            return false;
        }
        return true;
    }

    @Override
    public Long obtainIDFromLastCreatedUtilisateur() {
        return jdbcTemplate.queryForObject("SELECT MAX(id_utilisateur) FROM utilisateur", Long.class);
    }

    @Override
    public void saveUtilisateur(Utilisateur utilisateur) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idUtilisateur", utilisateur.getId());
        params.addValue("nom", utilisateur.getNom());
        params.addValue("prenom", utilisateur.getPrenom());
        params.addValue("rue", utilisateur.getRue());
        params.addValue("codePostal", utilisateur.getCodePostal());
        params.addValue("ville", utilisateur.getVille());
        params.addValue("email", utilisateur.getEmail());
        params.addValue("password", utilisateur.getPassword());
        params.addValue("idRole", utilisateur.getRole().getId());
        params.addValue("idCommandeEnCours", utilisateur.getId_commande_en_cours());
        String c = "";
        if (utilisateur instanceof Client){
            c = "C";
        }
        if (utilisateur instanceof Employe){
            c = "E";
        }
        params.addValue("classe", c);

        String sql;
        if (utilisateur.getId() != null && findUtilisateurById(utilisateur.getId()) != null) {
            sql = "UPDATE utilisateur SET id_utilisateur= :idUtilisateur, classe= :classe, nom = :nom, prenom = :prenom, rue = :rue, code_postal = :codePostal, ville = :ville, email = :email, mot_de_passe= :password, id_commande_en_cours= :idCommandeEnCours WHERE id_utilisateur = :idUtilisateur";
            namedParameterJdbcTemplate.update(sql, params);
            sql = "UPDATE role_utilisateur SET ROLE_id_role = :idRole WHERE UTILISATEUR_id_utilisateur = :idUtilisateur";
            namedParameterJdbcTemplate.update(sql, params);
            System.out.println("Utilisateur " + utilisateur.getNom() + " " + utilisateur.getPrenom() + " mis à jour en table utilisateur de la BDD db_bobopizza");
        } else {
            sql = "INSERT INTO utilisateur (classe, nom, prenom, rue, code_postal, ville, email, mot_de_passe) VALUES (:classe, :nom, :prenom, :rue, :codePostal, :ville, :email, :password)";
            namedParameterJdbcTemplate.update(sql, params);
            params.addValue("idUtilisateur", obtainIDFromLastCreatedUtilisateur());
            sql = "INSERT INTO role_utilisateur (UTILISATEUR_id_utilisateur, ROLE_id_role) VALUES (:idUtilisateur, :idRole)";
            namedParameterJdbcTemplate.update(sql, params);
            System.out.println("Utilisateur " + utilisateur.getNom() + " " + utilisateur.getPrenom() + " ajouté en table utilisateur de la BDD db_bobopizza");
        }
    }
}
