package fr.eni.Pizza.app.bll;

import fr.eni.Pizza.app.bo.Utilisateur;

public interface AuthentificationManager {

    Utilisateur findUtilisateurByEmailAndPassword(String email, String password);
}
