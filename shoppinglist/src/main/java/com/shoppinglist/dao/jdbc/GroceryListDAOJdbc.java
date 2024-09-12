package com.shoppinglist.dao.jdbc;

import com.google.common.collect.ImmutableList;
import com.shoppinglist.api.dao.GroceryListDAO;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.model.GroceryItemImpl;
import com.shoppinglist.util.BatchExecutionHelper;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class GroceryListDAOJdbc implements GroceryListDAO {

    private JdbcTemplate jdbcTemplate;
    private EntityManager entityManager;

    public GroceryListDAOJdbc(JdbcTemplate jdbcTemplate, EntityManager entityManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.entityManager = entityManager;
    }

    private Session getConnection() {
        Session session = null;
        if (entityManager == null
                || (session = entityManager.unwrap(Session.class)) == null) {
            throw new NullPointerException("Unable to get a connection from the entity manager");
        }
        return session;
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

        List<GroceryItemImpl> shoppingList = jdbcTemplate.query(sqlStatement,
                (rs, rowNum) -> new GroceryItemImpl(
                rs.getLong(1),
                rs.getString(2),
                rs.getBigDecimal(3),
                rs.getString(4)
        ));

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

        int numDeletedRows = jdbcTemplate.update(sqlStatement, groceryItemId);

        if (numDeletedRows == Statement.EXECUTE_FAILED || numDeletedRows == Statement.SUCCESS_NO_INFO)
            throw new RuntimeException("Error in deleting rows");

        return true;
    }

    @Override
    public boolean deleteAllOfGroceryListItem(long groceryItemId) {
        final String sqlStatement = "DELETE FROM GroceryList WHERE GroceryItemId = ?";

        int numDeletedRows = jdbcTemplate.update(sqlStatement, groceryItemId);

        if (numDeletedRows == Statement.EXECUTE_FAILED || numDeletedRows == Statement.SUCCESS_NO_INFO)
            throw new RuntimeException("Error in deleting rows");

        return true;
    }
    
    @Override public boolean deleteAllGroceryListItems(List<Long> groceryListItemIds) {

        final String sqlStatement = "DELETE FROM GroceryList WHERE GroceryItemId = ?";

        BatchPreparedStatementSetter bpss = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, groceryListItemIds.get(i));
            }

            @Override
            public int getBatchSize() {
                return groceryListItemIds.size();
            }
        };

        int[] deletedRows = jdbcTemplate.batchUpdate(sqlStatement, bpss);
        
        return BatchExecutionHelper.successfulBatchExecution(deletedRows);
    }

    @Override
    public boolean deleteGroceryList() {
        final String sqlStatement = "DELETE FROM GroceryList";

        int numDeletedRows = jdbcTemplate.update(sqlStatement);

        if (numDeletedRows == Statement.EXECUTE_FAILED || numDeletedRows == Statement.SUCCESS_NO_INFO)
            throw new RuntimeException("Error in deleting rows");

        return true;
    }
}
