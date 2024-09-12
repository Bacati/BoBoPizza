package fr.eni.Pizza.app.bll;

public interface ClientManager {

    //TODO devra retourner true si en BDD id_commande_en_panier est non null, ou false si null
    boolean hasCurrentBasket();
}
