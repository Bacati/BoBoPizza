package fr.eni.Pizza.app.dal;

import fr.eni.Pizza.app.bo.Commande;
import fr.eni.Pizza.app.bo.Produit;

import java.util.List;

public interface DAOCommande {

    void deleteCommandeById(Long id_commande);

    List<Commande> findAllCommandes();

    List<Commande> findAllCommandesByEtat(Long id_etat);

    Commande findCommandeById (Long id_commande);

    boolean idCommandeExist(Long id_commande);

    Long obtainIDFromLastCreatedCommande();

    void saveCommande(Commande commande);
}
