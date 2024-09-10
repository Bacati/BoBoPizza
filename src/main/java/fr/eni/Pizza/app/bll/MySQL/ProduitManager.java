package fr.eni.Pizza.app.bll.MySQL;

import fr.eni.Pizza.app.bll.IProduitManager;
import fr.eni.Pizza.app.bo.Produit;
import fr.eni.Pizza.app.dal.IDAOProduit;
import fr.eni.Pizza.app.dal.MySQL.DAOProduitMySQL;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Profile("MySQL")
@Service
public class ProduitManager implements IProduitManager {

    private IDAOProduit daoProduit;

    public ProduitManager(IDAOProduit daoProduit) {
        this.daoProduit = daoProduit;
    }

    /**
     * Appelle la DAL
     * 
     * @return le résultat de {@link DAOProduitMySQL#findAllProduits()}
     */
    @Override
    public List<Produit> getAllProduits() {
        return daoProduit.findAllProduits();
    }

    /**
     * Appelle la DAL
     *
     * @return le résultat de {@link DAOProduitMySQL#findAllProduitsByIdTypeProduit(Long)}
     */
    @Override
    public List<Produit> getAllProduitsByIdTypeProduit(Long id_type_produit) {
        return daoProduit.findAllProduitsByIdTypeProduit(id_type_produit);
    }

    /**
     * Appelle la DAL
     *
     * @return le résultat de {@link DAOProduitMySQL#findProduitById(Long)}
     */
    @Override
    public Produit getProduitById(Long id_produit) {
        return daoProduit.findProduitById(id_produit);
    }

}
