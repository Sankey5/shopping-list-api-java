package com.shoppinglist.api.dao;

import com.shoppinglist.api.model.GroceryItem;

import java.sql.SQLException;
import java.util.List;

public interface GroceryListDAO {

    List<GroceryItem> getGroceryList();

    List<GroceryItem> addToGroceryList(List<GroceryItem> groceryList);

    boolean deleteGroceryListItem(long groceryItemId);

    boolean deleteAllOfGroceryListItem(long groceryItemId);

    boolean deleteAllGroceryListItems(List<GroceryItem> groceryListItems);

    boolean deleteGroceryList();

}
