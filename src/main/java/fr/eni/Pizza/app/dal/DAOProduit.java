package fr.eni.Pizza.app.dal;

import fr.eni.Pizza.app.bo.Produit;

import java.util.List;

public interface DAOProduit {

    void deleteProduitById(Long id_produit);

    List<Produit> findAllProduits();

    List<Produit> findAllProduitsByIdTypeProduit(Long id_type_produit);

    List<Produit> findAllProduitsByIdCommande(Long id_commande);

    Produit findProduitById (Long id);

    void saveProduit(Produit produit);
}
