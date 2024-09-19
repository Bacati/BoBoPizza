package fr.eni.Pizza.app.bll.MySQL;

import fr.eni.Pizza.app.bo.Client;
import fr.eni.Pizza.app.bo.Utilisateur;
import fr.eni.Pizza.app.dal.DAOUtilisateur;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import java.util.List;

@Profile("MySQL")
@Service
public class ClientManager implements fr.eni.Pizza.app.bll.ClientManager {

    private DAOUtilisateur daoUtilisateur;

    public ClientManager(DAOUtilisateur daoUtilisateur) {
        this.daoUtilisateur = daoUtilisateur;
    }

    @Override
    public List<Utilisateur> findAllClients() {
        return daoUtilisateur.findAllUtilisateursByClass('C');
    }

    @Override
    public Utilisateur findClientById(Long id_utilisateur) {
        return daoUtilisateur.findUtilisateurById(id_utilisateur);
    }

    @Override
    public void saveClient(Utilisateur client) {
        if(!client.getPassword().contains("{bcrypt}")) {
            client.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(client.getPassword()));
        }
        daoUtilisateur.saveUtilisateur(client);
    }

    @Override
    public boolean hasCurrentBasket(Long id_utilisateur) {
        if (daoUtilisateur.findUtilisateurById(id_utilisateur).getId_commande_en_cours() == null) {
            return false;
        }
        return true;
    }

    @Override
    public Long getCurrentBasketId(Long id_utilisateur) {
        if (hasCurrentBasket(id_utilisateur)) {
            return daoUtilisateur.findUtilisateurById(id_utilisateur).getId_commande_en_cours();
        }
        return null;
    }
}