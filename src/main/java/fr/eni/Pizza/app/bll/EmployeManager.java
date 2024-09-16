package fr.eni.Pizza.app.bll;

import fr.eni.Pizza.app.bo.Employe;
import fr.eni.Pizza.app.bo.Utilisateur;

import java.util.List;

public interface EmployeManager {

    List<Utilisateur> findAllEmployes();

    List<Utilisateur> findAllEmployesByRole(String libelleRole);

    Utilisateur findEmployeById(Long id_utilisateur);

    void saveEmploye(Utilisateur employe);
}
