package com.shoppinglist.api.dao;

import com.shoppinglist.api.model.GroceryItem;

import java.sql.SQLException;
import java.util.List;

public interface GroceryListDAO {

    List<GroceryItem> getGroceryList() throws SQLException;

    boolean saveGroceryList(List<GroceryItem> groceryList);

    List<GroceryItem> updateGroceryList(List<GroceryItem> groceryList);

    boolean deleteGroceryListItem(long groceryItemId);

    boolean deleteGroceryList();

}
