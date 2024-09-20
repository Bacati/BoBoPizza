package fr.eni.Pizza.app.bo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public abstract class Utilisateur{

    private Long id;
    @NotNull(message = "Le nom est obligatoire.")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères.")
    private String nom;
    @NotNull(message = "Le prénom est obligatoire.")
    @Size(min = 2, max = 100, message = "Le prénom doit contenir entre 2 et 100 caractères.")
    private String prenom;
    @NotNull(message = "Le nom de la rue est obligatoire.")
    @Size(min = 2, max = 100, message = "Le nom de la rue doit contenir entre 2 et 100 caractères.")
    private String rue;
    @NotNull(message = "Le code postal est obligatoire.")
    @Pattern(regexp = "\\d{5}", message = "Le code postal doit être composé de 5 chiffres.")
    private String codePostal;
    @NotNull(message = "Le nom de la ville est obligatoire.")
    @Size(min = 2, max = 50, message = "Le nom de la ville doit contenir entre 2 et 50 caractères.")
    private String ville;
    @NotNull(message = "L'adresse email est obligatoire.")
    @Email(message = "L'adresse email doit être valide.")
    private String email;
    @NotNull(message = "Le mot de passe est obligatoire.")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères.")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$",
            message = "Le mot de passe doit contenir au moins une majuscule, une minuscule, et un chiffre."
    )
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
