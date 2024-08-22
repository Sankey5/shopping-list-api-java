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
        List<Recipe> recipes = recipeService.getRecipes();
        return new ResponseEntity<>(recipes, HttpStatus.OK) ;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<String> saveRecipe(@RequestBody RecipeImpl newRecipe) {
        System.out.println(newRecipe.getId());
        if(recipeService.saveRecipe(newRecipe)) {
            return new ResponseEntity<>("Added new recipe", HttpStatus.OK);
        }

        return new ResponseEntity<>("Invalid recipe creation", HttpStatus.BAD_REQUEST);
    }

    @PutMapping(produces = "application/json")
    public ResponseEntity<String> updateRecipe(@RequestBody Recipe updatedRecipe) {

        if(updatedRecipe.getId() <= 0)
            return new ResponseEntity<>("Invalid recipe id", HttpStatus.BAD_REQUEST);

        if(recipeService.updateRecipe(updatedRecipe)) {
            return new ResponseEntity<>("Updated recipe", HttpStatus.OK);
        }

        return new ResponseEntity<>("Invalid recipe update", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{recipeId}", produces = "application/json")
    public ResponseEntity<String> deleteRecipe(@PathVariable long recipeId) {

        if(recipeService.deleteRecipe(recipeId)) {
            return new ResponseEntity<>("Deleted recipe", HttpStatus.OK);
        }

        return new ResponseEntity<>("Invalid recipe deletion", HttpStatus.BAD_REQUEST);
    }
}
