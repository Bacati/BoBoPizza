package fr.eni.Pizza.app.controller.converter;

import fr.eni.Pizza.app.bll.EtatManager;
import fr.eni.Pizza.app.bo.Etat;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToEtatConverter implements Converter<String, Etat> {

    private EtatManager etatManager;

    public StringToEtatConverter(EtatManager etatManager) {
        this.etatManager = etatManager;
    }


    @Override
    public Etat convert(String idEtat) {
        System.out.println("Conversion de idEtat : " + idEtat);
        return etatManager.getEtatById(Long.parseLong(idEtat));
    }
}
