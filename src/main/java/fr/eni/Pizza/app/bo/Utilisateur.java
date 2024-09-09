package fr.eni.Pizza.app.bo;

public final class Utilisateur extends Personne{
    private String email;
    private String password;
    private Role role;

    public Utilisateur() {
        super();
    }

    public Utilisateur(Long id, String nom, String prenom, String email, String password, Role role) {
        super(id, nom, prenom);
        setEmail(email);
        setPassword(password);
        setRole(role);
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

    @Override
    public String toString() {
        return "Utilisateur{" + super.toString() + '\'' +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
