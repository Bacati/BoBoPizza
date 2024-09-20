package fr.eni.Pizza.app.controller;

import fr.eni.Pizza.app.bll.AuthentificationManager;
import fr.eni.Pizza.app.bll.ClientManager;
import fr.eni.Pizza.app.bll.EmployeManager;
import fr.eni.Pizza.app.bo.Client;
import fr.eni.Pizza.app.bo.Employe;
import fr.eni.Pizza.app.bo.Role;
import fr.eni.Pizza.app.bo.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@SessionAttributes({"membreSession"})
public class UtilisateurController {

    private AuthentificationManager authentificationManager;
    private final ClientManager clientManager;
    private final EmployeManager employeManager;

    public UtilisateurController(AuthentificationManager authentificationManager, fr.eni.Pizza.app.bll.ClientManager clientManager, EmployeManager employeManager) {
        this.authentificationManager = authentificationManager;
        this.clientManager = clientManager;
        this.employeManager = employeManager;
    }

    @GetMapping("login")
    public String login (Model model, RedirectAttributes redirectAttributes) {

        //Tester si Utilisateur déjà connecté (le user en session n'est pas null)
        Utilisateur userConnected = (Utilisateur) model.getAttribute("membreSession");

        if (userConnected != null){
            System.out.println(userConnected.getNom() + " " + userConnected.getPrenom() + " est déjà connecté-e.");
            return "redirect:/";
        }

       //Préparer ce que tu vas envoyer dans le formulaire par défaut
        userConnected = new Client();

        // Data test: à commenter quand on sort de nos tests
        userConnected.setEmail("stephane.gobin@email.com");
        userConnected.setPassword("password");

        model.addAttribute("userByDefault", userConnected);


            //Afficher la page formulaire
            return "/connexion";
    }

    @GetMapping("user-connected")
    public String userConnected (Principal principal, Model model, RedirectAttributes redirectAttributes) {
        //On récupère l'identifiant stocké par Spring Security dans l'objet Principal, qui est un singleton Bean créé automatiquement et implicitement suite à notre norre utilisation de la méthode web de SecurityConfig
        String email = principal.getName();

        Utilisateur userConnected = authentificationManager.getAuthentificatedUser(email);

        //Ajouter le member en session
        model.addAttribute("membreSession", userConnected);

        if (userConnected != null) {
            System.out.printf("L'utilisateur ayant l'e-mail %s est connecté.%n", userConnected.getEmail());
        }

        return "redirect:/";
    }

    @GetMapping({"/addCLient"})
    public String addClient (Model model) {

        //Préparer ce que tu vas envoyer dans le formulaire par défaut
        Client client = new Client();


        //Envoyer le Member dans le front (dans la réponse) pour le mettre dans le formulaire
        //Fonctionne sur la base d'une clé en String : value
        model.addAttribute("client", client);

        return "inscription";
    }

    @PostMapping("/addCLient")
    //Attention : l'ordre de @Valid et @ModelAttribute est important : TJS mettre @Valid en premier
    //ATTENTION: BindingResult bindingResult dans la liste arg doit être appelé immédiatement après le @Valid Class class,
    public String processAddClient (@Valid @ModelAttribute(name = "client") Client client, BindingResult bindingResult, Model model) {
        //Objectif tester la validité des data par contrôle de surface (=check du format uniquement)
        if (bindingResult.hasErrors()){
            System.out.println("Erreur de contrôle surface");
            //PAS DE REDIRECTION SI ERREUR FORMULAIRE
            //Car redirection avec redirect signifie nouvelle url et donc nouvelle requête et donc perte des erreurs stockées
            return "inscription";
        }
        client.setRole(new Role(1L, "C"));
        //On sauvegarde le member dans la table member de BDD db_movie en faisant appel à la couche BLL via LoginManager
        clientManager.saveClient(client);

        //Ajouter un message temporaire (flash card) dans la section "flashMessage"
        System.out.println("Vous avez ajouté le membre <%s %s> à la session et la BDD avec succès" + client.getNom() + client.getPrenom());

        return "redirect:/";
    }
    @GetMapping({"/addEmployer"})
    public String addEmployer (Model model) {

        Employe employe = new Employe();

        model.addAttribute("employe", employe);

        return "inscriptionEmployer";
    }

    @PostMapping("/addEmployer")

    public String processAddEmployer (@Valid @ModelAttribute(name = "employe") Employe employe, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()){
            System.out.println("Erreur de contrôle surface");
            return "inscriptionEmployer";
        }

        clientManager.saveClient(employe);

        System.out.println("Vous avez ajouté le membre <%s %s> à la session et la BDD avec succès" + employe.getNom() + employe.getPrenom());

        return "redirect:/";
    }
}

