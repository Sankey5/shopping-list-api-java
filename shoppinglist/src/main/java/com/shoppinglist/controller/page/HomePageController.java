package com.shoppinglist.controller.page;

import com.shoppinglist.api.model.Recipe;
import com.shoppinglist.api.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
public class HomePageController {

    private final Logger LOGGER = LoggerFactory.getLogger(HomePageController.class);

    @GetMapping(value = "/")
    public String home() {
        return "index";
    }

    @GetMapping(value = "/health", produces = "text/json")
    public ResponseEntity<String> health() {
        return new ResponseEntity<String>("I am healthy", HttpStatus.OK);
    }

    @GetMapping(value = "/home/add-recipe")
    public String recipeForm(@RequestParam(name = "recipeCount", required = false) Integer recipeCount, Model model) {

        if(Objects.isNull(recipeCount)) {
            recipeCount = 10;
            model.addAttribute("start", 0);
            model.addAttribute("entries", recipeCount);
            return "addrecipe.html :: addRecipe";
        }


        if(recipeCount < 0 || recipeCount > 25)
            return "error";


        model.addAttribute("start", recipeCount+1);
        model.addAttribute("entries", recipeCount+1);
        return "addrecipe.html :: singleRecipeInput";
    }

}