package com.shoppinglist.dao;

import com.google.common.collect.ImmutableList;
import com.shoppinglist.api.dao.GroceryDAO;
import com.shoppinglist.api.dao.RecipeDAO;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.model.Recipe;
import com.shoppinglist.model.RecipeImpl;
import com.shoppinglist.util.Database;
import com.shoppinglist.util.SQLExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RecipeJdbcDAO implements RecipeDAO {

    @Autowired
    private GroceryDAO groceryItemsDAO;

    public RecipeJdbcDAO() {
        //dbConnection = Database.getConnection();
    }

    @Override
    public List<Recipe> getRecipes() {
        final String getAllRecpies = "SELECT * FROM Recipes";
        List<RecipeImpl> recipesList = new ArrayList<>();

        try(Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement(getAllRecpies)) {

            int querySuccessful = ps.executeUpdate();

            if(querySuccessful <= 0) {
                return ImmutableList.copyOf(recipesList);
            }

            try(ResultSet rs = ps.getResultSet();) {

                while(rs.next()) {
                    RecipeImpl currRecpie = new RecipeImpl(
                            rs.getLong("RecipeId"),
                            rs.getString("Name")
                    );

                    recipesList.add(currRecpie);
                }

            } catch (SQLException sqlE) {
                SQLExceptionHandler.handle(sqlE);
            }


        } catch (SQLException sqlE) {
            SQLExceptionHandler.handle(sqlE);
        }

        return ImmutableList.copyOf(recipesList);
    }

    @Override
    public boolean saveRecipe(Recipe newRecipe) {
        return false;//recipesList.add((RecipeImpl) newRecipe);
    }

    @Override
    public boolean updateRecipe(Recipe updatedRecipe) {

//        // TODO: Change to a stream
//        // If it can be found, update the item
//        for(int i = 0; i < recipesList.size(); i++) {
//            if(recipesList.get(i).equals(updatedRecipe)) {
//                recipesList.set(i, (RecipeImpl) updatedRecipe);
//                return true;
//            }
//        }

        return false;
    }

    @Override
    public boolean deleteRecipe(String recipeName) {
//        for(int r = 0; r < recipesList.size(); r++) {
//            if(recipeName.equals(recipesList.get(r).getName())) {
//                recipesList.remove(r);
//                return true;
//            }
//        }
        return false;
    }
}
