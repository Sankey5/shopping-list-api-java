package com.shoppinglist.dao.impl;

import com.google.common.collect.ImmutableList;
import com.shoppinglist.model.GroceryItem;
import com.shoppinglist.model.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GroceryItemDAOImpl {

    private static List<GroceryItem> groceryItemsList;

    static {
        groceryItemsList = populateGroceryItems();
    }

    private static List<GroceryItem> populateGroceryItems() {
        List<GroceryItem> mockGroceryItems = new ArrayList<>();

        // TODO: Create a list of multiple recipes
        mockGroceryItems.add(new GroceryItem("Bananas", 1.0, "lbs"));
        mockGroceryItems.add(new GroceryItem("Ground Beef", 2.5, "lbs"));
        mockGroceryItems.add(new GroceryItem("Salt", 2.2, "tbsp"));

        return mockGroceryItems;
    }

    public List<GroceryItem> getGroceryItemsForRecipe(long recipeId) { return ImmutableList.copyOf(groceryItemsList);}

    public List<GroceryItem> saveGroceryItemsForRecipe(Recipe recipe, List<GroceryItem> groceryItems) {
        groceryItems.forEach(groceryItem -> {
            groceryItem.setRecipe(recipe);
            groceryItemsList.add(groceryItem);
        });
        return ImmutableList.copyOf(groceryItemsList);
    }

    public List<GroceryItem> updateGroceryItemsForRecipe(Recipe recipe, List<GroceryItem> updatedGroceryItems) {

        // TODO: Needs a major upgrade in terms of performance
        for(GroceryItem updatedGroceryItem: updatedGroceryItems) {
            if(groceryItemExists(updatedGroceryItem)) {
                groceryItemsList = groceryItemsList.stream()
                        .map( r -> {
                            if(Objects.equals(r.getGroceryItemId(), updatedGroceryItem.getGroceryItemId()))
                                return updatedGroceryItem;
                            return r;
                        })
                        .collect(Collectors.toList());
            }
        }

        return ImmutableList.copyOf(groceryItemsList);
    }

    private boolean groceryItemExists(GroceryItem groceryItem) {
        return groceryItemsList.stream()
                .filter( r -> r.equals(groceryItem))
                .findFirst()
                .isEmpty();
    }

    public boolean deleteAllGroceryItemsForRecipe(long recipeId) {
        // TODO: Implement
        return false;
    }

    public boolean deleteGroceryItem(long groceryItemId) {
        for(int r = 0; r < groceryItemsList.size(); r++) {
            if(groceryItemId == groceryItemsList.get(r).getGroceryItemId()) {
                groceryItemsList.remove(r);
                return true;
            }
        }
        return false;
    }
}
