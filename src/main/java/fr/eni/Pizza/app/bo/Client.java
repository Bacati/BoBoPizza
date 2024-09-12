package fr.eni.Pizza.app.bo;

public final class Client extends Utilisateur{

    public Client () {
        super();
    }

    public Client(Long id, String nom, String prenom, String rue, String codePostal, String ville, String email, String password, Role role) {
        super(id, nom, prenom, rue, codePostal, ville, email, password, role);
    }
}
