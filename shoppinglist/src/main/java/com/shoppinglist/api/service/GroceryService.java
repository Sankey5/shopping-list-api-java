package com.shoppinglist.api.service;

import com.shoppinglist.api.model.GroceryItem;

import java.sql.Connection;
import java.util.List;

public interface GroceryService {

    List<GroceryItem> getGroceryItems(long recipeId);

    boolean saveGroceryItems(Connection connection, long recipeId, List<GroceryItem> newGroceryItems);

    boolean updateGroceryItems(Connection connection, long recipeId, List<GroceryItem> updatedGroceryItems);

    boolean deleteAllGroceryItems(Connection connection, long recipeId);

    boolean deleteGroceryItem(long groceryItemId);
}
