package fr.eni.Pizza.app.bll.MySQL;

import fr.eni.Pizza.app.bo.TypeProduit;
import fr.eni.Pizza.app.dal.DAOTypeProduit;
import fr.eni.Pizza.app.dal.MySQL.DAOTypeProduitMySQL;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Profile("MySQL")
@Service
public class TypeProduitManager implements fr.eni.Pizza.app.bll.TypeProduitManager {

   private DAOTypeProduit daoTypeProduit;

   public TypeProduitManager(DAOTypeProduit daoTypeProduit) {
       this.daoTypeProduit = daoTypeProduit;
   }

    /**
     * Appelle la DAL par l'appel à {@link DAOTypeProduitMySQL#deleteTypeProduitById(Long)}
     *
     * @param id_type_produit : Long, identifiant de l'objet {@link TypeProduit}; l'{@code id_type_produit} doit correspondre à une "id_type_produit" présente en table "type_produit" de la BDD "db_bobopizza"
     */
    @Override
    public void deleteTypeProduitById(Long id_type_produit) {

        daoTypeProduit.deleteTypeProduitById(id_type_produit);
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

    /**
     * Appelle la DAL par {@link DAOTypeProduitMySQL#saveTypeProduit(TypeProduit)}
     *
     * @param typeProduit : {@link TypeProduit}; si son {@link TypeProduit#getId()} est non {@code null} et présent dans la table "type_produit" de la BDD "db_bobopizza", alors mise à jour ; sinon insertion d'une nouvelle entrée en base de donnée
     */
    @Override
    public void saveTypeProduit(TypeProduit typeProduit) {
        daoTypeProduit.saveTypeProduit(typeProduit);
    }
}
