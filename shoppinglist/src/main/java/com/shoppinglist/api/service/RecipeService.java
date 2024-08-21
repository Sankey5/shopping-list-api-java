package com.shoppinglist.api.service;

import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.model.Recipe;

import java.util.List;

public interface RecipeService {

    List<Recipe> getRecipes();

    boolean saveRecipe(Recipe newRecipe);

    boolean updateRecipe(Recipe recipe);

    boolean deleteRecipe(long recipeId);

}
