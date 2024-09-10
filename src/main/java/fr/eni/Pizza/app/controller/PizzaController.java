package fr.eni.Pizza.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PizzaController {

    //clé model "product"
    @GetMapping("/laCarte")
    public String laCarte() {
        return "laCarte";
    }
}
