package fr.eni.Pizza.app.controller.converter;

import fr.eni.Pizza.app.bll.ITypeProduitManager;
import fr.eni.Pizza.app.bo.TypeProduit;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToTypeProduitConverter implements Converter<String, TypeProduit> {

    private ITypeProduitManager typeProduitManager;

    public StringToTypeProduitConverter(ITypeProduitManager typeProduitManager) {
        this.typeProduitManager = typeProduitManager;
    }

    @Override
    public TypeProduit convert(String idTypeProduit) {
        System.out.println("Conversion de idTypeProduit : " + idTypeProduit);
        return typeProduitManager.getTypeProduitById(Long.parseLong(idTypeProduit));
    }
}