package fr.eni.Pizza.app.bll.MySQL;


import fr.eni.Pizza.app.bo.Etat;
import fr.eni.Pizza.app.dal.DAOEtat;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Profile("MySQL")
@Service
public class EtatManager implements fr.eni.Pizza.app.bll.EtatManager {

    private DAOEtat daoetat;

    public EtatManager(DAOEtat daoetat) {
        this.daoetat = daoetat;
    }

    /**
     * Appelle la DAL
     *
     * @return le résultat de {@link DAOEtat#findAllEtats()}
     */
    @Override
    public List<Etat> getAllEtats() {
        return daoetat.findAllEtats();
    }

    /**
     * Appelle la DAL
     *
     * @return le résultat de {@link DAOEtat#findEtatById(Long)}
     */
    @Override
    public Etat getEtatById(Long id_etat) {
        return daoetat.findEtatById(id_etat);
    }
}