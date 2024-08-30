package com.shoppinglist.api.service;

import com.shoppinglist.api.model.GroceryItem;

import java.sql.Connection;
import java.util.List;

public interface GroceryItemService {

    List<GroceryItem> getGroceryItemsForRecipe(long recipeId);

    boolean saveGroceryItemsForRecipe(Connection connection, long recipeId, List<GroceryItem> newGroceryItems);

    boolean updateGroceryItemsForRecipe(Connection connection, long recipeId, List<GroceryItem> updatedGroceryItems);

    boolean deleteAllGroceryItemsForRecipe(Connection connection, long recipeId);

    boolean deleteGroceryItem(long groceryItemId);
}