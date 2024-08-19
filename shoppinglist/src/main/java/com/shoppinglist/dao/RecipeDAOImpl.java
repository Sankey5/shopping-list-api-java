package com.shoppinglist.dao;

import com.google.common.collect.ImmutableList;
import com.shoppinglist.api.dao.GroceryDAO;
import com.shoppinglist.api.model.Recipe;
import com.shoppinglist.api.dao.RecipeDAO;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.model.RecipeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RecipeDAOImpl implements RecipeDAO {

    @Autowired
    private GroceryDAO groceryItemsDAO;
    private List<RecipeImpl> recipesList;

    public RecipeDAOImpl() {
        groceryItemsDAO = new GroceryDAOImpl();
        recipesList = populateRecipes();
    }

    private List<RecipeImpl> populateRecipes() {
        ArrayList<RecipeImpl> tempRecipes = new ArrayList<>();
        List<GroceryItem> mockGroceryItems = groceryItemsDAO.getGroceryItems();

        tempRecipes.add(new RecipeImpl("Chicken Bake", mockGroceryItems));
        tempRecipes.add(new RecipeImpl("Lasagna", mockGroceryItems));
        tempRecipes.add(new RecipeImpl("Chicken Pot Pie", mockGroceryItems));

        return tempRecipes;
    }

    @Override
    public List<Recipe> getRecipes() { return ImmutableList.copyOf(recipesList);}

    @Override
    public boolean saveRecipe(Recipe newRecipe) {
        return recipesList.add((RecipeImpl) newRecipe);
    }

    @Override
    public boolean updateRecipe(Recipe updatedRecipe) {

        if(recipeExists(updatedRecipe)) {
            recipesList = recipesList.stream()
                    .map( r ->  r.getId() == updatedRecipe.getId() ? (RecipeImpl) updatedRecipe : r)
                    .collect(Collectors.toList());
            return true;
        }

        return false;
    }

    private boolean recipeExists(Recipe recipe) {
        return recipesList.stream()
                .filter( r -> r.equals(recipe))
                .findFirst()
                .isEmpty();
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
