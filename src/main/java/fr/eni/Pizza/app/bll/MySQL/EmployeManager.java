package fr.eni.Pizza.app.bll.MySQL;

import fr.eni.Pizza.app.bo.Utilisateur;
import fr.eni.Pizza.app.dal.DAOUtilisateur;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Profile("MySQL")
@Service
public class EmployeManager implements fr.eni.Pizza.app.bll.EmployeManager {

    private DAOUtilisateur daoUtilisateur;

    public EmployeManager(DAOUtilisateur daoUtilisateur) {
        this.daoUtilisateur = daoUtilisateur;
    }

    @Override
    public List<Utilisateur> findAllEmployes() {
        return daoUtilisateur.findAllUtilisateursByClass('E');
    }

    @Override
    public List<Utilisateur> findAllEmployesByRole(String libelleRole) {
        return daoUtilisateur.findAllUtilisateursByRole(libelleRole);
    }

    @Override
    public Utilisateur findEmployeById(Long id_utilisateur) {
        return daoUtilisateur.findUtilisateurById(id_utilisateur);
    }

    @Override
    public void saveEmploye(Utilisateur employe) {
        //TODO à décommenter une fois Spring security inclu
        //employe.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(employe.getPassword()));
        daoUtilisateur.saveUtilisateur(employe);
    }
}
