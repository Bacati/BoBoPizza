package fr.eni.Pizza.app.bll;

import fr.eni.Pizza.app.bo.Produit;

import java.util.List;

public interface ProduitManager {

    void deleteProduitById(Long id_produit);

    List<Produit> getAllProduits();

    List<Produit> getAllProduitsByIdTypeProduit(Long id_type_produit);

    List<Produit> getAllProduitsByIdCommande(Long id_commande);

    Produit getProduitById(Long id_produit);

    void saveProduit(Produit produit);
}

