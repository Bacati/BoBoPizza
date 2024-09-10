package fr.eni.Pizza.app.bo;

public final class Role {

    private Long id;
    //Libelles autorises : PIZZAIOLO, GERANT, LIVREUR
    private String libelle;

    public Role () {
        super();
    }

    public Role (Long id, String libelle) {
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
        return "Role{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
