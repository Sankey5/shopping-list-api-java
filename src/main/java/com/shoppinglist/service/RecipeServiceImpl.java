package com.shoppinglist.service;

import com.google.common.collect.ImmutableList;
import com.shoppinglist.api.dao.GroceryItemDAO;
import com.shoppinglist.api.dao.RecipeDAO;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.model.Recipe;
import com.shoppinglist.api.service.GroceryItemService;
import com.shoppinglist.api.service.RecipeService;
import com.shoppinglist.model.RecipeImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Component
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeDAO recipeDAO;
    @Autowired
    private GroceryItemService groceryItemService;
    private Logger LOGGER = LoggerFactory.getLogger(RecipeServiceImpl.class);

    @Override
    public List<Recipe> getRecipes() {return ImmutableList.copyOf(recipeDAO.findAll());}

    @Transactional
    @Override public Recipe saveRecipe(Recipe newRecipe) {

        if(newRecipe.getRecipeId() != 0 || newRecipe.getName().isEmpty() || newRecipe.getClass() != RecipeImpl.class)
            return new RecipeImpl();
        Recipe savedRecipe = recipeDAO.save((RecipeImpl) newRecipe);
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

        if (recipeId == 0 || updatedRecipe.getName().isEmpty() || updatedRecipe.getClass() != RecipeImpl.class)
            return new RecipeImpl();
        return recipeDAO.save((RecipeImpl) updatedRecipe);
    }

    @Override
    @Transactional
    public boolean deleteRecipe(long recipeId) {
        if(recipeId <= 0
                && !groceryItemService.deleteAllGroceryItemsForRecipe(recipeId))
            return false;
        recipeDAO.deleteById(recipeId);
        return true;
    }
}
