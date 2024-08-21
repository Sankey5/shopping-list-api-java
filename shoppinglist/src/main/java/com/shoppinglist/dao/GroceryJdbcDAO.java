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
    public void saveGroceryItems(Connection connection, List<GroceryItem> newGroceryItems) throws SQLException {


    }

    @Override
    public void updateGroceryItems(Connection connection, List<GroceryItem> updatedGroceryItem) throws SQLException {

    }

    @Override
    public void deleteAllGroceryItems (Connection connection, long recipeId) throws SQLException {
        final String sqlDeleteGroceries = "DELETE FROM GroceryItem WHERE RecipeId = ?";

        try (PreparedStatement ps = connection.prepareStatement(sqlDeleteGroceries);) {
            ps.setLong(1, recipeId);
            ps.executeUpdate();
        }
    }

    @Override
    public boolean deleteGroceryItem(long groceryItemId) {


        return false;
    }
}
