package fr.eni.Pizza.app.controller;


import fr.eni.Pizza.app.bll.MySQL.ClientManager;
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
    private final ClientManager clientManager;
    ProduitManager produitManager;

    public PizzaController(ProduitManager produitManager, TypeProduitManager typeProduitManager, CommandeManager commandeManager, EtatManager etatManager, ClientManager clientManager) {
        this.produitManager = produitManager;
        this.typeProduitManager = typeProduitManager;
        this.commandeManager = commandeManager;
        this.etatManager = etatManager;
        this.clientManager = clientManager;
    }
    @GetMapping("/")
    public String index( Model model) {
        Employe user = new Employe(0L, "lievre", "lucas", "3 rue fellonneau", "44000", "Nantes", "lucaslievre@gmail.com", "password", new Role(4L, "GERANT"));
        Client client = new Client(6L, "DUPONT", "Martin", "2b rue Michael FARADAY", "44300", "SAINT-HERBLAIN", "martin.dupont@email.com", "password", new Role(1L, "CLIENT"));
        model.addAttribute("membreSession", user);
        model.addAttribute("clientSession", client);
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
    public String commander(Model model, @ModelAttribute ("clientSession") Client client) {
        model.addAttribute("produits", produitManager.getAllProduits());
        model.addAttribute("pizzaProduits", produitManager.getAllProduitsByIdTypeProduit(1L));
        model.addAttribute("boissonProduits", produitManager.getAllProduitsByIdTypeProduit(2L));
        System.out.println(client);

        return "commande";
    }
    @GetMapping("/detailCommande")
    public String detailCommande(@RequestParam(value = "idCommande") Long id, Model model) {
        model.addAttribute("etat", etatManager.getAllEtats());
        model.addAttribute("produitBycommande", produitManager.getAllProduitsByIdCommande(id));
        return "detailCommande";
    }
    @GetMapping("/panier")
    public String panier(Model model, @ModelAttribute("clientSession") Client clientSession){
        if (clientSession.getId_commande_en_cours() != null){
            model.addAttribute("commandes", commandeManager.getCommandeById(clientSession.getId_commande_en_cours()) );
            model.addAttribute("produit", produitManager.getAllProduitsByIdCommande(clientSession.getId_commande_en_cours()));
        }else {
           return "commande";
        }
        return "panier";
    }
    @PostMapping("/deleteCommande")
        public String deleteCommande(@ModelAttribute("clientSession") Client clientSession){
        commandeManager.deleteCommandeById(clientSession.getId_commande_en_cours());
        return "commande";
    }
    @PostMapping("/commander")
    public String passerCommande(@ModelAttribute("clientSession") Client clientSession){

        if (clientSession.getId_commande_en_cours() != null) {
            commandeManager.finishBasket(clientSession.getId_commande_en_cours());
            System.out.println("commande passer");
        }else {
            return "commande";
        }
        return "redirect:/";
    }

    @PostMapping("/creerPanier")
    public String creerPanier(@RequestParam(value = "idProduit", required = true) Long idProduit,
                              @RequestParam(value = "quantite", required = true) int quantite,
                              @ModelAttribute("clientSession") Client clientSession) {

        Produit produit = produitManager.getProduitById(idProduit);
        produit.setQuantite(quantite);

        // Vérifier si l'utilisateur a déjà un panier en cours
        if (clientSession.getId_commande_en_cours() == null) {
            // Créer une nouvelle commande/panier
            Long idNouvelleCommande = commandeManager.createBasket(clientSession.getId(), produit);
            clientSession.setId_commande_en_cours(idNouvelleCommande); // Mise à jour de l'ID du panier en cours
            System.out.println("Création d'un nouveau panier avec le produit " + produit.getNom());
        } else {
            // Mise à jour du panier existant
            commandeManager.updateBasket(clientSession.getId_commande_en_cours(), produit);
            System.out.println("Ajout du produit dans le panier existant : " + produit.getNom());
        }

        return "redirect:/commande";
    }

    @ModelAttribute("typeSession")
    public List<TypeProduit> chargerTypeSession(){
        return typeProduitManager.getAllTypeProduits();
    }
}

