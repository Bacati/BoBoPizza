package fr.eni.Pizza.app.controller.converter;

import fr.eni.Pizza.app.bll.ClientManager;
import fr.eni.Pizza.app.bo.Client;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToClientConverter implements Converter<String, Client>{
    private ClientManager clientManager;

    public StringToClientConverter(ClientManager clientManager) {
        this.clientManager = clientManager;
    }


    @Override
    public Client convert(String idUtilisateur){
        System.out.println("Conversion de idUtilisateur : " + idUtilisateur);
        return (Client) clientManager.findClientById(Long.parseLong(idUtilisateur));
    }
}
