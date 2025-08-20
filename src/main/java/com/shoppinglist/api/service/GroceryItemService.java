package com.shoppinglist.api.service;

import com.shoppinglist.model.GroceryItem;
import com.shoppinglist.model.Recipe;

import java.util.List;

public interface GroceryItemService {

    List<GroceryItem> getGroceryItemsForRecipe(long recipeId);

    List<GroceryItem> saveGroceryItemsForRecipe(Recipe recipe, List<GroceryItem> newGroceryItems);

    List<GroceryItem> updateGroceryItemsForRecipe(Recipe recipe, List<GroceryItem> updatedGroceryItems);

    void deleteAllGroceryItemsForRecipe(long recipeId);

    void deleteGroceryItem(long groceryItemId);
}
