package fr.eni.Pizza.app.bo;

import java.time.LocalDateTime;
import java.util.List;

public final class Commande {

    private Long id;
    private Utilisateur client;
    private List<Produit> produits;
    private LocalDateTime dateHeureCreation;
    private Etat etat;
    private Utilisateur preparateur;
    private LocalDateTime dateHeurePreparation;
    private Utilisateur livreur;
    private LocalDateTime dateHeureLivraison;
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
        setDateHeureCreation(LocalDateTime.now());
        setEtat(new Etat(1L, "PANIER"));
        setPreparateur(null);
        setDateHeurePreparation(null);
        setLivreur(null);
        setDateHeureLivraison(null);
        setEstLivree(false);
        setEstPayee(false);
        calculatePrixTotal();
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

    public LocalDateTime getDateHeureCreation() {
        return dateHeureCreation;
    }

    public void setDateHeureCreation(LocalDateTime dateHeureCreation) {
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

    public LocalDateTime getDateHeurePreparation() {
        return dateHeurePreparation;
    }

    public void setDateHeurePreparation(LocalDateTime dateHeurePreparation) {
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

    public LocalDateTime getDateHeureLivraison() {
        return dateHeureLivraison;
    }

    public void setDateHeureLivraison(LocalDateTime dateHeureLivraison) {
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

    public void setPrixTotal(double prixTotal){
        this.prixTotal = prixTotal;
    }

    public void calculatePrixTotal() {
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
