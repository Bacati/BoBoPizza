package fr.eni.Pizza.app.controller;


import fr.eni.Pizza.app.bll.IProduitManager;
import fr.eni.Pizza.app.bll.MySQL.TypeProduitManager;
import fr.eni.Pizza.app.bo.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SessionAttributes({"typeSession","membreSession","clientSession"})

public class PizzaController {

    private final TypeProduitManager typeProduitManager;
    IProduitManager produitManager;

    public PizzaController(IProduitManager produitManager, TypeProduitManager typeProduitManager) {
        this.produitManager = produitManager;
        this.typeProduitManager = typeProduitManager;
    }
    @GetMapping("/")
    public String index( Model model) {
        Utilisateur user = new Utilisateur(0L, "lievre", "lucas", "lucaslievre@gmail.com", "password", new Role(3L, "GERANT"));
        Client client = new Client(0L, "lapin", "lucas", "3 rue fellonneau", "44000", "Nantes");
        model.addAttribute("membreSession", user);
        model.addAttribute("clientSession", client);
        System.out.println(client);
        System.out.println(user);
        return "index";
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
    @GetMapping("/produit")
    public String afficherOuCreerProduit(@RequestParam(value = "idProduit", required = false) Long id, Model model) {
        Produit p;
        if (id != null) {
            p = this.produitManager.getProduitById(id);
            model.addAttribute("titre", "Modification d'un produit");
        } else {
            p = new Produit();
            model.addAttribute("titre", "Création d'un produit");
        }
        List<TypeProduit> typeSession = typeProduitManager.getAllTypeProduits();
        model.addAttribute("typeSession", typeSession);
        model.addAttribute("produit", p);
        return "carteDetail";
    }

    @GetMapping("/allCommande")
    public String afficherCommande(Model model){
        //model.addAttribute("commandes", commandeManager.getAllCommandes());
        return "allCommande";
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
    @GetMapping("/commande")
    public String commander(Model model) {
        model.addAttribute("produits", produitManager.getAllProduits());
        model.addAttribute("pizzaProduits", produitManager.getAllProduitsByIdTypeProduit(1L));
        model.addAttribute("boissonProduits", produitManager.getAllProduitsByIdTypeProduit(2L));

        return "commande";
    }
    @GetMapping("/panier")
    public String panier(){
        return "panier";
    }
    /*@PostMapping("/creerPanier")
    public String creerPanier(){
        if (bindingResult.hasErrors()) {
            return "panier";
        }
        if (clientManager.hasCurrentBasket() === false){
            commandeManager.createBasket(produit.getId());
            System.out.println("création panier avec"+ produit.getId());
        }else {
            commandeManager.updateBasket(produit.getId());
            System.out.println("ajout dans le panier avec"+ produit.getId());
        }
        return "redirect:/commande";
    }*/

    @ModelAttribute("typeSession")
    public List<TypeProduit> chargerTypeSession(){
        return typeProduitManager.getAllTypeProduits();
    }
}

