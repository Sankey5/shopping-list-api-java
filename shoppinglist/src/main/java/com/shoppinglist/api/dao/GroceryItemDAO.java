package com.shoppinglist.api.dao;

import com.shoppinglist.api.model.GroceryItem;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GroceryItemDAO {
    List<GroceryItem> getGroceryItemsForRecipe(long recipeId);

    List<GroceryItem> saveGroceryItemsForRecipe(long recipeId, List<GroceryItem> updatedGroceryItems) throws SQLException;

    List<GroceryItem> updateGroceryItemsForRecipe(long recipeId, List<GroceryItem> updatedGroceryItems) throws SQLException;

    boolean deleteAllGroceryItemsForRecipe(long recipeId) throws SQLException;

    boolean deleteGroceryItem(long groceryItemId);
}
