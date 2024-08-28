package com.shoppinglist.api.service;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.shoppinglist.api.model.GroceryItem;

import java.sql.SQLException;
import java.util.List;

@JsonDeserialize(as = GroceryItem.class)
public interface GroceryListService {
    
    List<GroceryItem> getGroceryList() throws SQLException;

    List<GroceryItem> updateGroceryList(List<GroceryItem> updatedGroceryList);

    boolean deleteGroceryListItem(long groceryItemId);

    boolean deleteGroceryList();
}
