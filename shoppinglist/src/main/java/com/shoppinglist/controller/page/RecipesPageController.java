package com.shoppinglist.controller.page;

import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.model.Recipe;
import com.shoppinglist.api.service.GroceryItemService;
import com.shoppinglist.api.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/home/recipes")
public class RecipesPageController {

    private final static Logger LOGGER = LoggerFactory.getLogger(RecipesPageController.class);

    @Autowired RecipeService recipeService;
    @Autowired GroceryItemService groceryItemService;

    @GetMapping
    public String getRecipes(Model model) {
        List<Recipe> recipeList = recipeService.getRecipes();
        LOGGER.info(String.format("Retrieved recipe list from database: %s", recipeList));
        model.addAttribute("recipes", recipeList);

        return "recipes.html :: recipes";
    }

    @GetMapping("/{recipeId}/grocery-items")
    public String getGroceryItems(@PathVariable long recipeId, Model model) {
        List<GroceryItem> groceryItems = groceryItemService.getGroceryItemsForRecipe(recipeId);

        model.addAttribute("groceryItems", groceryItems);

        return "groceryItems.html :: grocery-item-table";
    }

    @PostMapping
    public ModelAndView createRecipe(@RequestBody Recipe newRecipe) {
        ModelAndView modelAndView = new ModelAndView("recipes.html :: recipes-rows");

        if(!recipeService.saveRecipe(newRecipe)) {
            modelAndView.setStatus(HttpStatusCode.valueOf(400));
            return modelAndView;
        }

        List<Recipe> updatedGroceryItems = recipeService.getRecipes();

        if(updatedGroceryItems.isEmpty())
            modelAndView.setStatus(HttpStatusCode.valueOf(500));

        modelAndView.addObject("recipes", updatedGroceryItems);

        return modelAndView;
    }
}