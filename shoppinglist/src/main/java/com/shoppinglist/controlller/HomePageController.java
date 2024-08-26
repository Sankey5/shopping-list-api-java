package com.shoppinglist.controlller;

import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.model.Recipe;
import com.shoppinglist.api.service.GroceryService;
import com.shoppinglist.api.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomePageController {

    @Autowired RecipeService recipeService;
    @Autowired GroceryService groceryService;
    @Autowired ShoppingListController shoppingListController;

    @GetMapping(value = "/")
    public String home() {
        return "index";
    }

    @GetMapping(value = "/health", produces = "text/json")
    public ResponseEntity<String> health() {
        return new ResponseEntity<String>("I am healthy", HttpStatus.OK);
    }

    @GetMapping(value = "/home/recipes")
    public String getRecipes(Model model) {
        List<Recipe> recipeList = recipeService.getRecipes();
        model.addAttribute("recipes", recipeList);

        return "recipes.html :: recipes";
    }

    @GetMapping(value = "/home/recipes/{recipeId}/grocery-items")
    public String getGroceryItems(@PathVariable long recipeId, Model model) {
        List<GroceryItem> groceryItems = groceryService.getGroceryItems(recipeId);

        model.addAttribute("groceryItems", groceryItems);

        return "groceryItems.html :: grocery-item-table";
    }

    @GetMapping(value = "/home/groceryList")
    public String getShoppingList(Model model) {
        //shoppingListController.getShoppingList();

        return "grocerylist.html :: grocerylist";
    }

}