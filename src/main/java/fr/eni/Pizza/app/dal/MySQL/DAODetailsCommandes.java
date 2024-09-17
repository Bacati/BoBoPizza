package fr.eni.Pizza.app.dal.MySQL;

import fr.eni.Pizza.app.bo.Produit;
import fr.eni.Pizza.app.dal.DAOProduit;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Profile("MySQL")
@Repository
public class DAODetailsCommandes implements fr.eni.Pizza.app.dal.DAODetailsCommandes {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private DAOProduit daoProduit;

    public DAODetailsCommandes (JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, DAOProduit daoProduit) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.daoProduit = daoProduit;
    }

    @Override
    public void deleteDetailsCommandesOfProductInBasket(Long id_commande, Long id_produit) {
        if(detailsCommandesExist(id_commande, id_produit)) {
            String sql ="DELETE FROM detail_commande WHERE COMMANDE_id_commande = ? AND PRODUIT_id_produit = ?";
            jdbcTemplate.update(sql, id_commande, id_produit);
            System.out.println("Supression du produit n°" + id_produit + " de la commande n°" + id_commande + " en table details_commandes de la BDD db_bobopizza.");
        }
    }

    @Override
    public boolean detailsCommandesExist(Long id_commande, Long id_produit) {
        Integer count = null;
        String sql = "SELECT COUNT(*) FROM detail_commande WHERE COMMANDE_id_commande = ? AND PRODUIT_id_produit = ?";
        count = jdbcTemplate.queryForObject(sql, Integer.class, id_commande, id_produit);

        if (count == 1){
            return true;
        }

        return false;
    }

    @Override
    public Integer findQuantityOfProductInBasket(Long id_commande, Produit produit){
        if(detailsCommandesExist(id_commande, produit.getId())){
            return null;
        }

        String sql = "SELECT quantite FROM detail_commande WHERE COMMANDE_id_commande = ? AND PRODUIT_id_produit = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, id_commande, produit.getId());
    }

    @Override
    public void saveDetailsCommandes(Long id_commande, Produit produit) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("quantiteProduit", produit.getQuantite());
        params.addValue("idCommande", id_commande);
        params.addValue("idProduit", produit.getId());
        params.addValue("nomProduit", produit.getNom());
        params.addValue("descriptionProduit", produit.getDescription());
        params.addValue("prixUnitaireProduit", produit.getPrixUnitaire());
        params.addValue("URLImageProduit", produit.getUrlImage());
        params.addValue("typeProduitId", produit.getType().getId());

        String sql;
        if (detailsCommandesExist(id_commande, produit.getId())) {
            sql = "UPDATE detail_commande SET quantite =:quantiteProduit, PRODUIT_nom = :nomProduit, PRODUIT_description = :descriptionProduit, PRODUIT_prix=:prixUnitaireProduit, PRODUIT_image_url=:URLImageProduit, TYPE_PRODUIT_id_type_produit=:typeProduitId WHERE COMMANDE_id_commande = :idCommande AND PRODUIT_id_produit = :idProduit";
            System.out.println("DetailsCommandes en commande " + id_commande +  " et produit "+ produit.getNom() + " mis à jour en table details_commande de la BDD db_bobopizza ");
        } else {
            sql = "INSERT INTO detail_commande (quantite, COMMANDE_id_commande, PRODUIT_id_produit, PRODUIT_nom, PRODUIT_description, PRODUIT_prix, PRODUIT_image_url, TYPE_PRODUIT_id_type_produit) " +
                    "VALUES (:quantiteProduit, :idCommande, :idProduit, :nomProduit, :descriptionProduit, :prixUnitaireProduit, :URLImageProduit, :typeProduitId)";
            System.out.println("DetailsCommandes en commande " + id_commande +  " et produit "+ produit.getNom() + "  inséré en table details_commande de la BDD db_bobopizza ");
        }
        namedParameterJdbcTemplate.update(sql, params);

    }
}

