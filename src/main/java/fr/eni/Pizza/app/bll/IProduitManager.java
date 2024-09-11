package fr.eni.Pizza.app.bll;

import fr.eni.Pizza.app.bo.Produit;
import fr.eni.Pizza.app.bo.TypeProduit;

import java.util.List;

public interface IProduitManager {

    void deleteProduitById(Long id_produit);

    List<Produit> getAllProduits();

    List<Produit> getAllProduitsByIdTypeProduit(Long id_type_produit);

    Produit getProduitById(Long id_produit);

    void saveProduit(Produit produit);
}

