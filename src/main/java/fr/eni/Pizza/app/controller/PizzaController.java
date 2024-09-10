package fr.eni.Pizza.app.controller;


import fr.eni.Pizza.app.bll.IProduitManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PizzaController {

    IProduitManager produitManager;

    public PizzaController(IProduitManager produitManager) {
        this.produitManager = produitManager;
    }

    //clés model "produits", "pizza-produits", "boisson-produits"
    @GetMapping("/laCarte")
    public String laCarte(Model model) {

        model.addAttribute("produits", produitManager.getAllProduitsByIdTypeProduit(1L));
        model.addAttribute("pizza-produits", produitManager.getAllProduitsByIdTypeProduit(1L));
        model.addAttribute("boisson-produits", produitManager.getAllProduitsByIdTypeProduit(1L));

        return "laCarte";
    }
}
