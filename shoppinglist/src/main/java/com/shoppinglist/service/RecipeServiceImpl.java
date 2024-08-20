package com.shoppinglist.service;

import com.shoppinglist.api.dao.GroceryDAO;
import com.shoppinglist.api.dao.RecipeDAO;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.model.Recipe;
import com.shoppinglist.api.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecipeServiceImpl implements RecipeService {

    @Qualifier("recipeJdbcDAO")
    @Autowired
    RecipeDAO recipeDAO;
    @Qualifier("groceryJdbcDAO")
    @Autowired
    GroceryDAO groceryDAO;

    @Override
    public List<Recipe> getRecipes() {return recipeDAO.getRecipes();}

    @Override
    public List<GroceryItem> getGroceryItems(long recipeId) { return groceryDAO.getGroceryItems(recipeId); }

    @Override
    public boolean saveRecipe(Recipe newRecipe) { return recipeDAO.saveRecipe(newRecipe); }

    @Override
    public boolean updateRecipe(Recipe recipe) { return recipeDAO.updateRecipe(recipe); }

    @Override
    public boolean deleteRecipe(String recipeName) { return recipeDAO.deleteRecipe(recipeName); }
}
