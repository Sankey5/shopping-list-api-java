package com.shoppinglist.api.dao;

import com.shoppinglist.api.model.Recipe;
import com.shoppinglist.model.RecipeImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface RecipeDAO extends JpaRepository<RecipeImpl, Long> {

//    List<Recipe> getRecipes();
//
//    Recipe saveRecipe(String recipeName);
//
//    String updateRecipeName(long recipeId, String updatedRecipeName);
//
//    boolean deleteRecipe(long recipeId);
}
