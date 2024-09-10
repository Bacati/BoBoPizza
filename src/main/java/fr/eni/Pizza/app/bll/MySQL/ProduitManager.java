package fr.eni.Pizza.app.bll.MySQL;

import fr.eni.Pizza.app.bll.IProduitManager;
import fr.eni.Pizza.app.bo.Produit;
import fr.eni.Pizza.app.bo.TypeProduit;
import fr.eni.Pizza.app.dal.IDAOProduit;
import fr.eni.Pizza.app.dal.IDAOTypeProduit;
import fr.eni.Pizza.app.dal.MySQL.DAOProduitMySQL;
import fr.eni.Pizza.app.dal.MySQL.DAOTypeProduitMySQL;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitManager implements IProduitManager {

    IDAOTypeProduit daoTypeProduit;
    IDAOProduit daoProduit;

    public ProduitManager(IDAOTypeProduit daoTypeProduit, IDAOProduit daoProduit) {
        this.daoTypeProduit = daoTypeProduit;
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

    /**
     * Appelle la DAL
     *
     * @return le résultat de {@link DAOTypeProduitMySQL#findAllTypeProduits()}
     */
    @Override
    public List<TypeProduit> getAllTypeProduits() {
        return daoTypeProduit.findAllTypeProduits();
    }

    /**
     * Appelle la DAL
     *
     * @return le résultat de {@link DAOTypeProduitMySQL#findTypeProduitById(Long)}
     */
    @Override
    public TypeProduit getTypeProduitById(Long id_type_produit) {
        return daoTypeProduit.findTypeProduitById(id_type_produit);
    }
}
