package com.shoppinglist.service;

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
    @Qualifier("recipeDAOJdbc")
    private RecipeDAO recipeDAO;
    @Autowired
    private GroceryItemService groceryItemService;
    private Logger LOGGER = LoggerFactory.getLogger(RecipeServiceImpl.class);

    @Override
    public List<Recipe> getRecipes() {return recipeDAO.getRecipes();}

    @Transactional
    @Override public Recipe saveRecipe(Recipe newRecipe) {

        if(newRecipe.getRecipeId() != 0 || newRecipe.getName().isEmpty())
            return new RecipeImpl();

        Recipe recipeFromDatabase = recipeDAO.saveRecipe(newRecipe.getName());

        LOGGER.debug("Returned recipe from database: {}", recipeFromDatabase);

        Long recipeId = recipeFromDatabase.getRecipeId();
        List<GroceryItem> groceryItems = newRecipe.getGroceryItems();

        List<GroceryItem> newGroceryItems = groceryItemService.saveGroceryItemsForRecipe(recipeId, groceryItems);

        recipeFromDatabase.setGroceryItems(newGroceryItems);
        return recipeFromDatabase;
    }

    @Transactional
    @Override public Recipe updateRecipe(long recipeId, Recipe updatedRecipe) {

        if (Objects.isNull(updatedRecipe.getName()))
            return new RecipeImpl(0, "", List.of());

        String updatedRecipeName = recipeDAO.updateRecipeName(recipeId, updatedRecipe.getName());
        List<GroceryItem> updatedGroceryItems = groceryItemService.updateGroceryItemsForRecipe(recipeId, updatedRecipe.getGroceryItems());

        return new RecipeImpl(recipeId, updatedRecipeName, updatedGroceryItems);
    }

    @Transactional
    @Override public boolean deleteRecipe(long recipeId) {
        return groceryItemService.deleteAllGroceryItemsForRecipe(recipeId) &&
                recipeDAO.deleteRecipe(recipeId);
    }
}
