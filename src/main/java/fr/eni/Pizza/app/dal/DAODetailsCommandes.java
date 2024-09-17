package fr.eni.Pizza.app.dal;

import fr.eni.Pizza.app.bo.Produit;

public interface DAODetailsCommandes {

    void deleteDetailsCommandesOfProductInBasket (Long id_commande, Long id_produit);

    boolean detailsCommandesExist(Long id_commande, Long id_produit);

    Integer findQuantityOfProductInBasket(Long id_commande, Produit produit);

    void saveDetailsCommandes(Long id_commande, Produit produit);

}
