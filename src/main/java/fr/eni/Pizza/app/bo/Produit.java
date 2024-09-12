package fr.eni.Pizza.app.bo;

public final class Produit {
    private Long id;
    private String nom;
    private TypeProduit type;
    private String urlImage;
    private String description;
    private double prixUnitaire;
    private Integer quantite;
    private double prixTotal;

    public Produit() {
        super();
    }

    public Produit(Long id, String nom, TypeProduit type, String urlImage, String description, double prixUnitaire, Integer quantite) {
        this();
        setId(id);
        setNom(nom);
        setType(type);
        setUrlImage(urlImage);
        setDescription(description);
        setPrixUnitaire(prixUnitaire);
        setQuantite(quantite);
        setPrixTotal(quantite);
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

    public TypeProduit getType() {
        return type;
    }

    public void setType(TypeProduit type) {
        this.type = type;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {

        if (quantite <= 0){
            quantite = 1;
        }

        this.quantite = quantite;
        this.setPrixTotal(quantite);
    }

    public double getPrixTotal() {
        return prixTotal;
    }

    private void setPrixTotal(double quantite) {
        if (quantite <= 0){
            prixTotal = 0;
            return;
        }

        prixTotal = quantite * getPrixUnitaire();
    }

    @Override
    public String toString() {
        return "Produit{" + "id=" + id + ", nom='" + nom + ", type=" + type + ", urlImage='" + urlImage + ", description='" + description
                + ", prixUnitaire=" + prixUnitaire + ", quantite=" + quantite + ", prixTotal=" + prixTotal + "}";
    }
}
