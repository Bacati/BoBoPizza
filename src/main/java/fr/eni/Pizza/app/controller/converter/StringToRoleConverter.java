package fr.eni.Pizza.app.controller.converter;

import fr.eni.Pizza.app.bll.RoleManager;
import fr.eni.Pizza.app.bo.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToRoleConverter implements Converter<String, Role> {

    private RoleManager roleManager;

    public StringToRoleConverter(RoleManager roleManager) {
        this.roleManager = roleManager;
    }


    @Override
    public Role convert(String idRole) {
        System.out.println("Conversion de idRole : " + idRole);
        return roleManager.getRoleById(Long.parseLong(idRole));
    }
}
