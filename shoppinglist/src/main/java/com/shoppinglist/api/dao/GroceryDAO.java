package com.shoppinglist.api.dao;

import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.model.Recipe;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GroceryDAO {
    List<GroceryItem> getGroceryItems(long RecipeId);

    void saveGroceryItems(Connection connection, long recipeId, List<GroceryItem> updatedGroceryItems) throws SQLException;

    void updateGroceryItems(Connection connection, long recipeId, List<GroceryItem> updatedGroceryItems) throws SQLException;

    void deleteAllGroceryItems(Connection connection, long recipeId) throws SQLException;

    boolean deleteGroceryItem(long groceryItemId);
}
