package com.shoppinglist.dao.jdbc;

import com.google.common.collect.ImmutableList;
import com.shoppinglist.api.dao.GroceryListDAO;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.model.GroceryItemImpl;
import com.shoppinglist.util.BatchExecutionHelper;
import com.shoppinglist.util.DataAccessExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class GroceryListDAOJdbc implements GroceryListDAO {

    private JdbcTemplate jdbcTemplate;

    public GroceryListDAOJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<GroceryItem> getGroceryList() {

        final String sqlStatement = """
                                    SELECT GroceryItem.GroceryItemId AS id,
                                            GroceryItem.Name AS name,
                                            GroceryItem.Quantity AS quantity,
                                            GroceryItem.Measure AS measure
                                    FROM GroceryItem
                                    INNER JOIN GroceryList ON GroceryList.GroceryItemId = GroceryItem.GroceryItemId
                                    """;

        List<GroceryItemImpl> shoppingList = jdbcTemplate.queryForList(sqlStatement, GroceryItemImpl.class);

        return ImmutableList.copyOf(shoppingList);
    }

    @Override
    public List<GroceryItem> addToGroceryList(List<GroceryItem> groceryList) {

        final String sqlStatement = "INSERT INTO GroceryList (GroceryItemId) VALUES (?)";

        BatchPreparedStatementSetter bpss = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, groceryList.get(i).getId());
            }

            @Override
            public int getBatchSize() {
                return groceryList.size();
            }
        };

        int[] createdRows = jdbcTemplate.batchUpdate(sqlStatement, bpss);

        if (BatchExecutionHelper.failedBatchExecution(createdRows))
            throw new RuntimeException(String.format(
                    "Failed adding one or more grocery items to the grocery list: %s",
                    groceryList));

        return groceryList;
    }

    @Override
    public boolean deleteGroceryListItem(long groceryItemId) {
        final String sqlStatement = "DELETE FROM GroceryList WHERE GroceryItemId = ? FETCH FIRST ROW ONLY";

        int numDeletedRows = jdbcTemplate.update(sqlStatement, groceryItemId, Long.class);

        return numDeletedRows == 1;
    }

    @Override
    public boolean deleteAllOfGroceryListItem(long groceryItemId) {
        final String sqlStatement = "DELETE FROM GroceryList WHERE GroceryItemId = ?";

        int numDeletedRows = jdbcTemplate.update(sqlStatement, groceryItemId, Long.class);

        if (numDeletedRows < 1)
            throw new RuntimeException("No rows delete or no rows to delete");

        return true;
    }

    @Override
    public boolean deleteGroceryList() {
        final String sqlStatement = "DELETE FROM GroceryList";

        int deletedRows = jdbcTemplate.update(sqlStatement);

        return deletedRows < 1;
    }
}
