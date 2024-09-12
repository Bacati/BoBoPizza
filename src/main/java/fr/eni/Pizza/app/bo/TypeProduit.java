package fr.eni.Pizza.app.bo;

public final class TypeProduit {

    private Long id;
    //Libelles autorises : PIZZA,  BOISSON
    private String libelle;

    public TypeProduit () {
        super();
    }

    public TypeProduit (Long id, String libelle) {
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
        return "TypeProduit{" + "id=" + id + ", libelle='" + libelle + "'}'";
    }
}
