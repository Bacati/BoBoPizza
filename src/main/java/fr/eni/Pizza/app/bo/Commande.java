package fr.eni.Pizza.app.bo;

import java.time.LocalDate;
import java.util.List;

public final class Commande {

    private Long id;
    private Client client;
    private LocalDate dateHeureLivraison;
    private boolean estLivree;
    private boolean estPayee;
    private Etat etat;
    private Utilisateur utilisateur;
    private double prixTotal;

    private List<Produit> produits;

    public Commande() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    @Override
    public String toString() {
        String s = "Commande{" +
                "id=" + id +
                ", client=" + client +
                ", dateHeureLivraison=" + dateHeureLivraison +
                ", estLivree=" + estLivree +
                ", estPaye=" + estPayee +
                ", etat=" + etat +
                ", utilisateur=" + utilisateur +
                ", prixTotal=" + prixTotal +
                ", produits={\n"
                ;

        for (Produit p : produits) {
            s += "   - " + p.toString() + ",\n";
        }

        s = "}";

        return s;
    }
}
