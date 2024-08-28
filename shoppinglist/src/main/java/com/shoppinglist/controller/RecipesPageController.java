package com.shoppinglist.controller;

import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.model.Recipe;
import com.shoppinglist.api.service.GroceryService;
import com.shoppinglist.api.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home/recipes")
public class RecipesPageController {

    @Autowired RecipeService recipeService;
    @Autowired GroceryService groceryService;

    @GetMapping
    public String getRecipes(Model model) {
        List<Recipe> recipeList = recipeService.getRecipes();
        model.addAttribute("recipes", recipeList);

        return "recipes.html :: recipes";
    }

    @GetMapping("/{recipeId}/grocery-items")
    public String getGroceryItems(@PathVariable long recipeId, Model model) {
        List<GroceryItem> groceryItems = groceryService.getGroceryItems(recipeId);

        model.addAttribute("groceryItems", groceryItems);

        return "groceryItems.html :: grocery-item-table";
    }
}