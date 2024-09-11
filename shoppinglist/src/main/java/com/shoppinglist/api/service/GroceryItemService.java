package com.shoppinglist.api.service;

import com.shoppinglist.api.model.GroceryItem;

import java.sql.Connection;
import java.util.List;

public interface GroceryItemService {

    List<GroceryItem> getGroceryItemsForRecipe(long recipeId);

    List<GroceryItem> saveGroceryItemsForRecipe(long recipeId, List<GroceryItem> newGroceryItems);

    List<GroceryItem> updateGroceryItemsForRecipe(long recipeId, List<GroceryItem> updatedGroceryItems);

    boolean deleteAllGroceryItemsForRecipe(long recipeId);

    boolean deleteGroceryItem(long groceryItemId);
}
