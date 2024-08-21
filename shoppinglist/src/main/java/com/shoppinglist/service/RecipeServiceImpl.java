package com.shoppinglist.service;

import com.shoppinglist.api.dao.GroceryDAO;
import com.shoppinglist.api.dao.RecipeDAO;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.model.Recipe;
import com.shoppinglist.api.service.GroceryService;
import com.shoppinglist.api.service.RecipeService;
import com.shoppinglist.util.Database;
import com.shoppinglist.util.SQLExceptionHandler;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    @Qualifier("recipeJdbcDAO")
    RecipeDAO recipeDAO;
    @Autowired
    GroceryService groceryService;

    @Override
    public List<Recipe> getRecipes() {return recipeDAO.getRecipes();}

    @Override
    public boolean saveRecipe(Recipe newRecipe) {
        long recipeId = newRecipe.getId();
        List<GroceryItem> groceryItems = newRecipe.getGroceryItems();

        try(Connection connection = Database.getConnection();) {

            connection.setAutoCommit(false);
            Optional<Long> generatedKeysOpt = recipeDAO.saveRecipe(connection, newRecipe);

            if(generatedKeysOpt.isEmpty()) {
                connection.rollback();
                return false;
            }

            if(!groceryService.saveGroceryItems(connection, recipeId, groceryItems)) {
                connection.rollback();
                return false;
            }

            return true;

        } catch (SQLException e) {
            SQLExceptionHandler.handle(e);
        }

        return false;
    }

    @Override
    public boolean updateRecipe(Recipe updatedRecipe) {

        return false;
    }

    @Override
    public boolean deleteRecipe(long recipeId) {

        try(Connection con = Database.getConnection();) {

            con.setAutoCommit(false);

            boolean successfulDelete = groceryService.deleteAllGroceryItems(con, recipeId);

            if (!successfulDelete) {
                con.rollback();
                return false;
            }

            successfulDelete = recipeDAO.deleteRecipe(con, recipeId);

            if(!successfulDelete) {
                con.rollback();
                return false;
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            SQLExceptionHandler.handle(e);
        }

        return false;
    }
}
