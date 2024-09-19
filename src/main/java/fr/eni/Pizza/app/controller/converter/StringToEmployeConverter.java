package fr.eni.Pizza.app.controller.converter;

import fr.eni.Pizza.app.bll.ClientManager;
import fr.eni.Pizza.app.bll.EmployeManager;
import fr.eni.Pizza.app.bo.Employe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToEmployeConverter implements Converter<String, Employe>{

    private EmployeManager empManager;

    public StringToEmployeConverter(EmployeManager empManager) {
        this.empManager = empManager;
    }

    @Override
    public Employe convert(String idUtilisateur){
        System.out.println("Conversion de idUtilisateur : " + idUtilisateur);
        return (Employe) empManager.findEmployeById(Long.parseLong(idUtilisateur));
    }
}

