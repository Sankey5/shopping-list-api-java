package com.shoppinglist.dao;

import com.google.common.collect.ImmutableList;
import com.shoppinglist.api.model.Recipe;
import com.shoppinglist.api.dao.RecipeDAO;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.model.RecipeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RecipeDAOImpl implements RecipeDAO {

    private static List<RecipeImpl> recipesList;
    @Autowired
    private static GroceryDAOImpl groceryItemsDAO;

    static {
        recipesList = populateRecipes();
    }

    private static List<RecipeImpl> populateRecipes() {
        ArrayList<RecipeImpl> temp = new ArrayList<RecipeImpl>();
        List<GroceryItem> tempGroceryItems = groceryItemsDAO.getGroceryItems();

        temp.add(new RecipeImpl("Chicken Bake", tempGroceryItems));
        temp.add(new RecipeImpl("Lasagna", tempGroceryItems));
        temp.add(new RecipeImpl("Chicken Pot Pie", tempGroceryItems));

        return temp;
    }

    @Override
    public List<Recipe> getRecipes() { return ImmutableList.copyOf(recipesList);}

    @Override
    public Recipe saveRecipe(Recipe newRecipe) {
        recipesList.add((RecipeImpl) newRecipe);
        return newRecipe;
    }

    @Override
    public Recipe updateRecipe(Recipe updatedRecipe) {

        // TODO: Change to a stream
        // If it can be found, update the item
        for(int i = 0; i < recipesList.size(); i++) {
            if(recipesList.get(i).equals(updatedRecipe)) {
                recipesList.set(i, (RecipeImpl) updatedRecipe);
                return updatedRecipe;
            }
        }

        return null;
    }

    @Override
    public boolean deleteRecipe(String recipeName) {
        for(int r = 0; r < recipesList.size(); r++) {
            if(recipeName.equals(recipesList.get(r).getName())) {
                recipesList.remove(r);
                return true;
            }
        }
        return false;
    }
}
