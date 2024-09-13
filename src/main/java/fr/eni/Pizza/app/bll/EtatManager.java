package fr.eni.Pizza.app.bll;

import fr.eni.Pizza.app.bo.Etat;

import java.util.List;

public interface EtatManager {

    List<Etat> getAllEtats();

    Etat getEtatById (Long id_etat);
}
