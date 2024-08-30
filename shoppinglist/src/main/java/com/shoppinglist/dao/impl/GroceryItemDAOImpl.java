package com.shoppinglist.dao.impl;

import com.google.common.collect.ImmutableList;
import com.shoppinglist.api.dao.GroceryItemDAO;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.model.GroceryItemImpl;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Connection;
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

        mockGroceryItems.add(new GroceryItemImpl("Bananas", new BigDecimal("1.0"), "lbs"));
        mockGroceryItems.add(new GroceryItemImpl("Ground Beef", new BigDecimal("2.5"), "lbs"));
        mockGroceryItems.add(new GroceryItemImpl("Salt", new BigDecimal("2.2"), "tpbs"));

        return mockGroceryItems;
    }

    @Override
    public List<GroceryItem> getGroceryItemsForRecipe(long recipeId) { return ImmutableList.copyOf(groceryItemsList);}

    @Override
    public void saveGroceryItemsForRecipe(Connection connection, long recipeId, List<GroceryItem> newGroceryItem) {
        groceryItemsList.add((GroceryItemImpl) newGroceryItem);
    }

    @Override
    public void updateGroceryItemsForRecipe(Connection connection, long recipeId, List<GroceryItem> updatedGroceryItems) {

        // TODO: Needs a major upgrade in terms of performance
        for(GroceryItem updatedGroceryItem: updatedGroceryItems) {
            if(groceryItemExists(updatedGroceryItem)) {
                groceryItemsList = groceryItemsList.stream()
                        .map( r -> {
                            if(r.getId() == updatedGroceryItem.getId())
                                return (GroceryItemImpl) updatedGroceryItem;
                            return r;
                        })
                        .collect(Collectors.toList());
            }
        }

    }

    private boolean groceryItemExists(GroceryItem groceryItem) {
        return groceryItemsList.stream()
                .filter( r -> r.equals(groceryItem))
                .findFirst()
                .isEmpty();
    }

    @Override
    public void deleteAllGroceryItemsForRecipe(Connection connection, long recipeId) {


    }

    @Override
    public boolean deleteGroceryItem(long groceryItemId) {
        for(int r = 0; r < groceryItemsList.size(); r++) {
            if(groceryItemId == groceryItemsList.get(r).getId()) {
                groceryItemsList.remove(r);
                return true;
            }
        }
        return false;
    }
}
