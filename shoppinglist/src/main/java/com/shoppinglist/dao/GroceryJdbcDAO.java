package com.shoppinglist.dao;

import com.google.common.collect.ImmutableList;
import com.shoppinglist.api.dao.GroceryDAO;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.model.GroceryItemImpl;
import com.shoppinglist.util.Database;
import com.shoppinglist.util.SQLExceptionHandler;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Repository
public class GroceryJdbcDAO implements GroceryDAO {

    // TODO: Maybe change to static-only class instead or figure out how to make it make sense to instantiate class.
    public GroceryJdbcDAO() {
        super();
    }

    @Override
    public List<GroceryItem> getGroceryItems(long recipeId) {
        final String sqlStatement = "SELECT * FROM GroceryItems WHERE RecipeId = ?";
        ArrayList<GroceryItemImpl> groceryItemsList = new ArrayList<>();

        try(Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement(sqlStatement);) {

            ps.setLong(1, recipeId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                GroceryItemImpl currGI = new GroceryItemImpl(
                        rs.getLong("GroceryItemId"),
                        rs.getString("Name"),
                        rs.getDouble("Quantity"),
                        rs.getString("Measure"),
                        rs.getLong("RecipeId")
                );

                groceryItemsList.add(currGI);
            }

        } catch (SQLException sqlException) {
            SQLExceptionHandler.handle(sqlException);
        }

        return ImmutableList.copyOf(groceryItemsList);
    }

    @Override
    public void saveGroceryItems(Connection connection, long recipeId, List<GroceryItem> newGroceryItems) throws SQLException {
        final String sqlInsertGroceries = "INSERT INTO GroceryItems (Name, Quantity, Measure, RecipeId) Values (?, ?, ?, ?)";

        try(PreparedStatement ps = connection.prepareStatement(sqlInsertGroceries);) {

            for(int i = 0; i < newGroceryItems.size(); i++) {
                GroceryItem currGI = newGroceryItems.get(i);

                ps.setString(1, currGI.getName());
                ps.setDouble(2, currGI.getQuantity());
                ps.setString(3, currGI.getMeasure());
                ps.setLong(4, recipeId); // Ignore sonarling in favor of sql injection possibility?
                ps.addBatch();
            }

            ps.executeBatch();
        }
    }

    @Override
    public void updateGroceryItems(Connection connection, long recipeId, List<GroceryItem> updatedGroceryItem) throws SQLException {
        final String sqlInsertGroceries = "MERGE INTO GroceryItems (Name, Quantity, Measure, RecipeId) Values (?, ?, ?) WHERE recipeId = ?";

        try(PreparedStatement ps = connection.prepareStatement(sqlInsertGroceries);) {

            for(int i = 0; i < updatedGroceryItem.size(); i++) {
                GroceryItem currGI = updatedGroceryItem.get(i);

                ps.setString(1, currGI.getName());
                ps.setDouble(2, currGI.getQuantity());
                ps.setString(3, currGI.getMeasure());
                ps.setLong(4, recipeId); // Ignore sonarling in favor of sql injection possibility?
                ps.addBatch();
            }

            ps.executeUpdate();
        }
    }

    @Override
    public void deleteAllGroceryItems (Connection connection, long recipeId) throws SQLException {
        final String sqlDeleteGroceries = "DELETE FROM GroceryItems WHERE RecipeId = ?";

        try (PreparedStatement ps = connection.prepareStatement(sqlDeleteGroceries);) {
            ps.setLong(1, recipeId);
            ps.executeUpdate();
        }
    }

    @Override
    public boolean deleteGroceryItem(long groceryItemId) {
        final String sqlDeleteGroceries = "DELETE FROM GroceryItems WHERE GroceryItemId = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlDeleteGroceries);) {

            ps.setLong(1, groceryItemId);
            ps.executeUpdate();

            return true;

        } catch (SQLException sqlException) {
            SQLExceptionHandler.handle(sqlException);
        }

        return false;
    }
}
