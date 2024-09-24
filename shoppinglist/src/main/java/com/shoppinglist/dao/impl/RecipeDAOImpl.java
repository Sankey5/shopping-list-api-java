package com.shoppinglist.dao.impl;

import com.google.common.collect.ImmutableList;
import com.shoppinglist.api.dao.GroceryItemDAO;
import com.shoppinglist.api.model.Recipe;
import com.shoppinglist.api.dao.RecipeDAO;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.model.RecipeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class RecipeDAOImpl implements RecipeDAO {

    @Qualifier("groceryItemDAOImpl")
    @Autowired
    private GroceryItemDAO groceryItemsDAO;
    private List<RecipeImpl> recipesList;

    public RecipeDAOImpl() {
        groceryItemsDAO = new GroceryItemDAOImpl();
        recipesList = populateRecipes();
    }

    private List<RecipeImpl> populateRecipes() {
        ArrayList<RecipeImpl> tempRecipes = new ArrayList<>();
        List<GroceryItem> mockGroceryItems = groceryItemsDAO.getGroceryItemsForRecipe(1);

        tempRecipes.add(new RecipeImpl(1, "Chicken Bake", mockGroceryItems));
        tempRecipes.add(new RecipeImpl(2, "Lasagna", mockGroceryItems));
        tempRecipes.add(new RecipeImpl(3, "Chicken Pot Pie", mockGroceryItems));

        return tempRecipes;
    }

    @Override
    public List<Recipe> getRecipes() { return ImmutableList.copyOf(recipesList);}

    @Override
    public Recipe saveRecipe(String newRecipeName) {

        if(Objects.isNull(newRecipeName))
            return new RecipeImpl(0, "", List.of());


        RecipeImpl newRecipe = new RecipeImpl(0, newRecipeName, List.of());
        recipesList.add(newRecipe);
        return newRecipe;
    }

    @Override
    public String updateRecipeName(long recipeId, String updatedRecipeName) {

        if(Objects.isNull(updatedRecipeName))
            return "";

        if(recipeExists(updatedRecipeName)) {
            recipesList = recipesList.stream()
                    .peek(r ->  {
                        if (r.getRecipeId() == recipeId)
                            r.setName(updatedRecipeName);
                    }).toList();
            return updatedRecipeName;
        }

        return "";
    }

    private boolean recipeExists(String recipeName) {
        return recipesList.stream()
                .map(RecipeImpl::getName)
                .filter(rs -> rs.equals(recipeName))
                .findFirst()
                .isEmpty();
    }

    @Override
    public boolean deleteRecipe(long recipeId) {
        for(int r = 0; r < recipesList.size(); r++) {
            if(recipeId == recipesList.get(r).getRecipeId()) {
                recipesList.remove(r);
                return true;
            }
        }
        return false;
    }
}
