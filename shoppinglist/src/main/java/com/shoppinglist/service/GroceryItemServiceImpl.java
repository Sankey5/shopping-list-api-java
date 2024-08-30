package com.shoppinglist.service;

import com.shoppinglist.api.dao.GroceryItemDAO;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.service.GroceryItemService;
import com.shoppinglist.util.SQLExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class GroceryItemServiceImpl implements GroceryItemService {

    @Qualifier("groceryItemDAOJdbc")
    @Autowired GroceryItemDAO groceryItemDAO;

    @Override
    public List<GroceryItem> getGroceryItemsForRecipe(long recipeId) { return groceryItemDAO.getGroceryItemsForRecipe(recipeId); }

    @Override
    public List<GroceryItem> saveGroceryItemsForRecipe(Connection connection, long recipeId, List<GroceryItem> newGroceryItems) {

        try {
            return groceryItemDAO.saveGroceryItemsForRecipe(connection, recipeId, newGroceryItems);
        } catch (SQLException e) {
            SQLExceptionHandler.handle(e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<GroceryItem> updateGroceryItemsForRecipe(Connection connection, long recipeId, List<GroceryItem> updatedGroceryItems) {
        try {
            return groceryItemDAO.updateGroceryItemsForRecipe(connection, recipeId, updatedGroceryItems);
        } catch (SQLException e) {
            SQLExceptionHandler.handle(e);
            return new ArrayList<>();
        }
    }

    @Override
    public boolean deleteAllGroceryItemsForRecipe(Connection connection, long recipeId) {
        try {
            // TODO: Change to delete from GroceryList table first as a transaction
            return groceryItemDAO.deleteAllGroceryItemsForRecipe(connection, recipeId);
        } catch (SQLException e) {
            SQLExceptionHandler.handle(e);
            return false;
        }
    }

    @Override
    public boolean deleteGroceryItem(long groceryItemId) {
        // TODO: Change to delete from GroceryList first as a transaction
        return groceryItemDAO.deleteGroceryItem(groceryItemId);
    }

}
