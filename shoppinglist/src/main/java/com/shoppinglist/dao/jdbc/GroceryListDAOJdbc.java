package com.shoppinglist.dao.jdbc;

import com.google.common.collect.ImmutableList;
import com.shoppinglist.api.dao.GroceryListDAO;
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
public class GroceryListDAOJdbc implements GroceryListDAO {

    // TODO: Maybe change to static-only class instead or figure out how to make it make sense to instantiate class.
    private GroceryListDAOJdbc() {
        super();
    }

    @Override
    public List<GroceryItem> getGroceryList() throws SQLException {

        final String sqlStatement = "SELECT * FROM ShoppingList";
        ArrayList<GroceryItemImpl> shoppingList = new ArrayList<>();

        try(Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlStatement)) {

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                GroceryItemImpl currGroceryItem = new GroceryItemImpl(
                        rs.getLong(1),
                        rs.getString(2),
                        BigDecimal.valueOf(rs.getDouble(3)),
                        rs.getString(4)
                );

                shoppingList.add(currGroceryItem);
            }

        }

        return ImmutableList.copyOf(shoppingList);
    }

    @Override
    public boolean saveGroceryList(List<GroceryItem> groceryList) {

        final String sqlStatement = "INSERT INTO ShoppingList (GroceryItemId, Name, Quantity, Measure) VALUES (?, ?, ?, ?)";

        try(Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlStatement)) {

            for(int i = 0; i < groceryList.size(); i++) {
                GroceryItem currGI = groceryList.get(i);

                ps.setLong(1, currGI.getId());
                ps.setString(2, currGI.getName());
                ps.setDouble(3, currGI.getQuantity().doubleValue());
                ps.setString(4, currGI.getMeasure());

                ps.addBatch();
            }

            // TODO: Check the int array for successful execution
            int[] returnVals = ps.executeBatch();
            for(int i = 0; i < returnVals.length; i++) {
                if (returnVals[i] == PreparedStatement.EXECUTE_FAILED)
                    return false;
            }
            return true;

        } catch (SQLException e) {
            SQLExceptionHandler.handle(e);
        }

        return false;
    }

    @Override
    public List<GroceryItem> updateGroceryList(List<GroceryItem> groceryList) {

        final String sqlStatement = "UPDATE ShoppingList SET Quantity = ? WHERE GroceryItemId = ?";

        try(Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlStatement)) {

            for (GroceryItem currGI : groceryList) {
                ps.setDouble(1, currGI.getQuantity().doubleValue());
                ps.setLong(2, currGI.getId());

                ps.addBatch();
            }

            int[] returnVals = ps.executeBatch();
            for (int returnVal : returnVals) {
                if (returnVal == Statement.EXECUTE_FAILED)
                    return List.of();
            }

            return this.getGroceryList();

        } catch (SQLException e) {
            SQLExceptionHandler.handle(e);
        }

        return List.of();
    }

    @Override
    public boolean deleteGroceryListItem(long groceryListId) {
        return false;
    }

    @Override
    public boolean deleteGroceryList() {
        return false;
    }

}
