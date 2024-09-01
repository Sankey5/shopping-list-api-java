package com.shoppinglist.api.dao;

import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.model.GroceryListItem;

import java.sql.SQLException;
import java.util.List;

public interface GroceryListDAO {

    List<GroceryListItem> getGroceryList();

    List<GroceryListItem> addToGroceryList(List<GroceryItem> groceryList);

    boolean deleteGroceryListItem(long groceryItemId);

    boolean deleteAllOfGroceryListItem(long groceryListId);

    boolean deleteGroceryList();

}
