package fr.eni.Pizza.app.dal;

import fr.eni.Pizza.app.bo.Utilisateur;

import java.util.List;

public interface DAOUtilisateur {

    List<Utilisateur> findAllUtilisateurs();

    List<Utilisateur> findAllUtilisateursByClass(char Class);

    Utilisateur findUtilisateurById(Long id_utilisateur);

    Utilisateur findUtilisateurByEmailAndPassword(String email, String password);

    void saveUtilisateur(Utilisateur utilisateur);
}
