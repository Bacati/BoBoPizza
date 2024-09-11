package fr.eni.Pizza.app.controller.converter;

import fr.eni.Pizza.app.bll.IProduitManager;
import fr.eni.Pizza.app.bo.Produit;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToProduitConverter implements Converter<String, Produit> {

    IProduitManager produitManager;

    public StringToProduitConverter(IProduitManager produitManager) {
        this.produitManager = produitManager;
    }


    @Override
    public Produit convert(String idProduit) {
        System.out.println("Conversion de idProduit : " + idProduit);
        return produitManager.getProduitById(Long.parseLong(idProduit));
    }
}
