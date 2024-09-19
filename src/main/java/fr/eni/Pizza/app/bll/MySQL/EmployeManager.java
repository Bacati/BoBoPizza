package fr.eni.Pizza.app.bll.MySQL;

import fr.eni.Pizza.app.bo.Utilisateur;
import fr.eni.Pizza.app.dal.DAOUtilisateur;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import java.util.Iterator;
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
        List <Utilisateur> employes = daoUtilisateur.findAllUtilisateursByClass('E');

        Iterator<Utilisateur> iterator = employes.iterator();

        while (iterator.hasNext()) {
            Utilisateur u = iterator.next();
            if(u.getNom().equals("PREPARATEUR NON ATTRIBUE") || u.getNom().equals("LIVREUR NON ATTRIBUE")){
                iterator.remove();
            }
        }

        return employes;
    }

    @Override
    public List<Utilisateur> findAllEmployesByRole(String libelleRole) {
        List <Utilisateur> employes = daoUtilisateur.findAllUtilisateursByRole(libelleRole);

        Iterator<Utilisateur> iterator = employes.iterator();

        while (iterator.hasNext()) {
            Utilisateur u = iterator.next();
            if(u.getNom().equals("PREPARATEUR NON ATTRIBUE") || u.getNom().equals("LIVREUR NON ATTRIBUE")){
                iterator.remove();
            }
        }

        return employes;
    }

    @Override
    public Utilisateur findEmployeById(Long id_utilisateur) {
        return daoUtilisateur.findUtilisateurById(id_utilisateur);
    }

    @Override
    public void saveEmploye(Utilisateur employe) {
        if(!employe.getPassword().contains("{bcrypt}")) {
            employe.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(employe.getPassword()));
        }
        daoUtilisateur.saveUtilisateur(employe);
    }
}
