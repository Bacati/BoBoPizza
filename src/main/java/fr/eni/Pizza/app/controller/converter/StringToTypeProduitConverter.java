package fr.eni.Pizza.app.controller.converter;

import fr.eni.Pizza.app.bll.TypeProduitManager;
import fr.eni.Pizza.app.bo.TypeProduit;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToTypeProduitConverter implements Converter<String, TypeProduit> {

    private TypeProduitManager typeProduitManager;

    public StringToTypeProduitConverter(TypeProduitManager typeProduitManager) {
        this.typeProduitManager = typeProduitManager;
    }

    @Override
    public TypeProduit convert(String idTypeProduit) {
        System.out.println("Conversion de idTypeProduit : " + idTypeProduit);
        return typeProduitManager.getTypeProduitById(Long.parseLong(idTypeProduit));
    }
}