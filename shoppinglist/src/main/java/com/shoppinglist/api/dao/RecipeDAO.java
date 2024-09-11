package com.shoppinglist.api.dao;

import com.shoppinglist.api.model.Recipe;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface RecipeDAO {

    List<Recipe> getRecipes();

    Recipe saveRecipe(Recipe newRecipe);

    Recipe updateRecipe(long recipeId, Recipe updatedRecipe);

    boolean deleteRecipe(long recipeId);
}
