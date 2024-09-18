package fr.eni.Pizza.app.controller;

import fr.eni.Pizza.app.bll.EmployeManager;
import fr.eni.Pizza.app.bll.ClientManager;
import fr.eni.Pizza.app.bll.CommandeManager;
import fr.eni.Pizza.app.bll.EtatManager;
import fr.eni.Pizza.app.bll.ProduitManager;
import fr.eni.Pizza.app.bll.TypeProduitManager;
import fr.eni.Pizza.app.bo.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@SessionAttributes({"typeSession","membreSession","clientSession"})
public class PizzaController {

    private final ProduitManager produitManager;
    private final fr.eni.Pizza.app.bll.TypeProduitManager typeProduitManager;
    private final fr.eni.Pizza.app.bll.CommandeManager commandeManager;
    private final fr.eni.Pizza.app.bll.EtatManager etatManager;
    private final fr.eni.Pizza.app.bll.ClientManager clientManager;
    private final EmployeManager employeManager;

    public PizzaController(ProduitManager produitManager, fr.eni.Pizza.app.bll.TypeProduitManager typeProduitManager, fr.eni.Pizza.app.bll.CommandeManager commandeManager, fr.eni.Pizza.app.bll.EtatManager etatManager, fr.eni.Pizza.app.bll.ClientManager clientManager, EmployeManager employeManager) {
        this.produitManager = produitManager;
        this.typeProduitManager = typeProduitManager;
        this.commandeManager = commandeManager;
        this.etatManager = etatManager;
        this.clientManager = clientManager;
        this.employeManager = employeManager;
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
    public String afficherCommande(Model model, @ModelAttribute("membreSession") Utilisateur user) {
        model.addAttribute("etat", etatManager.getAllEtats());

        List<Long> etatIds = new ArrayList<>();
        if (user.getRole().getLibelle().equals("PIZZAIOLO")) {
            etatIds = Arrays.asList(2L, 3L, 4L);
        } else if (user.getRole().getLibelle().equals("LIVREUR")) {
            etatIds = Arrays.asList(4l, 5L, 6L, 7L);
        }

        if (!etatIds.isEmpty()) {
            model.addAttribute("commandes", commandeManager.getCommandesByEtats(etatIds));
        } else if (user.getRole().getLibelle().equals("GERANT")) {
            model.addAttribute("commandes", commandeManager.getAllCommandes());
        }

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
           return "redirect:/commande";
        }
        return "panier";
    }
    @PostMapping("/updateQuantite")
    public String updateQuantite(@ModelAttribute("clientSession") Client clientSession,
                                 @RequestParam("produitId") Long produitId,
                                 @RequestParam(value = "quantite") int quantite) {
        Produit produit = produitManager.getProduitById(produitId);
        produit.setQuantite(quantite);
        commandeManager.updateProductInBasket(clientSession.getId_commande_en_cours(), produit);
        System.out.println("produitId: " + produitId);
        System.out.println("quantite: " + quantite);
        return "redirect:/panier";
    }

    @PostMapping("/deleteProductInBasket")
    public String deleteProductInBasket(@ModelAttribute("clientSession") Client clientSession, @RequestParam("produitId") Long produitId) {

        commandeManager.deleteProductInBasket(clientSession.getId_commande_en_cours(), produitId);
        return "redirect:/panier";
    }
    @PostMapping("/deleteCommande")
        public String deleteCommande(@ModelAttribute("clientSession") Client clientSession){
        commandeManager.cancelBasket(clientSession.getId_commande_en_cours());
        clientSession.setId_commande_en_cours(null);
        return "redirect:/commande";
    }
    @PostMapping("/commander")
    public String passerCommande(@ModelAttribute("clientSession") Client clientSession, @RequestParam("heureCommande") String heureCommande){
        if (clientSession.getId_commande_en_cours() != null) {
            // TODO remplacer LocalDateTime.now().toString() par la datetime-local récupérée du Front au moment de la validation panier
            commandeManager.finishBasket(clientSession.getId_commande_en_cours(), heureCommande);
            System.out.println("Commande passée");
        }else {
            return "redirect:/commande";
        }
        return "redirect:/";
    }
    @PostMapping("/updateEtat")
    public String updateEtat(@RequestParam(value = "idCommande") Long id,
                             @RequestParam(value = "idEtat") Long idEtat){
        commandeManager.updateEtatFromCommande(id, idEtat);
        return "redirect:/allCommande";
    }
    @PostMapping("/creerPanier")
    public String creerPanier(@RequestParam(value = "idProduit", required = true) Long idProduit,
                              @RequestParam(value = "quantite", required = true) int quantite,
                              @ModelAttribute("clientSession") Client clientSession) {

        Produit produit = produitManager.getProduitById(idProduit);
        produit.setQuantite(quantite);

        if (clientSession.getId_commande_en_cours() == null) {
            Long idNouvelleCommande = commandeManager.createBasket(clientSession.getId(), produit);
            clientSession.setId_commande_en_cours(idNouvelleCommande);
            System.out.println("Création d'un nouveau panier avec le produit " + produit.getNom());
        } else {
            commandeManager.updateProductInBasket(clientSession.getId_commande_en_cours(), produit);
            System.out.println("Ajout du produit dans le panier existant : " + produit.getNom());
        }

        return "redirect:/commande";
    }

    @ModelAttribute("typeSession")
    public List<TypeProduit> chargerTypeSession(){
        return typeProduitManager.getAllTypeProduits();
    }
}

