package com.shoppinglist.api.service;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.shoppinglist.api.model.GroceryItem;

import java.sql.SQLException;
import java.util.List;

@JsonDeserialize(as = GroceryItem.class)
public interface GroceryListService {
    
    List<GroceryItem> getGroceryList();

    List<GroceryItem> addToGroceryList(List<GroceryItem> newGroceryList);

    boolean deleteGroceryListItem(long groceryItemId);

    boolean deleteAllOfGroceryListItem(long groceryItemId);

    boolean deleteGroceryList();
}
