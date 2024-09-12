package fr.eni.Pizza.app.bll.MySQL;

import fr.eni.Pizza.app.bo.Commande;
import fr.eni.Pizza.app.bo.Produit;
import fr.eni.Pizza.app.dal.DAOCommande;
import fr.eni.Pizza.app.dal.DAOProduit;
import fr.eni.Pizza.app.dal.MySQL.DAOCommandeMySQL;
import fr.eni.Pizza.app.dal.MySQL.DAOProduitMySQL;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Profile("MySQL")
@Service
public class CommandeManager implements fr.eni.Pizza.app.bll.CommandeManager {

    private DAOCommande daoCommande;
    private DAOProduit daoProduit;

    public CommandeManager(DAOCommande daoCommande, DAOProduit daoProduit) {

        this.daoCommande = daoCommande;
        this.daoProduit = daoProduit;
    }

    /**
     *Appelle la DAL au niveau de {@link DAOCommandeMySQL#deleteCommandeById(Long)}
     *
     * @param id_commande: Long, identifiant de l'objet {@link Commande}; l'{@code id_commande} doit correspondre à une "id_commande" présente en table "commande" de la BDD "db_bobopizza"
     */
    @Override
    public void deleteCommandeById(Long id_commande) {
        daoCommande.deleteCommandeById(id_commande);
    }

    /**
     * Appelle la DAL
     *
     * @return le résultat de {@link DAOCommandeMySQL#findAllCommandes()} en ayant complété chaque commande avec sa liste de produits récupérée via {@link DAOProduitMySQL#findAllProduitsByIdCommande(Long)}
     */
    @Override
    public List<Commande> getAllCommandes() {

        List <Commande> commandes = daoCommande.findAllCommandes();

        for (Commande commande: commandes) {
            List<Produit> produits = daoProduit.findAllProduitsByIdCommande(commande.getId());
            commande.setProduits(produits);
        }

        return commandes;
    }

    /**
     * Appelle la DAL
     *
     * @param id_commande : Long, identifiant du type d'objet {@link Commande}; l'{@code id_commande} doit correspondre à une "id_commande" présente en table "commande" de la BDD "db_bobopizza"
     * @return le résultat de {@link DAOCommandeMySQL#findCommandeById(Long)}
     */
    @Override
    public Commande getCommandeById(Long id_commande) {
        Commande commande = daoCommande.findCommandeById(id_commande);

        commande.setProduits(daoProduit.findAllProduitsByIdCommande(id_commande));

        return commande;
    }

    /**
     * Appelle la DAL par {@link fr.eni.Pizza.app.dal.MySQL.DAOCommandeMySQL#saveCommande(Commande)}
     *
     * @param commande : {@link Commande} qui va être inserée en nouvelle entrée en BDD
     */

    @Override
    public void saveCommande(Commande commande) {

        daoCommande.saveCommande(commande);
    }

    @Override
    public Long createBasket(Produit produit) {
        return 0L;
    }

    @Override
    public void updateBasket(Produit produit) {

    }
}
