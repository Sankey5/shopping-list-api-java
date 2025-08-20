package com.shoppinglist.api.service;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.shoppinglist.model.GroceryItem;

import java.util.List;

@JsonDeserialize(as = GroceryItem.class)
public interface GroceryListService {
    
    List<GroceryItem> getGroceryList();

    List<GroceryItem> addToGroceryList(List<GroceryItem> newGroceryList);

    void deleteGroceryListItem(long groceryItemId);

    void deleteAllOfGroceryListItem(long groceryItemId);

    void deleteAllGroceryListItems(List<GroceryItem> groceryListItemIds);

    void deleteGroceryList();
}
