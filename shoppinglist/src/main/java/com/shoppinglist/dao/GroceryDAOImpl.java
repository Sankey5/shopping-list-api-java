package com.shoppinglist.dao;

import com.google.common.collect.ImmutableList;
import com.shoppinglist.api.dao.GroceryDAO;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.model.Recipe;
import com.shoppinglist.model.GroceryItemImpl;
import com.shoppinglist.model.RecipeImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class GroceryDAOImpl implements GroceryDAO {

    private static List<GroceryItemImpl> groceryItemsList;

    static {
        groceryItemsList = populateGroceryItems();
    }

    private static List<GroceryItemImpl> populateGroceryItems() {
        List<GroceryItemImpl> mockGroceryItems = new ArrayList<>();

        mockGroceryItems.add(new GroceryItemImpl("Bananas", 1.0, "lbs"));
        mockGroceryItems.add(new GroceryItemImpl("Ground Beef", 2.5, "lbs"));
        mockGroceryItems.add(new GroceryItemImpl("Salt", 2.2, "tpbs"));

        return mockGroceryItems;
    }

    @Override
    public List<GroceryItem> getGroceryItems() { return ImmutableList.copyOf(groceryItemsList);}

    @Override
    public boolean saveGroceryItem(GroceryItem newGroceryItem) {
        return groceryItemsList.add((GroceryItemImpl) newGroceryItem);
    }

    @Override
    public boolean updateGroceryItem(GroceryItem updatedGroceryItem) {

        if(groceryItemExists(updatedGroceryItem)) {
            groceryItemsList = groceryItemsList.stream()
                    .map( r -> {
                        if(r.getId() == updatedGroceryItem.getId())
                            return (GroceryItemImpl) updatedGroceryItem;
                        return r;
                    })
                    .collect(Collectors.toList());
            return true;
        }

        return false;
    }

    private boolean groceryItemExists(GroceryItem groceryItem) {
        return groceryItemsList.stream()
                .filter( r -> r.equals(groceryItem))
                .findFirst()
                .isEmpty();
    }

    @Override
    public boolean deleteGroceryItem(String groceryItemName) {
        for(int r = 0; r < groceryItemsList.size(); r++) {
            if(groceryItemName.equals(groceryItemsList.get(r).getName())) {
                groceryItemsList.remove(r);
                return true;
            }
        }
        return false;
    }
}
