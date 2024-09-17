package fr.eni.Pizza.app.dal;

import fr.eni.Pizza.app.bo.Role;

import java.util.List;

public interface DAORole {

    List<Role> findAllRoles();

    Role findRoleById (Long id_role);

    boolean idRoleExist(Long id_role);
}
