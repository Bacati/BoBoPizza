package fr.eni.Pizza.app.controller;

import fr.eni.Pizza.app.bll.AuthentificationManager;
import fr.eni.Pizza.app.bo.Client;
import fr.eni.Pizza.app.bo.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@SessionAttributes({"membreSession"})
public class UtilisateurController {

    private AuthentificationManager authentificationManager;

    public UtilisateurController(AuthentificationManager authentificationManager) {
        this.authentificationManager = authentificationManager;
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

//    @GetMapping({"add-member/{id}", "add-member"})
//    public String addMember (@PathVariable(required = false) int id, Model model, RedirectAttributes redirectAttributes) {
//
//        //Préparer ce que tu vas envoyer dans le formulaire par défaut
//        Member member = new Member();
//
//        //Si il y a un id existant dans la BDD, on récupère le film de la BDD
//        //Alors on écrase le film vide qu'on voulait afficher dans le formulaire
//        //Donc on affichera un film existant dans le formulaire
//        if (id != 0) {
//            member = loginManager.getMemberById(id);
//        }
//
//        //Data test: à commenter quand on sort de nos tests
//        member.setFirstName("John");
//        member.setLastName("Doe");
//        member.setEmail("john@doe.com");
//        member.setPassword("password");
//        member.setAdmin(false);
//
//        //Envoyer le Member dans le front (dans la réponse) pour le mettre dans le formulaire
//        //Fonctionne sur la base d'une clé en String : value
//        model.addAttribute("member", member);
//
//        return "/V2/add-member-page-v2";
//    }
//
//    @PostMapping("add-member")
//    //Attention : l'ordre de @Valid et @ModelAttribute est important : TJS mettre @Valid en premier
//    //ATTENTION: BindingResult bindingResult dans la liste arg doit être appelé immédiatement après le @Valid Class class,
//    public String processAddMember (@Valid @ModelAttribute(name = "member") Member member, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
//        //Objectif tester la validité des data par contrôle de surface (=check du format uniquement)
//        if (bindingResult.hasErrors()){
//            System.out.println("Erreur de contrôle surface");
//            //PAS DE REDIRECTION SI ERREUR FORMULAIRE
//            //Car redirection avec redirect signifie nouvelle url et donc nouvelle requête et donc perte des erreurs stockées
//            return "/V2/add-member-page-v2.html";
//        }
//
//        //On sauvegarde le member dans la table member de BDD db_movie en faisant appel à la couche BLL via LoginManager
//        loginManager.saveMember(member);
//
//        //Ajouter un message temporaire (flash card) dans la section "flashMessage"
//        EniIHMHelpers.sendSuccessFlashMessage(redirectAttributes, String.format("Vous avez ajouté le membre <%s %s> à la session et la BDD avec succès", member.getFirstName(), member.getLastName()));
//
//        //Afficher la page Accueil
//        return "redirect:/";
//    }
}
