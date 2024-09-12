package fr.eni.Pizza.app.dal;

import fr.eni.Pizza.app.bo.Commande;

import java.util.List;

public interface DAOCommande {

    void deleteCommandeById(Long id_commande);

    List<Commande> findAllCommandes();

    Commande findCommandeById (Long id_commande);

    void saveCommande(Commande commande);
}
