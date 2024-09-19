package fr.eni.Pizza.app.dal;

import fr.eni.Pizza.app.bo.Utilisateur;

import java.util.List;

public interface DAOUtilisateur {

    List<Utilisateur> findAllUtilisateurs();

    List<Utilisateur> findAllUtilisateursByClass(char c);

    List<Utilisateur> findAllUtilisateursByRole(String libelleRole);

    Utilisateur findUtilisateurById(Long id_utilisateur);

    Utilisateur findUtilisateurByEmail(String email);

    boolean idUtilisateurExist(Long id_utilisateur);

    Long obtainIDFromLastCreatedUtilisateur();

    void saveUtilisateur(Utilisateur utilisateur);
}
