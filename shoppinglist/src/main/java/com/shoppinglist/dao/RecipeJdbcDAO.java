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
import java.util.Optional;

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
    public Optional<Long> saveRecipe(Connection connection, Recipe newRecipe) {
        final String sqlQuery = "INSERT INTO Recipes (Name) VALUES (?)";

        try (PreparedStatement ps = connection.prepareStatement(sqlQuery, PreparedStatement.RETURN_GENERATED_KEYS);) {

            ps.setString(1, newRecipe.getName());
            int querySuccessful = ps.executeUpdate();

            if (querySuccessful != 1) {
                throw new SQLException("Could not insert the recipe");
            }

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return Optional.of(rs.getLong(1));

        } catch (SQLException e) {
            SQLExceptionHandler.handle(e);
            return Optional.empty();
        }
    }

    @Override
    public boolean updateRecipe(Connection connection, long recipeId, Recipe updatedRecipe) {
        final String sqlQuery = "UPDATE Recipes SET Name = ? WHERE RecipeId = ?";

        try(Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlQuery)) {

            ps.setString(1, updatedRecipe.getName());
            ps.setLong(2, recipeId);
            int querySuccessful = ps.executeUpdate();

            if (querySuccessful == 1)
                return true;

        } catch (SQLException sqlException) {
            SQLExceptionHandler.handle(sqlException);
        }

        return false;
    }

    @Override
    public boolean deleteRecipe(Connection connection, long recipeId) {
        final String sqlDeleteRecipes = "DELETE FROM Recipes WHERE RecipeId = ?";

        // Delete the parent recipe and rollback if there are any failures
        try (PreparedStatement psRecipe = connection.prepareStatement(sqlDeleteRecipes)) {

            psRecipe.setLong(1, recipeId);
            psRecipe.executeUpdate();
            return true;

        } catch (SQLException e) {
            SQLExceptionHandler.handle(e);
            return false;
        }
    }
}
