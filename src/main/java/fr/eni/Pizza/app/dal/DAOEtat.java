package fr.eni.Pizza.app.dal;

import fr.eni.Pizza.app.bo.Etat;

import java.util.List;

public interface DAOEtat {

    List<Etat> findAllEtats();

    Etat findEtatById (Long id_etat);

}
