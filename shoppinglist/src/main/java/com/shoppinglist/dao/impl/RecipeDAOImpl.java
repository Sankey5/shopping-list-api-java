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

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Optional<Long> saveRecipe(Connection connection, Recipe newRecipe) {
        // TODO: Major overhaul needed
        recipesList.add((RecipeImpl) newRecipe);
        return Optional.empty();
    }

    @Override
    public boolean updateRecipe(Connection connection, long recipeId, Recipe updatedRecipe) {

        if(recipeExists(updatedRecipe)) {
            recipesList = recipesList.stream()
                    .map( r ->  r.getId() == recipeId ? (RecipeImpl) updatedRecipe : r)
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
    public boolean deleteRecipe(Connection connection, long recipeId) {
        for(int r = 0; r < recipesList.size(); r++) {
            if(recipeId == recipesList.get(r).getId()) {
                recipesList.remove(r);
                return true;
            }
        }
        return false;
    }
}
