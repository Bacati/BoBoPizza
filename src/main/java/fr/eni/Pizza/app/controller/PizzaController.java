package fr.eni.Pizza.app.controller;

import fr.eni.Pizza.app.bll.EmployeManager;
import fr.eni.Pizza.app.bll.ProduitManager;
import fr.eni.Pizza.app.bo.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@SessionAttributes({"typeSession","membreSession"})
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
    public String index() {
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
    @GetMapping("/produit")
    public String afficherOuCreerProduit(@RequestParam(value = "idProduit", required = false) Long id, Model model) {
        Produit p;
        if (id != null) {
            p = produitManager.getProduitById(id);
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

    @PostMapping("/produit")
    public String modifCarte(@Valid @ModelAttribute("produit") Produit produit, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "carteDetail";
        }
        produitManager.saveProduit(produit);

        //TODO ajouter un message flash de type
        //redirectAttributes.addFlashAttribute("message", "Produit "+ produit.getNom() +" créé avec succès !");
        return "redirect:/laCarte";
    }

    @GetMapping("/allCommande")
    public String afficherCommande(Model model, @ModelAttribute("membreSession") Utilisateur user) {
        model.addAttribute("etat", etatManager.getAllEtats());
        model.addAttribute("user", user);

        List<Long> etatIds = new ArrayList<>();
        if (user.getRole().getLibelle().equals("PIZZAIOLO")) {
            etatIds = Arrays.asList(2L, 3L, 4L);
        } else if (user.getRole().getLibelle().equals("LIVREUR")) {
            etatIds = Arrays.asList(4L, 5L, 6L, 7L);
        }

        if (!etatIds.isEmpty()) {
            model.addAttribute("commandes", commandeManager.getCommandesByEtats(etatIds));
        } else if (user.getRole().getLibelle().equals("GERANT")) {
            model.addAttribute("commandes", commandeManager.getAllCommandes());
        }

        return "allCommande";
    }

    @PostMapping("/delete")
    public String deleteProduit(@ModelAttribute("produit") Produit produit) {
        produitManager.deleteProduitById(produit.getId());
        System.out.println("delete"+ produit.getId());
        return "redirect:/laCarte";
    }
    @GetMapping("/commande")
    public String commander(Model model, @ModelAttribute ("membreSession") Utilisateur user) {
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
    public String panier(Model model, @ModelAttribute ("membreSession") Utilisateur user){
        if (user.getId_commande_en_cours() != null){
            model.addAttribute("commandes", commandeManager.getCommandeById(user.getId_commande_en_cours()) );
            model.addAttribute("produit", produitManager.getAllProduitsByIdCommande(user.getId_commande_en_cours()));
            return "panier";
        }
        return "redirect:/commande";
    }
    @PostMapping("/updateQuantite")
    public String updateQuantite(@ModelAttribute ("membreSession") Utilisateur user,
                                 @RequestParam("produitId") Long produitId,
                                 @RequestParam(value = "quantite") int quantite) {
        Produit produit = produitManager.getProduitById(produitId);
        produit.setQuantite(quantite);
        commandeManager.updateProductInBasket(user.getId_commande_en_cours(), produit);
        System.out.println("produitId: " + produitId);
        System.out.println("quantite: " + quantite);
        return "redirect:/panier";
    }

    @PostMapping("/deleteProductInBasket")
    public String deleteProductInBasket(@ModelAttribute ("membreSession") Utilisateur user, @RequestParam("produitId") Long produitId) {

        commandeManager.deleteProductInBasket(user.getId_commande_en_cours(), produitId);
        return "redirect:/panier";
    }
    @PostMapping("/deleteCommande")
        public String deleteCommande(@ModelAttribute ("membreSession") Utilisateur user){
        commandeManager.cancelBasket(user.getId_commande_en_cours());
        user.setId_commande_en_cours(null);
        return "redirect:/commande";
    }
    @PostMapping("/commander")
    public String passerCommande(@ModelAttribute ("membreSession") Utilisateur user, @RequestParam("heureCommande") String heureCommande, Model model){
        if (user.getId_commande_en_cours() != null) {
            commandeManager.finishBasket(user.getId_commande_en_cours(), heureCommande);
            user.setId_commande_en_cours(null);
            model.addAttribute("membreSession", user);
            System.out.println("Commande passée");
        }else {
            return "redirect:/commande";
        }
        return "redirect:/";
    }
    @PostMapping("/updateEtat")
    public String updateEtat(@RequestParam(value = "idCommande") Long id,
                             @RequestParam(value = "idEtat") Long idEtat, Model model){
        Utilisateur employe = (Utilisateur) model.getAttribute("membreSession");
        commandeManager.updateEtatFromCommande(id, idEtat, employe.getId());
        return "redirect:/allCommande";
    }
    @PostMapping("/creerPanier")
    public String creerPanier(@RequestParam(value = "idProduit", required = true) Long idProduit,
                              @RequestParam(value = "quantite", required = true) int quantite,
                              @ModelAttribute ("membreSession") Utilisateur user) {

        Produit produit = produitManager.getProduitById(idProduit);
        produit.setQuantite(quantite);

        if (user.getId_commande_en_cours() == null) {
            Long idNouvelleCommande = commandeManager.createBasket(user.getId(), produit);
            user.setId_commande_en_cours(idNouvelleCommande);
            System.out.println("Création d'un nouveau panier avec le produit " + produit.getNom());
        } else {
            commandeManager.updateProductInBasket(user.getId_commande_en_cours(), produit);
            System.out.println("Ajout du produit dans le panier existant : " + produit.getNom());
        }

        return "redirect:/commande";
    }
    @GetMapping("/profil")
    public String profil(Model model){
        model.addAttribute("users", employeManager.findAllEmployes());
        model.addAttribute("clients", clientManager.findAllClients());
        return "profil";
    }
    @GetMapping("/user")
    public String user(@RequestParam(value = "idUser")Long id,Model model){
        model.addAttribute("user", employeManager.findEmployeById(id));
        return "profilDetail";
    }
    @PostMapping("/user")
    public String modifUser(@ModelAttribute(value = "user") Employe user){
        System.out.println(user);
        Utilisateur updateUser = employeManager.findEmployeById(user.getId());
        updateUser.getRole().setId(user.getRole().getId());
        employeManager.saveEmploye(updateUser);
        return "redirect:/profil";
    }
    @ModelAttribute("typeSession")
    public List<TypeProduit> chargerTypeSession(){
        return typeProduitManager.getAllTypeProduits();
    }
}

