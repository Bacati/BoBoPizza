package fr.eni.Pizza.app.dal;

import fr.eni.Pizza.app.bo.TypeProduit;

import java.util.List;

public interface IDAOTypeProduit {

    List<TypeProduit> findAllTypeProduits();

    TypeProduit findTypeProduitById (Long id_type_produit);
}
