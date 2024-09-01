package com.shoppinglist.api.service;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.model.GroceryListItem;

import java.sql.SQLException;
import java.util.List;

@JsonDeserialize(as = GroceryItem.class)
public interface GroceryListService {
    
    List<GroceryListItem> getGroceryList();

    List<GroceryListItem> addToGroceryList(List<GroceryItem> newGroceryList);

    boolean deleteGroceryListItem(long groceryListItemId);

    boolean deleteAllOfGroceryListItem(long groceryItemId);

    boolean deleteGroceryList();
}
