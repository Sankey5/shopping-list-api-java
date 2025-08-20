package com.shoppinglist.api.service;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shoppinglist.model.Recipe;

import java.util.List;

@JsonSerialize(as = Recipe.class)
public interface RecipeService {

    List<Recipe> getRecipes();

    Recipe saveRecipe(Recipe newRecipe);

    Recipe updateRecipe(long recipeId, Recipe recipe);

    void deleteRecipe(long recipeId);

}
