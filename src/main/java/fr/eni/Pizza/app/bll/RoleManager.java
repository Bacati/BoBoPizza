package fr.eni.Pizza.app.bll;

import fr.eni.Pizza.app.bo.Role;

import java.util.List;

public interface RoleManager {

    List<Role> getAllRoles();

    Role getRoleById (Long id_role);
}
