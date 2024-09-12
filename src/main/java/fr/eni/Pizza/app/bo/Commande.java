package fr.eni.Pizza.app.bo;

import java.time.LocalDate;
import java.util.List;

public final class Commande {

    private Long id;
    private Utilisateur client;
    private List<Produit> produits;
    private LocalDate dateHeureCreation;
    private Etat etat;
    private Utilisateur preparateur;
    private LocalDate dateHeurePreparation;
    private Utilisateur livreur;
    private LocalDate dateHeureLivraison;
    private boolean estLivree;
    private boolean estPayee;
    private double prixTotal;

    public Commande() {
        super();
    }

    //TODO mettre à jour
    public Commande(Long id, Utilisateur client, List<Produit> produits, Etat etat) {
        this();
        setId(id);
        setClient(client);
        setProduits(produits);
        setDateHeureCreation(LocalDate.now());
        setEtat(new Etat(1L, "PANIER"));
        setPreparateur(null);
        setDateHeurePreparation(null);
        setLivreur(null);
        setDateHeureLivraison(null);
        setEstLivree(false);
        setEstPayee(false);
        setPrixTotal();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return (Client) client;
    }

    public void setClient(Utilisateur client) {
        if (!(client instanceof Client)) {
            this.client = null;
        }

        this.client = client;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public LocalDate getDateHeureCreation() {
        return dateHeureCreation;
    }

    public void setDateHeureCreation(LocalDate dateHeureCreation) {
        this.dateHeureCreation = dateHeureCreation;
    }

    public Employe getPreparateur() {
        return (Employe) preparateur;
    }

    public void setPreparateur(Utilisateur preparateur) {
        if (!(preparateur instanceof Employe)) {
            this.preparateur = null;
        }
        this.preparateur = preparateur;
    }

    public LocalDate getDateHeurePreparation() {
        return dateHeurePreparation;
    }

    public void setDateHeurePreparation(LocalDate dateHeurePreparation) {
        this.dateHeurePreparation = dateHeurePreparation;
    }

    public Employe getLivreur() {
        return (Employe) livreur;
    }

    public void setLivreur(Utilisateur livreur) {
        if (!(livreur instanceof Employe)) {
            this.livreur = null;
        }
        this.livreur = livreur;
    }

    public LocalDate getDateHeureLivraison() {
        return dateHeureLivraison;
    }

    public void setDateHeureLivraison(LocalDate dateHeureLivraison) {
        this.dateHeureLivraison = dateHeureLivraison;
    }

    public boolean getEstLivree() {
        return estLivree;
    }

    public void setEstLivree(boolean estLivree) {
        this.estLivree = estLivree;
    }

    public boolean getEstPayee() {
        return estPayee;
    }

    public void setEstPayee(boolean estPayee) {
        this.estPayee = estPayee;
    }

    public double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal() {
        prixTotal = 0;

        if(produits == null || produits.isEmpty()){
            return;
        }

        for (Produit p : produits) {
            prixTotal += p.getPrixTotal();
        }
    }

    @Override
    public String toString() {
        String str = "Commande{" +
                "id=" + id +
                ", client=" + client +
                ", dateHeureCreation=" + dateHeureCreation +
                ", etat=" + etat +
                ", preparateur=" + preparateur +
                ", dateHeurePreparation=" + dateHeurePreparation +
                ", livreur=" + livreur +
                ", dateHeureLivraison=" + dateHeureLivraison +
                ", estLivree=" + estLivree +
                ", estPayee=" + estPayee +
                ", prixTotal=" + prixTotal +
                ", produits={\n";

        for (Produit p : produits) {
            str += "   - " + p.toString() + ",\n";
        }

        str += "}\n}";
        return str;
    }
}
