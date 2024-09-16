package fr.eni.Pizza.app.bll.MySQL;

import fr.eni.Pizza.app.bo.*;
import fr.eni.Pizza.app.dal.DAOCommande;
import fr.eni.Pizza.app.dal.DAODetailsCommandes;
import fr.eni.Pizza.app.dal.DAOProduit;
import fr.eni.Pizza.app.dal.DAOUtilisateur;
import fr.eni.Pizza.app.dal.MySQL.DAOCommandeMySQL;
import fr.eni.Pizza.app.dal.MySQL.DAOProduitMySQL;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Profile("MySQL")
@Service
public class CommandeManager implements fr.eni.Pizza.app.bll.CommandeManager {

    private DAOCommande daoCommande;
    private DAOProduit daoProduit;
    private DAODetailsCommandes daoDetailsCommandes;
    private DAOUtilisateur daoUtilisateur;

    public CommandeManager(DAOCommande daoCommande, DAOProduit daoProduit, DAODetailsCommandes daoDetailsCommandes, DAOUtilisateur daoUtilisateur) {
        this.daoCommande = daoCommande;
        this.daoProduit = daoProduit;
        this.daoDetailsCommandes = daoDetailsCommandes;
        this.daoUtilisateur = daoUtilisateur;
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
     * @param id_etat : Long, identifiant du type d'objet {@link fr.eni.Pizza.app.bo.Etat} présent dans l'objet {@link Commande}; l'{@code id_etat} doit correspondre à une "id_etat" présente en table "etat" de la BDD "db_bobopizza"
     * @return le résultat de {@link DAOCommandeMySQL#findAllCommandesByEtat(Long)}
     */
    @Override
    public List<Commande> getCommandesByEtat(Long id_etat) {
        List <Commande> commandes = daoCommande.findAllCommandesByEtat(id_etat);

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
    public Long createBasket(Long id_utilisateur, Produit produit) {
        //Créer une nouvelle commande
        Commande commande = new Commande();
        Client client = new Client();
        client.setId(id_utilisateur);
        commande.setClient(client);
        commande.setDateHeureCreation(LocalDateTime.now());
        Etat etat = new Etat();
        etat.setId(1L);
        commande.setEtat(etat);
        Utilisateur preparateur = new Employe();
        preparateur.setId(1L);
        commande.setPreparateur(preparateur);
        commande.setDateHeurePreparation(null);
        Utilisateur livreur = new Employe();
        livreur.setId(2L);
        commande.setLivreur(livreur);
        commande.setDateHeureLivraison(null);
        commande.setEstLivree(false);
        commande.setEstPayee(false);
        commande.setPrixTotal(produit.getPrixTotal());

        //insérer en BDD commande
        daoCommande.saveCommande(commande);

        //récupérer l'id_commande nouvellement créé pour mettre à jour la BDD utilisateur au niveau de id_commande_en_cours
        Long idCommande = daoCommande.obtainIDFromLastCreatedCommande();
        Utilisateur utilisateur = daoUtilisateur.findUtilisateurById(id_utilisateur);
        utilisateur.setId_commande_en_cours(idCommande);
        daoUtilisateur.saveUtilisateur(utilisateur);

        //insérer en table details_commande
        daoDetailsCommandes.saveDetailsCommandes(idCommande, produit);

        // retourner l'id_commande_en_cours
        System.out.println("Commande en état \"PANIER\" id" + idCommande);
     return idCommande;
    }

    @Override
    public boolean productInBasket(Long id_commande_en_cours, long id_produit) {
        return daoDetailsCommandes.detailsCommandesExist(id_commande_en_cours, id_produit);
    }

    @Override
    public void updateBasket(Long id_commande_en_cours, Produit produit) {
        if (getCommandeById(id_commande_en_cours).getEtat().getId() == 1L){
            Commande commande = daoCommande.findCommandeById(id_commande_en_cours);

            if (productInBasket(id_commande_en_cours, produit.getId())) {
                Integer quantite = daoDetailsCommandes.findQuantityOfProductInBasket(id_commande_en_cours, produit);

                commande.setPrixTotal(commande.getPrixTotal() - (quantite*produit.getPrixUnitaire()));
            }

            commande.setPrixTotal(commande.getPrixTotal() + produit.getPrixTotal());
            daoCommande.saveCommande(commande);

            daoDetailsCommandes.saveDetailsCommandes(id_commande_en_cours, produit);

            System.out.println("Commande id " + id_commande_en_cours + " mise à jour avec " + produit.getNom());
        }
    }

    @Override
    public void finishBasket(Long id_commande_en_cours) {
        if (getCommandeById(id_commande_en_cours).getEtat().getId() == 1L){
            Commande commande = daoCommande.findCommandeById(id_commande_en_cours);
            Etat etat = new Etat();
            etat.setId(2L);
            commande.setEtat(etat);
            daoCommande.saveCommande(commande);
            System.out.println("Commande en état \"CREEE\" " + id_commande_en_cours);

            Utilisateur client = daoUtilisateur.findUtilisateurById(commande.getClient().getId());
            client.setId_commande_en_cours(null);
            daoUtilisateur.saveUtilisateur(client);
        }
    }

}
