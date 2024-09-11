package com.shoppinglist.service;

import com.shoppinglist.api.dao.GroceryItemDAO;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.service.GroceryItemService;
import com.shoppinglist.api.service.GroceryListService;
import com.shoppinglist.util.DataAccessExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class GroceryItemServiceImpl implements GroceryItemService {

    @Qualifier("groceryItemDAOJdbc")
    @Autowired GroceryItemDAO groceryItemDAO;
    @Autowired GroceryListService groceryListService;

    @Override
    public List<GroceryItem> getGroceryItemsForRecipe(long recipeId) {
        return groceryItemDAO.getGroceryItemsForRecipe(recipeId);
    }

    @Override public List<GroceryItem> saveGroceryItemsForRecipe(long recipeId, List<GroceryItem> newGroceryItems) {
        newGroceryItems = removeEmptyGroceryItems(newGroceryItems);
        return groceryItemDAO.saveGroceryItemsForRecipe(recipeId, newGroceryItems);
    }

    @Transactional
    @Override public List<GroceryItem> updateGroceryItemsForRecipe(long recipeId, List<GroceryItem> updatedGroceryItems) {
        updatedGroceryItems = removeEmptyGroceryItems(updatedGroceryItems);
        return groceryItemDAO.updateGroceryItemsForRecipe(recipeId, updatedGroceryItems);
    }

    @Override public boolean deleteAllGroceryItemsForRecipe(long recipeId) {

        List<Long> groceryItemIds = groceryItemDAO.getGroceryItemsForRecipe(recipeId).stream()
                .map(GroceryItem::getId).toList();

        return groceryListService.deleteAllGroceryListItems(groceryItemIds) &&
                groceryItemDAO.deleteAllGroceryItemsForRecipe(recipeId);
    }

    @Transactional
    @Override public boolean deleteGroceryItem(long groceryItemId) {
        return groceryListService.deleteGroceryListItem(groceryItemId) &&
                groceryItemDAO.deleteGroceryItem(groceryItemId);
    }

    private List<GroceryItem> removeEmptyGroceryItems(List<GroceryItem> groceryItems) {
        return groceryItems.stream()
                .filter(groceryItem -> !Objects.isNull(groceryItem) && !groceryItem.isAllDefault())
                .toList();
    }

}
