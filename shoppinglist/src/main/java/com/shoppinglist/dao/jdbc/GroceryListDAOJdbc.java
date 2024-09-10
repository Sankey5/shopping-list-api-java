package com.shoppinglist.dao.jdbc;

import com.google.common.collect.ImmutableList;
import com.shoppinglist.api.dao.GroceryListDAO;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.model.GroceryItemImpl;
import com.shoppinglist.util.DataAccessExceptionHandler;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GroceryListDAOJdbc implements GroceryListDAO {

    @Override
    public List<GroceryItem> getGroceryList() {

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

        } catch (SQLException e) {
            DataAccessExceptionHandler.handle(e);
            return List.of();
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

            if (Database.failedBatchExecution(returnVals)) {
                connection.rollback();
                return List.of();
            }

            connection.commit();
            return groceryList;

        } catch (SQLException e) {
            DataAccessExceptionHandler.handle(e);
        }

        return List.of();
    }

    @Override
    public boolean deleteGroceryListItem(long groceryItemId) {
        final String sqlStatement = "DELETE FROM GroceryList WHERE GroceryItemId = ? FETCH FIRST ROW ONLY";

        try(Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlStatement)) {

            connection.setAutoCommit(false);

            ps.setLong(1, groceryItemId);
            int deletedItems = ps.executeUpdate();

            if (deletedItems != 1) {
                connection.rollback();
                return false;
            }

            connection.commit();
            return true;

        } catch (SQLException e) {
            DataAccessExceptionHandler.handle(e);
        }

        return false;
    }

    @Override
    public boolean deleteAllOfGroceryListItem(long groceryItemId) {
        final String sqlStatement = "DELETE FROM GroceryList WHERE GroceryItemId = ?";

        try(Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlStatement)) {

            connection.setAutoCommit(false);

            ps.setLong(1, groceryItemId);
            int deletedItems = ps.executeUpdate();

            if (deletedItems < 1) {
                connection.rollback();
                return false;
            }

            connection.commit();
            return true;

        } catch (SQLException e) {
            DataAccessExceptionHandler.handle(e);
        }

        return false;
    }

    @Override
    public boolean deleteGroceryList() {
        final String sqlStatement = "DELETE FROM GroceryList";

        try(Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlStatement)) {

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            DataAccessExceptionHandler.handle(e);
        }

        return false;
    }

}
