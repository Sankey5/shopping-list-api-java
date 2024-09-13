package com.shoppinglist.dao.impl;

import com.google.common.collect.ImmutableList;
import com.shoppinglist.api.dao.GroceryItemDAO;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.model.GroceryItemImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class GroceryItemDAOImpl implements GroceryItemDAO {

    private static List<GroceryItemImpl> groceryItemsList;

    static {
        groceryItemsList = populateGroceryItems();
    }

    private static List<GroceryItemImpl> populateGroceryItems() {
        List<GroceryItemImpl> mockGroceryItems = new ArrayList<>();

        // TODO: Create a list of multiple recipes
        mockGroceryItems.add(new GroceryItemImpl("Bananas", 1.0, "lbs"));
        mockGroceryItems.add(new GroceryItemImpl("Ground Beef", 2.5, "lbs"));
        mockGroceryItems.add(new GroceryItemImpl("Salt", 2.2, "tbsp"));

        return mockGroceryItems;
    }

    @Override
    public List<GroceryItem> getGroceryItemsForRecipe(long recipeId) { return ImmutableList.copyOf(groceryItemsList);}

    @Override
    public List<GroceryItem> saveGroceryItemsForRecipe(long recipeId, List<GroceryItem> newGroceryItem) {
        groceryItemsList.add((GroceryItemImpl) newGroceryItem);
        return ImmutableList.copyOf(groceryItemsList);
    }

    @Override
    public List<GroceryItem> updateGroceryItemsForRecipe(long recipeId, List<GroceryItem> updatedGroceryItems) {

        // TODO: Needs a major upgrade in terms of performance
        for(GroceryItem updatedGroceryItem: updatedGroceryItems) {
            if(groceryItemExists(updatedGroceryItem)) {
                groceryItemsList = groceryItemsList.stream()
                        .map( r -> {
                            if(r.getGroceryItemId() == updatedGroceryItem.getGroceryItemId())
                                return (GroceryItemImpl) updatedGroceryItem;
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

    @Override
    public boolean deleteAllGroceryItemsForRecipe(long recipeId) {
        // TODO: Implement
        return false;
    }

    @Override
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
