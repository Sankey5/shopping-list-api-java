package com.shoppinglist.dao.jdbc;

import com.google.common.collect.ImmutableList;
import com.shoppinglist.api.dao.GroceryItemDAO;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.model.GroceryItemImpl;
import com.shoppinglist.util.BatchExecutionHelper;
import com.shoppinglist.util.DataAccessExceptionHandler;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GroceryItemDAOJdbc implements GroceryItemDAO {

    private JdbcTemplate jdbcTemplate;
    private EntityManager entityManager;

    public GroceryItemDAOJdbc (JdbcTemplate jdbcTemplate, EntityManager entityManager) {
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
    public List<GroceryItem> getGroceryItemsForRecipe(long recipeId) {
        final String sqlStatement = """
                                       SELECT GroceryItemId, Name, Quantity, Measure
                                       FROM GroceryItem
                                       WHERE RecipeId = ?
                                    """;

        List<GroceryItemImpl> groceryItemsList = jdbcTemplate.query(
                con -> {
                    PreparedStatement ps = con.prepareStatement(sqlStatement);
                    ps.setLong(1, recipeId);
                    return ps;
                },
                (rs, rowNum) -> new GroceryItemImpl(
                        rs.getLong("GroceryItemId"),
                        rs.getString("Name"),
                        BigDecimal.valueOf(rs.getDouble("Quantity")),
                        rs.getString("Measure"))
        );

        return ImmutableList.copyOf(groceryItemsList);
    }

    @Override
    public List<GroceryItem> saveGroceryItemsForRecipe(long recipeId, List<GroceryItem> newGroceryItems) {
        final String sqlInsertGroceries = "INSERT INTO GroceryItem (Name, Quantity, Measure, RecipeId) Values (?, ?, ?, " + recipeId + ")";

        int[] retVals = jdbcTemplate.batchUpdate(sqlInsertGroceries, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, newGroceryItems.get(i).getName());
                ps.setDouble(2, newGroceryItems.get(i).getQuantity().doubleValue());
                ps.setString(3, newGroceryItems.get(i).getMeasure());
            }

            @Override
            public int getBatchSize() {
                return newGroceryItems.size();
            }
        });

        if(BatchExecutionHelper.failedBatchExecution(retVals))
            throw new RuntimeException(String.format(
                    "Failed to create one or more grocery items for the recipe id [%d]: %s",
                    recipeId, newGroceryItems)
            );

        return newGroceryItems;
    }

    @Override
    public List<GroceryItem> updateGroceryItemsForRecipe(long recipeId, List<GroceryItem> updatedGroceryItems) {
        final String sqlInsertGroceries = """
                                             UPDATE GroceryItem
                                             SET Name = ?, Quantity = ?, Measure = ?
                                             WHERE GroceryItemId = ?
                                          """;

        int[] numUpdatedGroceryItems = jdbcTemplate.batchUpdate(sqlInsertGroceries, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, updatedGroceryItems.get(i).getName());
                ps.setDouble(2, updatedGroceryItems.get(i).getQuantity().doubleValue());
                ps.setString(3, updatedGroceryItems.get(i).getMeasure());
                ps.setLong(4, updatedGroceryItems.get(i).getId());
            }

            @Override
            public int getBatchSize() {
                return updatedGroceryItems.size();
            }
        });

        if(BatchExecutionHelper.failedBatchExecution(numUpdatedGroceryItems))
            throw new RuntimeException(String.format(
                    "Failed to update one or more grocery items for the recipe id [%d]: %s",
                    recipeId, updatedGroceryItems)
            );

        return updatedGroceryItems;
    }

    @Override
    public boolean deleteAllGroceryItemsForRecipe (long recipeId) {
        final String sqlDeleteGroceries = "DELETE FROM GroceryItem WHERE RecipeId = ?";

        int deletedRows = jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sqlDeleteGroceries);
            ps.setLong(1, recipeId);
            return ps;
        });

        if (deletedRows < 1)
            throw new RuntimeException(String.format("Failed deleting grocery items for recipe [%d]", recipeId));

        return true;
    }

    @Override
    public boolean deleteGroceryItem(long groceryItemId) {
        final String sqlDeleteGroceries = "DELETE FROM GroceryItem WHERE GroceryItemId = ?";

        int deletedRows = jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sqlDeleteGroceries);
            ps.setLong(1, groceryItemId);
            return ps;
        });

        if (deletedRows < 1)
            throw new RuntimeException(String.format("Failed deleting grocery item with id [%d]", groceryItemId));

        return true;
    }
}
