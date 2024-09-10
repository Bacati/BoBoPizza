package fr.eni.Pizza.app.bo;

import java.time.LocalDate;
import java.util.List;

public final class Commande {

    private Long id;
    private Client client;
    private List<Produit> produits;
    private Utilisateur livreur;
    private Etat etat;
    private LocalDate dateHeureLivraison;
    private boolean estLivree;
    private boolean estPayee;
    private double prixTotal;

    public Commande() {
        super();
    }

    public Commande(Long id, Client client, List<Produit> produits, Utilisateur livreur) {
        this();
        setId(id);
        setClient(client);
        setProduits(produits);
        setLivreur(livreur);
        setEtat(new Etat(1L, "CREEE"));
        setDateHeureLivraison(LocalDate.now());
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

    public Utilisateur getLivreur() {
        return livreur;
    }

    public void setLivreur(Utilisateur livreur) {
        this.livreur = livreur;
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
                ", utilisateur=" + livreur +
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
