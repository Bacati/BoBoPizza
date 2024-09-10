package fr.eni.Pizza.app.bll;

import fr.eni.Pizza.app.bo.Produit;
import fr.eni.Pizza.app.bo.TypeProduit;

import java.util.List;

public interface IProduitManager {

    List<Produit> getAllProduits();

    List<Produit> getAllProduitsByIdTypeProduit(Long id_type_produit);

    Produit getProduitById(Long id_produit);
}

