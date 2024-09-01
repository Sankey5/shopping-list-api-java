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

        final String sqlStatement = """
                                    SELECT GroceryItem.GroceryItemId, GroceryItem.Name, GroceryItem.Quantity, GroceryItem.Measure
                                    FROM GroceryItem
                                    INNER JOIN GroceryList ON GroceryList.GroceryItemId = GroceryItem.GroceryItemId
                                    """;
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
    public List<GroceryItem> addToGroceryList(List<GroceryItem> groceryList) {

        final String sqlStatement = "INSERT INTO GroceryList (GroceryItemId) VALUES (?)";

        try(Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlStatement)) {

            connection.setAutoCommit(false);

            for (GroceryItem currGI : groceryList) {
                ps.setLong(1, currGI.getId());
                ps.addBatch();
            }

            int[] returnVals = ps.executeBatch();

            for (int returnVal : returnVals) {
                if (returnVal == PreparedStatement.EXECUTE_FAILED) {
                    connection.rollback();
                    return List.of();
                }
            }

            connection.commit();
            return groceryList;

        } catch (SQLException e) {
            SQLExceptionHandler.handle(e);
        }

        return List.of();
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

            if(Database.failedBatchExecution(returnVals)) {
                connection.rollback();
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
