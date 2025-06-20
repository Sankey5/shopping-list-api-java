package com.shoppinglist.api.service;

import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.model.Recipe;

import java.sql.Connection;
import java.util.List;

public interface GroceryItemService {

    List<GroceryItem> getGroceryItemsForRecipe(long recipeId);

    List<GroceryItem> saveGroceryItemsForRecipe(Recipe recipe, List<GroceryItem> newGroceryItems);

    List<GroceryItem> updateGroceryItemsForRecipe(Recipe recipe, List<GroceryItem> updatedGroceryItems);

    boolean deleteAllGroceryItemsForRecipe(long recipeId);

    boolean deleteGroceryItem(long groceryItemId);
}
