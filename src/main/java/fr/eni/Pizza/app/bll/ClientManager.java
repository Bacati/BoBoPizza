package fr.eni.Pizza.app.bll;

import fr.eni.Pizza.app.bo.Client;
import fr.eni.Pizza.app.bo.Employe;
import fr.eni.Pizza.app.bo.Utilisateur;

import java.util.List;

public interface ClientManager {

    List<Utilisateur> findAllClients();

    Utilisateur findClientById(Long id_utilisateur);

    void saveClient(Utilisateur client);

    boolean hasCurrentBasket(Long id_utilisateur);

    Long getCurrentBasketId(Long id_utilisateur);
}
