package fr.eni.Pizza.app.controller;


import fr.eni.Pizza.app.bll.MySQL.CommandeManager;
import fr.eni.Pizza.app.bll.MySQL.EtatManager;
import fr.eni.Pizza.app.bll.ProduitManager;
import fr.eni.Pizza.app.bll.MySQL.TypeProduitManager;
import fr.eni.Pizza.app.bo.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SessionAttributes({"typeSession","membreSession","clientSession"})
public class PizzaController {

    private final TypeProduitManager typeProduitManager;
    private final CommandeManager commandeManager;
    private final EtatManager etatManager;
    ProduitManager produitManager;

    public PizzaController(ProduitManager produitManager, TypeProduitManager typeProduitManager, CommandeManager commandeManager, EtatManager etatManager) {
        this.produitManager = produitManager;
        this.typeProduitManager = typeProduitManager;
        this.commandeManager = commandeManager;
        this.etatManager = etatManager;
    }
    @GetMapping("/")
    public String index( Model model) {
        Employe user = new Employe(0L, "lievre", "lucas", "3 rue fellonneau", "44000", "Nantes", "lucaslievre@gmail.com", "password", new Role(4L, "GERANT"));
        Client client = new Client(0L, "lapin", "lucas", "3 rue fellonneau", "44000", "Nantes", "lucaslapin@gmail.com", "password", new Role(1L, "CLIENT"));
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
        model.addAttribute("commandes", commandeManager.getAllCommandes());
        return "allCommande";
    }
    @PostMapping("/produit")
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
    @GetMapping("/commande")
    public String commander(Model model) {
        model.addAttribute("produits", produitManager.getAllProduits());
        model.addAttribute("pizzaProduits", produitManager.getAllProduitsByIdTypeProduit(1L));
        model.addAttribute("boissonProduits", produitManager.getAllProduitsByIdTypeProduit(2L));

        return "commande";
    }
    @GetMapping("/detailCommande")
    public String detailCommande(@RequestParam(value = "idCommande") Long id, Model model) {
        model.addAttribute("etat", etatManager.getAllEtats());
        model.addAttribute("produitBycommande", produitManager.getAllProduitsByIdCommande(id));
        return "detailCommande";
    }
    @GetMapping("/panier")
    public String panier(){
        return "panier";
    }
    /*
    @PostMapping("/creerPanier")
    public String creerPanier( BindingResult bindingResult){
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

