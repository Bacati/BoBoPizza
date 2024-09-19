package fr.eni.Pizza.app.bll.MySQL;

import fr.eni.Pizza.app.bo.Utilisateur;
import fr.eni.Pizza.app.dal.DAOUtilisateur;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("MySQL")
@Service
public class AuthentificationManager implements fr.eni.Pizza.app.bll.AuthentificationManager {

    private DAOUtilisateur daoUtilisateur;

    public AuthentificationManager(DAOUtilisateur daoUtilisateur) {
        this.daoUtilisateur = daoUtilisateur;
    }

    @Override
    public Utilisateur authenticate (String email, String password) {
        return daoUtilisateur.findUtilisateurByEmailAndPassword(email, password);
    }

    @Override
    public Utilisateur getAuthentificatedUser(String email) {
        return daoUtilisateur.findUtilisateurByEmail(email);
    }
}
