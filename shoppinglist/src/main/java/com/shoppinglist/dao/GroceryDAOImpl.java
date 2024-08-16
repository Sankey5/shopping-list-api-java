package com.shoppinglist.dao;

import com.google.common.collect.ImmutableList;
import com.shoppinglist.api.dao.GroceryDAO;
import com.shoppinglist.api.model.Recipe;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.model.GroceryItemImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GroceryDAOImpl implements GroceryDAO {

    private static  List<GroceryItemImpl> groceriesList = new ArrayList<>();

    static {
        groceriesList = populateGroceryItems();
    }

    private static List<GroceryItemImpl> populateGroceryItems() {
        List<GroceryItemImpl> mockGroceryItems = new ArrayList<>();

        mockGroceryItems.add(new GroceryItemImpl("Bananas", 1.0, "lbs"));
        mockGroceryItems.add(new GroceryItemImpl("Ground Beef", 2.5, "lbs"));
        mockGroceryItems.add(new GroceryItemImpl("Salt", 2.2, "tpbs"));

        return mockGroceryItems;
    }

    @Override
    public List<GroceryItem> getGroceryItems() { return ImmutableList.copyOf(groceriesList);}

    @Override
    public boolean saveGroceryItem(GroceryItem newGroceryItem) {
        return groceriesList.add((GroceryItemImpl) newGroceryItem);
    }

    @Override
    public boolean updateGroceryItem(GroceryItem updatedGroceryItem) {

        // TODO: Change to a stream
        // If it can be found, update the item
        for(int i = 0; i < groceriesList.size(); i++) {
            if(groceriesList.get(i).equals(updatedGroceryItem)) {
                groceriesList.set(i, (GroceryItemImpl) updatedGroceryItem);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean deleteGroceryItem(String groceryItemName) {
        for(int r = 0; r < groceriesList.size(); r++) {
            if(groceryItemName.equals(groceriesList.get(r).getName())) {
                groceriesList.remove(r);
                return true;
            }
        }
        return false;
    }
}
