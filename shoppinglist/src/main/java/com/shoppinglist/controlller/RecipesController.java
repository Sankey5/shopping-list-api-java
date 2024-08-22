package com.shoppinglist.controlller;

import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.model.Recipe;
import com.shoppinglist.api.service.GroceryService;
import com.shoppinglist.api.service.RecipeService;
import com.shoppinglist.model.RecipeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/recipes")
@RestController
public class RecipesController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Recipe>> getRecipes() {
        return new ResponseEntity<>(recipeService.getRecipes(), HttpStatus.OK) ;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<String> saveRecipe(@RequestBody RecipeImpl newRecipe) {

        if(!recipeService.saveRecipe(newRecipe)) {
            return new ResponseEntity<>("Invalid recipe creation", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Added new recipe", HttpStatus.OK);
    }

    @PutMapping(value = "/{recipeId}", produces = "application/json")
    public ResponseEntity<String> updateRecipe(@PathVariable long recipeId, @RequestBody Recipe updatedRecipe) {

        if(recipeId <= 0)
            return new ResponseEntity<>("Invalid recipe update", HttpStatus.BAD_REQUEST);

        if(!recipeService.updateRecipe(recipeId, updatedRecipe)) {
            return new ResponseEntity<>("Invalid recipe update", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Updated recipe", HttpStatus.OK);
    }

    @DeleteMapping(value = "/{recipeId}", produces = "application/json")
    public ResponseEntity<String> deleteRecipe(@PathVariable long recipeId) {

        if(recipeService.deleteRecipe(recipeId)) {
            return new ResponseEntity<>("Deleted recipe", HttpStatus.OK);
        }

        return new ResponseEntity<>("Invalid recipe deletion", HttpStatus.BAD_REQUEST);
    }
}
