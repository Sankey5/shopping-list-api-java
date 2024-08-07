package com.shoppinglist.dao;

import com.google.common.collect.ImmutableList;
import com.shoppinglist.api.model.Recipe;
import com.shoppinglist.api.dao.RecipeDAO;
import com.shoppinglist.model.GroceryItem;
import com.shoppinglist.model.GroceryItemList;
import com.shoppinglist.model.RecipeImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RecipeDAOImpl implements RecipeDAO {

    private static  List<RecipeImpl> recipesList = new ArrayList<>();

    static {
        recipesList = populateRecipes();
    }

    private static List<RecipeImpl> populateRecipes() {
        ArrayList<RecipeImpl> temp = new ArrayList<RecipeImpl>();
        GroceryItemList temp1 = new GroceryItemList();

        temp1.addOrUpdateItem(new GroceryItem("Bananas", 1.0, "lbs"));
        temp1.addOrUpdateItem(new GroceryItem("Ground Beef", 2.5, "lbs"));
        temp1.addOrUpdateItem(new GroceryItem("Salt", 2.2, "tpbs"));

        temp.add(new RecipeImpl("Chicken Bake", temp1));
        temp.add(new RecipeImpl("Lasagna", temp1));
        temp.add(new RecipeImpl("Chicken Pot Pie", temp1));

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
