package com.shoppinglist.dao.jdbc;

import com.google.common.collect.ImmutableList;
import com.shoppinglist.api.dao.GroceryItemDAO;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.model.GroceryItemImpl;
import com.shoppinglist.util.Database;
import com.shoppinglist.util.SQLExceptionHandler;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GroceryItemDAOJdbc implements GroceryItemDAO {

    // TODO: Maybe change to static-only class instead or figure out how to make it make sense to instantiate class.
    public GroceryItemDAOJdbc() {
        super();
    }

    @Override
    public List<GroceryItem> getGroceryItemsForRecipe(long recipeId) {
        final String sqlStatement = """
                                       SELECT GroceryItemId, Name, Quantity, Measure
                                       FROM GroceryItem
                                       WHERE RecipeId = ?
                                    """;
        ArrayList<GroceryItemImpl> groceryItemsList = new ArrayList<>();

        try(Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement(sqlStatement);) {

            ps.setLong(1, recipeId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                GroceryItemImpl currGI = new GroceryItemImpl(
                        rs.getLong("GroceryItemId"),
                        rs.getString("Name"),
                        BigDecimal.valueOf(rs.getDouble("Quantity")),
                        rs.getString("Measure")
                );

                groceryItemsList.add(currGI);
            }

        } catch (SQLException sqlException) {
            SQLExceptionHandler.handle(sqlException);
        }

        return ImmutableList.copyOf(groceryItemsList);
    }

    @Override
    public List<GroceryItem> saveGroceryItemsForRecipe(Connection connection, long recipeId, List<GroceryItem> newGroceryItems) throws SQLException {
        final String sqlInsertGroceries = "INSERT INTO GroceryItem (Name, Quantity, Measure, RecipeId) Values (?, ?, ?, " + recipeId + ")";

        try(PreparedStatement ps = connection.prepareStatement(sqlInsertGroceries);) {

            for (GroceryItem currGI : newGroceryItems) {
                ps.setString(1, currGI.getName());
                ps.setDouble(2, currGI.getQuantity().doubleValue());
                ps.setString(3, currGI.getMeasure());

                ps.addBatch();
            }

            int[] retVals = ps.executeBatch();

            if(Database.failedBatchExecution(retVals))
                return List.of();

        }

        return newGroceryItems;
    }

    @Override
    public List<GroceryItem> updateGroceryItemsForRecipe(Connection connection, long recipeId, List<GroceryItem> updatedGroceryItem) throws SQLException {
        final String sqlInsertGroceries = """
                                             UPDATE GroceryItem
                                             SET Name = ?, Quantity = ?, Measure = ?
                                             WHERE GroceryItemId = ?
                                          """;

        try(PreparedStatement ps = connection.prepareStatement(sqlInsertGroceries);) {

            for (GroceryItem currGI : updatedGroceryItem) {
                ps.setString(1, currGI.getName());
                ps.setDouble(2, currGI.getQuantity().doubleValue());
                ps.setString(3, currGI.getMeasure());
                ps.setLong(4, currGI.getId());

                ps.addBatch();
            }

            ps.executeBatch();
        }

        // TODO: Cheating. Need to ensure the grocery items were saved in the database correctly by pulling those same ones from the database.
        return updatedGroceryItem;
    }

    @Override
    public boolean deleteAllGroceryItemsForRecipe (Connection connection, long recipeId) throws SQLException {
        final String sqlDeleteGroceries = "DELETE FROM GroceryItem WHERE RecipeId = ?";

        try (PreparedStatement ps = connection.prepareStatement(sqlDeleteGroceries);) {
            ps.setLong(1, recipeId);
            ps.executeUpdate();
            return true;
        }
    }

    @Override
    public boolean deleteGroceryItem(long groceryItemId) {
        final String sqlDeleteGroceries = "DELETE FROM GroceryItem WHERE GroceryItemId = ?";

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
