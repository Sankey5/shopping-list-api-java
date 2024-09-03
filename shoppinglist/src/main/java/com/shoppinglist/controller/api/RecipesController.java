package com.shoppinglist.controller.api;

import com.shoppinglist.api.model.Recipe;
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

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> saveRecipe(@RequestBody RecipeImpl newRecipe) {

        // TODO: Return list instead of boolean
        if(!recipeService.saveRecipe(newRecipe)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{recipeId}", consumes = "application/json")
    public ResponseEntity<String> updateRecipe(@PathVariable long recipeId, @RequestBody Recipe updatedRecipe) {

        if(recipeId <= 0)
            return  ResponseEntity.badRequest().build();

        // TODO: Return list instead of boolean
        if(!recipeService.updateRecipe(recipeId, updatedRecipe)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{recipeId}")
    public ResponseEntity<String> deleteRecipe(@PathVariable long recipeId) {

        if(!recipeService.deleteRecipe(recipeId))
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok().build();
    }
}
