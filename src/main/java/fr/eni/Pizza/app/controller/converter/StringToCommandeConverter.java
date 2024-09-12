package fr.eni.Pizza.app.controller.converter;

import fr.eni.Pizza.app.bll.CommandeManager;
import fr.eni.Pizza.app.bo.Commande;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCommandeConverter implements Converter<String, Commande> {

    private CommandeManager commandeManager;

    public StringToCommandeConverter(CommandeManager commandeManager) {
        this.commandeManager = commandeManager;
    }

    @Override
    public Commande convert(String idCommande) {
        System.out.println("Conversion de idCommande : " + idCommande);
        return commandeManager.getCommandeById(Long.parseLong(idCommande));
    }
}
