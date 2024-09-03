package com.shoppinglist.controller.page;

import com.shoppinglist.api.model.Recipe;
import com.shoppinglist.api.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomePageController {

    @GetMapping(value = "/")
    public String home() {
        return "index";
    }

    @GetMapping(value = "/health", produces = "text/json")
    public ResponseEntity<String> health() {
        return new ResponseEntity<String>("I am healthy", HttpStatus.OK);
    }

    @GetMapping(value = "/home/add-recipe")
    public String recipeForm(@RequestParam(name = "recipeCount", defaultValue = "10") int recipeCount, Model model) {

        if(recipeCount > 25)
            return "error";

        model.addAttribute("entries", recipeCount);
        return "addrecipe.html :: addRecipe";
    }

}