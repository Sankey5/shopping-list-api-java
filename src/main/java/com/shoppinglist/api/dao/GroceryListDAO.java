package com.shoppinglist.api.dao;

import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.model.GroceryItemImpl;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public interface GroceryListDAO extends JpaRepository<GroceryItemImpl, Long> {

    List<GroceryItem> getGroceryList();

    List<GroceryItem> addToGroceryList(List<GroceryItem> groceryList);

    boolean deleteGroceryListItem(long groceryItemId);

    boolean deleteAllOfGroceryListItem(long groceryItemId);

    boolean deleteAllGroceryListItems(List<GroceryItem> groceryListItems);

    boolean deleteGroceryList();

}
