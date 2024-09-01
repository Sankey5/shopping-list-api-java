package com.shoppinglist.dao.jdbc;

import com.google.common.collect.ImmutableList;
import com.shoppinglist.api.dao.GroceryListDAO;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.model.GroceryListItem;
import com.shoppinglist.model.GroceryItemImpl;
import com.shoppinglist.model.GroceryListItemImpl;
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
    public List<GroceryListItem> getGroceryList() {

        // TODO: Understand how to return grocery list item ID as well
        final String sqlStatement = """
                                    SELECT GroceryItem.GroceryItemId, GroceryItem.Name, GroceryItem.Quantity, GroceryItem.Measure, GroceryList.GroceryListItemId
                                    FROM GroceryItem
                                    INNER JOIN GroceryList ON GroceryList.GroceryItemId = GroceryItem.GroceryItemId
                                    """;
        ArrayList<GroceryListItem> shoppingList = new ArrayList<>();

        try(Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlStatement)) {

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                GroceryListItemImpl currGroceryItem = new GroceryListItemImpl(
                        rs.getLong(1),
                        rs.getString(2),
                        BigDecimal.valueOf(rs.getDouble(3)),
                        rs.getString(4),
                        rs.getLong(5)
                );

                shoppingList.add(currGroceryItem);
            }

        } catch (SQLException e) {
            SQLExceptionHandler.handle(e);
        }

        return ImmutableList.copyOf(shoppingList);
    }

    @Override
    public List<GroceryListItem> addToGroceryList(List<GroceryItem> groceryList) {

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
            return this.getGroceryList();

        } catch (SQLException e) {
            SQLExceptionHandler.handle(e);
        }

        return List.of();
    }

    @Override
    public boolean deleteGroceryListItem(long groceryListId) {
        String sqlStatement = "DELETE FROM GroceryList WHERE GroceryItemId = ? FETCH FIRST ROW ONLY";

        try(Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlStatement);) {

            connection.setAutoCommit(false);

            ps.setLong(1, groceryListId);
            int deletedGroceryListItems = ps.executeUpdate();

            if(deletedGroceryListItems != 1) {
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
    public boolean deleteAllOfGroceryListItem(long groceryListId) {
        String sqlStatement = "DELETE FROM GroceryList WHERE GroceryItemId = ?";

        try(Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlStatement);) {

            connection.setAutoCommit(false);

            ps.setLong(1, groceryListId);
            int deletedGroceryListItems = ps.executeUpdate();

            if(deletedGroceryListItems > 1) {
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
    public boolean deleteGroceryList() {
        String sqlStatement = "DELETE FROM GroceryList";

        try(Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlStatement);) {

            int deletedGroceryListItems = ps.executeUpdate();

            return deletedGroceryListItems > 0;

        } catch (SQLException e) {
            SQLExceptionHandler.handle(e);
        }

        return false;
    }

}
