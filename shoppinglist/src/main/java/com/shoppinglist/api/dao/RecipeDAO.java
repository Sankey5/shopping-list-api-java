package com.shoppinglist.api.dao;

import com.shoppinglist.api.model.Recipe;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface RecipeDAO {

    List<Recipe> getRecipes();

    Optional<Long> saveRecipe(Connection connection, Recipe newRecipe);

    boolean updateRecipe(Connection connection, Recipe updatedRecipe);

    boolean deleteRecipe(Connection connection, long recipeId);
}
