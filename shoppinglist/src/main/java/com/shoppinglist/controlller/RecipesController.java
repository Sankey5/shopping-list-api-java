package com.shoppinglist.controlller;

import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.model.Recipe;
import com.shoppinglist.api.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/recipe")
@RestController
public class RecipesController {

    @Autowired
    private RecipeService recipe;

    @GetMapping(value = "/getAll", produces = "application/json")
    public ResponseEntity<List<Recipe>> getRecipes() {
        return new ResponseEntity<>(recipe.getRecipes(), HttpStatus.OK) ;
    }

    @GetMapping(value = "/{recipeId]/grocery-items", produces = "application/json")
    public ResponseEntity<List<GroceryItem>> getRecipes(@PathVariable long recipeId) {
        return new ResponseEntity<>(recipe.getGroceryItems(recipeId), HttpStatus.OK) ;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<String> saveRecipe(@RequestBody Recipe newRecipe) {
        if(recipe.saveRecipe(newRecipe)) {
            return new ResponseEntity<>("Added new recipe", HttpStatus.OK);
        }

        return new ResponseEntity<>("Incorrect Recipe", HttpStatus.BAD_REQUEST);
    }

    @PutMapping(produces = "application/json")
    public ResponseEntity<String> addOrUpdateRecipes(@RequestBody Recipe updatedRecipe) {

        if(recipe.updateRecipe(updatedRecipe)) {
            return new ResponseEntity<>("Update recipe", HttpStatus.OK);
        }

        return new ResponseEntity<>("Incorrect Recipe", HttpStatus.BAD_REQUEST);
    }
}
