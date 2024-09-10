package fr.eni.Pizza.app.dal;

import fr.eni.Pizza.app.bo.Produit;

import java.util.List;

public interface IDAOProduit {

    List<Produit> findAllProduits();

    List<Produit> findAllProduitsByIdTypeProduit(Long id_type_produit);

    Produit findProduitById (Long id);
}
