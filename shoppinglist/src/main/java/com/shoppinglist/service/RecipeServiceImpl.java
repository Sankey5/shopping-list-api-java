package com.shoppinglist.service;

import com.shoppinglist.api.dao.RecipeDAO;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.model.Recipe;
import com.shoppinglist.api.service.GroceryItemService;
import com.shoppinglist.api.service.RecipeService;
import com.shoppinglist.util.Database;
import com.shoppinglist.util.SQLExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    @Qualifier("recipeJdbcDAO")
    RecipeDAO recipeDAO;
    @Autowired
    GroceryItemService groceryItemService;

    @Override
    public List<Recipe> getRecipes() {return recipeDAO.getRecipes();}

    @Override
    public boolean saveRecipe(Recipe newRecipe) {
        long recipeId;
        List<GroceryItem> groceryItems = newRecipe.getGroceryItems();

        try(Connection connection = Database.getConnection();) {

            connection.setAutoCommit(false);
            Optional<Long> generatedKeysOpt = recipeDAO.saveRecipe(connection, newRecipe);

            // TODO: This could be solved more elegantly,but might not be more readable
            if(generatedKeysOpt.isEmpty()) {
                connection.rollback();
                return false;
            }
            recipeId = generatedKeysOpt.get();

            if(!groceryItemService.saveGroceryItemsForRecipe(connection, recipeId, groceryItems)) {
                connection.rollback();
                return false;
            }

            connection.commit();

            return true;

        } catch (SQLException e) {
            SQLExceptionHandler.handle(e);
        }

        return false;
    }

    @Override
    public boolean updateRecipe(long recipeId, Recipe updatedRecipe) {

        try(Connection connection = Database.getConnection();) {

            connection.setAutoCommit(false);

            if(!recipeDAO.updateRecipe(connection, recipeId, updatedRecipe)) {
                connection.rollback();
                return false;
            }

            if (!groceryItemService.updateGroceryItemsForRecipe(connection, recipeId, updatedRecipe.getGroceryItems())) {
                connection.rollback();
                return false;
            }

            connection.commit();
            return true;

        } catch (SQLException e) {
            SQLExceptionHandler.handle(e);
        }

        return false;
    }

    @Override
    public boolean deleteRecipe(long recipeId) {

        try(Connection con = Database.getConnection();) {

            con.setAutoCommit(false);

            boolean successfulDelete = groceryItemService.deleteAllGroceryItemsForRecipe(con, recipeId);

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
