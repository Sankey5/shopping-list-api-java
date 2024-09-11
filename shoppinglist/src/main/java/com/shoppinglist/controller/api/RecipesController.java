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
    public ResponseEntity<Recipe> saveRecipe(@RequestBody Recipe newRecipe) {

        Recipe newRecipeFromDB = recipeService.saveRecipe(newRecipe);

        if(newRecipeFromDB.isAllDefault()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(newRecipeFromDB);
    }

    @PutMapping(value = "/{recipeId}", consumes = "application/json")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable long recipeId, @RequestBody Recipe updatedRecipe) {

        if(recipeId <= 0)
            return  ResponseEntity.badRequest().build();

        Recipe updateRecipeFromDB = recipeService.updateRecipe(recipeId, updatedRecipe);

        if(updateRecipeFromDB.isAllDefault()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(updateRecipeFromDB);
    }

    @DeleteMapping(value = "/{recipeId}")
    public ResponseEntity<String> deleteRecipe(@PathVariable long recipeId) {

        if(!recipeService.deleteRecipe(recipeId))
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok().header("HX-Trigger", "refreshGroceryList").build();
    }
}
