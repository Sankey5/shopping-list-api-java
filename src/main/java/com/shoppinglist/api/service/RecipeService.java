package com.shoppinglist.api.service;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.model.Recipe;
import com.shoppinglist.model.RecipeImpl;

import java.util.List;

@JsonSerialize(as = RecipeImpl.class)
public interface RecipeService {

    List<Recipe> getRecipes();

    Recipe saveRecipe(Recipe newRecipe);

    Recipe updateRecipe(long recipeId, Recipe recipe);

    boolean deleteRecipe(long recipeId);

}
