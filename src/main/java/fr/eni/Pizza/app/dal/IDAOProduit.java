package fr.eni.Pizza.app.dal;

import fr.eni.Pizza.app.bo.Produit;
import fr.eni.Pizza.app.bo.TypeProduit;

import java.util.List;

public interface IDAOProduit {

    void deleteProduitById(Long id_produit);

    List<Produit> findAllProduits();

    List<Produit> findAllProduitsByIdTypeProduit(Long id_type_produit);

    Produit findProduitById (Long id);

    void saveProduit(Produit produit);
}
