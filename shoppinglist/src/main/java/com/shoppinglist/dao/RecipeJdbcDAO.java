package com.shoppinglist.dao;

import com.google.common.collect.ImmutableList;
import com.shoppinglist.api.dao.RecipeDAO;
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

    // TODO: Maybe change to static-only class instead or figure out how to make it make sense to instantiate class.
    public RecipeJdbcDAO() {
        super();
    }

    @Override
    public List<Recipe> getRecipes() {
        final String sqlQuery = "SELECT * FROM Recipes";
        List<RecipeImpl> recipesList = new ArrayList<>();

        try(Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlQuery)) {

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                RecipeImpl currRecipe = new RecipeImpl(
                        rs.getLong("RecipeId"),
                        rs.getString("Name"));

                recipesList.add(currRecipe);
            }

        } catch (SQLException sqlE) {
            SQLExceptionHandler.handle(sqlE);
        }

        return ImmutableList.copyOf(recipesList);
    }

    @Override
    public boolean saveRecipe(Recipe newRecipe) {
        final String sqlQuery = "INSERT INTO Recipes (Name) VALUES (?)";

        try(Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlQuery)) {

            ps.setString(1, newRecipe.getName());
            int querySuccessful = ps.executeUpdate();

            if (querySuccessful == 1)
                return true;

        } catch (SQLException sqlException) {
            SQLExceptionHandler.handle(sqlException);
        }

        return false;
    }

    @Override
    public boolean updateRecipe(Recipe updatedRecipe) {
        final String sqlQuery = "MERGE INTO Recipes (Name) VALUES (?) WHERE RecipeId = ?";

        try(Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlQuery)) {

            ps.setString(1, updatedRecipe.getName());
            ps.setLong(2, updatedRecipe.getId());
            int querySuccessful = ps.executeUpdate();

            if (querySuccessful == 1)
                return true;

        } catch (SQLException sqlException) {
            SQLExceptionHandler.handle(sqlException);
        }

        return false;
    }

    @Override
    public boolean deleteRecipe(long recipeId) {
        final String sqlQuery = "DELETE FROM Recipes WHERE RecipeId = ?";

        try(Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlQuery)) {

            ps.setLong(1, recipeId);
            int querySuccessful = ps.executeUpdate();

            if (querySuccessful == 1)
                return true;

        } catch (SQLException sqlException) {
            SQLExceptionHandler.handle(sqlException);
        }

        return false;
    }
}
