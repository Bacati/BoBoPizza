package fr.eni.Pizza.app.bo;

public final class Etat {


    private Long id;
    //Libelles autorises : PANIER, CREEE, EN PREPARATION, PREPAREE, EN LIVRAISON, LIVREE, PAYEE
    private String libelle;

    public Etat() {
        super();
    }

    public Etat(Long id, String libelle) {
        this();
        setId(id);
        setLibelle(libelle);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "Etat{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
