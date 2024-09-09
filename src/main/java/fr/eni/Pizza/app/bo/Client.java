package fr.eni.Pizza.app.bo;

public final class Client extends Personne{
    private String rue;
    private String codePostal;
    private String ville;

    public Client () {
        super();
    }

    public Client(Long id, String nom, String prenom, String rue, String codePostal, String ville) {
        super(id, nom, prenom);
        setRue(rue);
        setCodePostal(codePostal);
        setVille(ville);
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

    @Override
    public String toString() {
        return "Client{" + super.toString() + '\'' +
                "rue='" + rue + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", ville='" + ville + '\'' +
                '}';
    }
}
