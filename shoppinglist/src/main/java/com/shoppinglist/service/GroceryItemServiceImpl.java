package com.shoppinglist.service;

import com.shoppinglist.api.dao.GroceryDAO;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.service.GroceryService;
import com.shoppinglist.util.SQLExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Component
public class GroceryItemServiceImpl implements GroceryService {

    @Autowired
    @Qualifier("groceryJdbcDAO")
    GroceryDAO groceryItemDAO;

    @Override
    public List<GroceryItem> getGroceryItems(long recipeId) { return groceryItemDAO.getGroceryItems(recipeId); }

    @Override
    public boolean saveGroceryItems(Connection connection, long recipeId, List<GroceryItem> newGroceryItems) {

        try {
            groceryItemDAO.saveGroceryItems(connection, recipeId, newGroceryItems);
            return true;
        } catch (SQLException e) {
            SQLExceptionHandler.handle(e);
            return false;
        }
    }

    @Override
    public boolean updateGroceryItems(Connection connection, long recipeId, List<GroceryItem> updatedGroceryItems) {

        return false;
    }

    @Override
    public boolean deleteAllGroceryItems(Connection connection, long recipeId) {
        try {
            groceryItemDAO.deleteAllGroceryItems(connection, recipeId);
            return true;
        } catch (SQLException e) {
            SQLExceptionHandler.handle(e);
            return false;
        }
    }

    @Override
    public boolean deleteGroceryItem(long groceryItemId) { return groceryItemDAO.deleteGroceryItem(groceryItemId);}
}
