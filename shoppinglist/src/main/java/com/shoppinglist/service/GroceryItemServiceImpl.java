package com.shoppinglist.service;

import com.shoppinglist.api.dao.GroceryItemDAO;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.service.GroceryItemService;
import com.shoppinglist.util.SQLExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Component
public class GroceryItemServiceImpl implements GroceryItemService {

    @Autowired
    GroceryItemDAO groceryItemDAO;

    @Override
    public List<GroceryItem> getGroceryItemsForRecipe(long recipeId) { return groceryItemDAO.getGroceryItemsForRecipe(recipeId); }

    @Override
    public boolean saveGroceryItemsForRecipe(Connection connection, long recipeId, List<GroceryItem> newGroceryItems) {

        try {
            groceryItemDAO.saveGroceryItemsForRecipe(connection, recipeId, newGroceryItems);
            return true;
        } catch (SQLException e) {
            SQLExceptionHandler.handle(e);
            return false;
        }
    }

    @Override
    public boolean updateGroceryItemsForRecipe(Connection connection, long recipeId, List<GroceryItem> updatedGroceryItems) {
        try {

            //Create a grocery item if there is not one already created
            createGroceryItemsIfNotExists(updatedGroceryItems);

            groceryItemDAO.updateGroceryItemsForRecipe(connection, recipeId, updatedGroceryItems);
            return true;
        } catch (SQLException e) {
            SQLExceptionHandler.handle(e);
            return false;
        }
    }

    @Override
    public boolean deleteAllGroceryItemsForRecipe(Connection connection, long recipeId) {
        try {
            groceryItemDAO.deleteAllGroceryItemsForRecipe(connection, recipeId);
            return true;
        } catch (SQLException e) {
            SQLExceptionHandler.handle(e);
            return false;
        }
    }

    @Override
    public boolean deleteGroceryItem(long groceryItemId) { return groceryItemService.deleteGroceryItem(groceryItemId); }

    private void createGroceryItemsIfNotExists(List <GroceryItem> updatedGroceryItems) throws SQLException{
        List<GroceryItem> currentGroceryItems = groceryItemService.getGroceryItems();

        // TODO: Check to make sure the predicate is sound
        List<GroceryItem> newGroceryItems = updatedGroceryItems.stream()
                .filter( groceryItem -> groceryItem.getId() == 0 || !currentGroceryItems.contains(groceryItem))
                .toList();

        groceryItemService.createGroceryItems(newGroceryItems);
    }
}
