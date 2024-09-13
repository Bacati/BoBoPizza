package fr.eni.Pizza.app.bll.MySQL;

import fr.eni.Pizza.app.bo.Role;
import fr.eni.Pizza.app.dal.DAORole;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Profile("MySQL")
@Service
public class RoleManager implements fr.eni.Pizza.app.bll.RoleManager {

    private DAORole daoRole;

    public RoleManager(DAORole daoRole) {
        this.daoRole = daoRole;
    }

    /**
     * Appelle la DAL
     *
     * @return le résultat de {@link DAORole#findAllRoles()}
     */
    @Override
    public List<Role> getAllRoles() {
        return daoRole.findAllRoles();
    }

    /**
     * Appelle la DAL
     *
     * @return le résultat de {@link DAORole#findRoleById(Long)}
     */
    @Override
    public Role getRoleById(Long id_role) {
       return daoRole.findRoleById(id_role);
    }
}
