package fr.eni.Pizza.app.dal;

import fr.eni.Pizza.app.bo.TypeProduit;

import java.util.List;

public interface DAOTypeProduit {

    void deleteTypeProduitById(Long id_type_produit);

    List<TypeProduit> findAllTypeProduits();

    TypeProduit findTypeProduitById (Long id_type_produit);

    boolean idTypeProduitExist(Long id_type_produit);

    void saveTypeProduit(TypeProduit typeProduit);
}
