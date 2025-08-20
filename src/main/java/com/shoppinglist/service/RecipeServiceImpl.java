package com.shoppinglist.service;

import com.shoppinglist.api.dao.RecipeDAO;
import com.shoppinglist.api.service.GroceryItemService;
import com.shoppinglist.api.service.RecipeService;
import com.shoppinglist.model.GroceryItem;
import com.shoppinglist.model.Recipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeDAO recipeDAO;
    @Autowired
    private GroceryItemService groceryItemService;
    private final Logger LOGGER = LoggerFactory.getLogger(RecipeServiceImpl.class);

    @Override
    public List<Recipe> getRecipes() {
        return recipeDAO.findAll();
    }

    @Transactional
    public Recipe saveRecipe(Recipe newRecipe) {
        if (newRecipe.getId() != 0 || newRecipe.getName().isEmpty() || newRecipe.getClass() != Recipe.class)
            return new Recipe();
        Recipe savedRecipe = recipeDAO.save(newRecipe);
        LOGGER.info("Saved recipe: {}", savedRecipe);
        List<GroceryItem> savedGroceryItems =
                groceryItemService.saveGroceryItemsForRecipe(savedRecipe, newRecipe.getGroceryItems());
        LOGGER.info("Saved grocery items: {}", savedGroceryItems);
        savedRecipe.setGroceryItems(savedGroceryItems);
        LOGGER.info("Saved final recipe: {}", savedRecipe);
        return savedRecipe;
    }

    // TODO: Merge this with save recipe method above
    @Override
    @Transactional
    public Recipe updateRecipe(long recipeId, Recipe updatedRecipe) {

        if (recipeId == 0 || updatedRecipe.getName().isEmpty() || updatedRecipe.getClass() != Recipe.class)
            return new Recipe();
        return recipeDAO.save((Recipe) updatedRecipe);
    }

    @Override
    @Transactional
    public void deleteRecipe(long recipeId) {
        if (recipeId <= 0) {
            return;
        }
        groceryItemService.deleteAllGroceryItemsForRecipe(recipeId);
        recipeDAO.deleteById(recipeId);
    }
}
