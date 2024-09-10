package fr.eni.Pizza.app.bll;

import fr.eni.Pizza.app.bo.TypeProduit;

import java.util.List;

public interface ITypeProduitManager {

    List<TypeProduit> getAllTypeProduits();

    TypeProduit getTypeProduitById (Long id_type_produit);

}
