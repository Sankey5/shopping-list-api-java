package com.shoppinglist.api.dao;

import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.model.Recipe;

import java.util.List;

public interface GroceryDAO {
    List<GroceryItem> getGroceryItems(long RecipeId);

    boolean saveGroceryItem(GroceryItem newGroceryItem);

    boolean updateGroceryItem(GroceryItem updatedGroceryItem);

    boolean deleteGroceryItem(String groceryItemName);
}
