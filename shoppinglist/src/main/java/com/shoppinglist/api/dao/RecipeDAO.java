package com.shoppinglist.api.dao;

import com.shoppinglist.api.model.Recipe;

import java.util.List;

public interface RecipeDAO {

    List<Recipe> getRecipes();

    boolean saveRecipe(Recipe newRecipe);

    boolean updateRecipe(Recipe updatedRecipe);

    boolean deleteRecipe(String recipeName);
}
