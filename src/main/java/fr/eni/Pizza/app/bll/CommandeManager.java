package fr.eni.Pizza.app.bll;

import fr.eni.Pizza.app.bo.Commande;
import fr.eni.Pizza.app.bo.Produit;

import java.util.List;

public interface CommandeManager {

    void deleteCommandeById(Long id_commande);

    List<Commande> getAllCommandes();

    Commande getCommandeById (Long id_commande);

    void saveCommande(Commande commande);

    Long createBasket(Produit produit);

    void updateBasket(Produit produit);
}
