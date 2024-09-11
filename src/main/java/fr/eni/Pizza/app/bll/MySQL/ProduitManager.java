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

    private final DAOProduitMySQL dAOProduitMySQL;
    private IDAOProduit daoProduit;

    public ProduitManager(IDAOProduit daoProduit, DAOProduitMySQL dAOProduitMySQL) {
        this.daoProduit = daoProduit;
        this.dAOProduitMySQL = dAOProduitMySQL;
    }

    /**
     *Appelle la DAL au niveau de {@link DAOProduitMySQL#deleteProduitById(Long)}
     *
     * @param id_produit: Long, identifiant de l'objet {@link fr.eni.Pizza.app.bo.Produit}; l'{@code id_produit} doit correspondre à une "id_produit" présente en table "produit" de la BDD "db_bobopizza"
     */
    @Override
    public void deleteProduitById(Long id_produit) {
        daoProduit.deleteProduitById(id_produit);
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
     * @param id_type_produit : Long, identifiant du type d'objet {@link fr.eni.Pizza.app.bo.TypeProduit}; l'{@code id_type_produit} doit correspondre à une "id_type_produit" présente en table "type_produit" de la BDD "db_bobopizza"
     * @return le résultat de {@link DAOProduitMySQL#findAllProduitsByIdTypeProduit(Long)}
     */
    @Override
    public List<Produit> getAllProduitsByIdTypeProduit(Long id_type_produit) {
        return daoProduit.findAllProduitsByIdTypeProduit(id_type_produit);
    }

    /**
     * Appelle la DAL
     *
     * @param id_produit : Long, identifiant de l'objet {@link fr.eni.Pizza.app.bo.Produit}; l'{@code id_produit} doit correspondre à une "id_produit" présente en table "produit" de la BDD "db_bobopizza"
     * @return le résultat de {@link DAOProduitMySQL#findProduitById(Long)}
     */
    @Override
    public Produit getProduitById(Long id_produit) {
        return daoProduit.findProduitById(id_produit);
    }

    /**
     * Appelle la DAL au niveau de {@link DAOProduitMySQL#saveProduit(Produit)}
     *
     * @param produit : {@link Produit}; si son {@link Produit#id} est non {@code null} et présent dans la table "produit" de la BDD "db_bobopizza", alors mise à jour ; sinon insertion d'une nouvelle entrée en base de donnée
     */
    @Override
    public void saveProduit(Produit produit) {
        daoProduit.saveProduit(produit);
    }

}
