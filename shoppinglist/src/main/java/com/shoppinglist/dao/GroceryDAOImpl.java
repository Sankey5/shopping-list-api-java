package com.shoppinglist.dao;

import com.google.common.collect.ImmutableList;
import com.shoppinglist.api.dao.GroceryDAO;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.model.GroceryItemImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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

        // TODO: Change to a stream
        // If it can be found, update the item
        for(int i = 0; i < groceryItemsList.size(); i++) {
            if(groceryItemsList.get(i).equals(updatedGroceryItem)) {
                groceryItemsList.set(i, (GroceryItemImpl) updatedGroceryItem);
                return true;
            }
        }

        return false;
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
