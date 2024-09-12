package fr.eni.Pizza.app.bo;

public abstract class Utilisateur{

    private Long id;
    private String nom;
    private String prenom;
    private String rue;
    private String codePostal;
    private String ville;
    private String email;
    private String password;
    private Role role;
    private Long id_commande_en_cours;

    public Utilisateur() {
        super();
    }

    public Utilisateur(Long id, String nom, String prenom, String rue, String codePostal, String ville, String email, String password, Role role) {
        this();
        setId(id);
        setNom(nom);
        setPrenom(prenom);
        setRue(rue);
        setCodePostal(codePostal);
        setVille(ville);
        setEmail(email);
        setPassword(password);
        setRole(role);
        setId_commande_en_cours(null);
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getId_commande_en_cours() {
        return id_commande_en_cours;
    }

    public void setId_commande_en_cours(Long id_commande_en_cours) {
        this.id_commande_en_cours = id_commande_en_cours;
    }

    @Override
    public final String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", id_commande_en_cours=" + id_commande_en_cours +
                '}';
    }
}
