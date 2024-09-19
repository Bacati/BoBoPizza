package fr.eni.Pizza.app.bll;

import fr.eni.Pizza.app.bo.Utilisateur;

public interface AuthentificationManager {

    Utilisateur getAuthentificatedUser(String email);
}
