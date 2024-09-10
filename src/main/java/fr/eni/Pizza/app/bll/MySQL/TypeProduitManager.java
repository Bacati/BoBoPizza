package fr.eni.Pizza.app.bll.MySQL;

import fr.eni.Pizza.app.bll.ITypeProduitManager;
import fr.eni.Pizza.app.bo.TypeProduit;
import fr.eni.Pizza.app.dal.IDAOTypeProduit;
import fr.eni.Pizza.app.dal.MySQL.DAOTypeProduitMySQL;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Profile("MySQL")
@Service
public class TypeProduitManager implements ITypeProduitManager {

   private IDAOTypeProduit daoTypeProduit;

   public TypeProduitManager(IDAOTypeProduit daoTypeProduit) {
       this.daoTypeProduit = daoTypeProduit;
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
