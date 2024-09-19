package fr.eni.Pizza.app.bll;

import fr.eni.Pizza.app.bo.Utilisateur;

public interface AuthentificationManager {

    Utilisateur authenticate(String email, String password);

    Utilisateur getAuthentificatedUser(String email);
}
