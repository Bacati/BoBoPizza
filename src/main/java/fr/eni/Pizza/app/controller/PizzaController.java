package fr.eni.Pizza.app.controller;


import fr.eni.Pizza.app.bll.IProduitManager;
import fr.eni.Pizza.app.bll.MySQL.TypeProduitManager;
import fr.eni.Pizza.app.bo.Produit;
import fr.eni.Pizza.app.bo.TypeProduit;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SessionAttributes({"typeSession","membreSession"})

public class PizzaController {

    private final TypeProduitManager typeProduitManager;
    IProduitManager produitManager;

    public PizzaController(IProduitManager produitManager, TypeProduitManager typeProduitManager) {
        this.produitManager = produitManager;
        this.typeProduitManager = typeProduitManager;
    }

    //clés model "produits", "pizza-produits", "boisson-produits"
    @GetMapping("/laCarte")
    public String laCarte(Model model) {

        model.addAttribute("produits", produitManager.getAllProduits());
        model.addAttribute("pizzaProduits", produitManager.getAllProduitsByIdTypeProduit(1L));
        model.addAttribute("boissonProduits", produitManager.getAllProduitsByIdTypeProduit(2L));

        return "laCarte";
    }
    @GetMapping("/modifLaCarte")
    public String modifLaCarte(Model model) {

        model.addAttribute("produits", produitManager.getAllProduits());
        model.addAttribute("pizzaProduits", produitManager.getAllProduitsByIdTypeProduit(1L));
        model.addAttribute("boissonProduits", produitManager.getAllProduitsByIdTypeProduit(2L));

        return "modifCarte";
    }
    @GetMapping("/detail")
    public String afficherUnProduit(@RequestParam("idProduit") long id, Model model) {
        Produit p = this.produitManager.getProduitById(id);
        List<TypeProduit> typeSession = typeProduitManager.getAllTypeProduits();
        model.addAttribute("typeSession", typeSession);
        model.addAttribute("produit", p);
        return "carteDetail";
    }

    @GetMapping("/creer")
    public String creer(Model model) {
            Produit p = new Produit();
            model.addAttribute("produit", p);
            return "carteNew";

    }
    @PostMapping("/creer")
    public String modifCarte(@Valid @ModelAttribute("produit") Produit produit, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "carteDetail";
        }
        produitManager.saveProduit(produit);
        return "redirect:/laCarte";
    }

    @PostMapping("/delete")
    public String deleteProduit(@Valid @ModelAttribute("produit") Produit produit, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "carteDetail";
        }
        produitManager.deleteProduitById(produit.getId());
        System.out.println("delete"+ produit.getId());
        return "redirect:/laCarte";
    }

    @ModelAttribute("typeSession")
    public List<TypeProduit> chargerTypeSession(){
        return typeProduitManager.getAllTypeProduits();
    }
}

