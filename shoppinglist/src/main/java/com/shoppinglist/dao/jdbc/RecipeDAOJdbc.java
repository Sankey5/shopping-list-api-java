package com.shoppinglist.dao.jdbc;

import com.google.common.collect.ImmutableList;
import com.shoppinglist.api.dao.RecipeDAO;
import com.shoppinglist.api.model.Recipe;
import com.shoppinglist.model.RecipeImpl;
import com.shoppinglist.util.DataAccessExceptionHandler;
import jakarta.persistence.EntityManager;
import org.checkerframework.checker.units.qual.A;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class RecipeDAOJdbc implements RecipeDAO {

    private JdbcTemplate jdbcTemplate;
    private EntityManager entityManager;

    public RecipeDAOJdbc (JdbcTemplate jdbcTemplate, EntityManager entityManager) {
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
    public List<Recipe> getRecipes() {
        final String sqlQuery = "SELECT RecipeId AS id, Name AS name FROM Recipe";

        List<RecipeImpl> recipesList = jdbcTemplate.query(sqlQuery, (rs, rowNum) ->
                new RecipeImpl(rs.getLong(1), rs.getString(2))
        );

        return ImmutableList.copyOf(recipesList);
    }

    @Override
    public Recipe saveRecipe(String recipeName) {
        final String sqlQuery = "INSERT INTO Recipe (Name) VALUES (?)";

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, recipeName);
                return ps;
            }
        };

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int numInsertedRows = jdbcTemplate.update(psc, keyHolder);

        if (numInsertedRows != 1 || Objects.isNull(keyHolder.getKey()))
            throw new RuntimeException(String.format("Unable to create recipe with name: %s", recipeName));

        return new RecipeImpl(keyHolder.getKey().longValue(), recipeName);
    }

    @Override
    public String updateRecipeName(long recipeId, String updatedRecipeName) {
        final String sqlQuery = "UPDATE Recipe SET Name = ? WHERE RecipeId = ?";

        int numUpdatedRows = jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sqlQuery);

            ps.setString(1, updatedRecipeName);
            ps.setLong(2, recipeId);

            return ps;
        });

        if (numUpdatedRows != 1)
            throw new RuntimeException(String.format("Unable to update recipe with name: %s", updatedRecipeName));

        return updatedRecipeName;
    }

    @Override
    public boolean deleteRecipe(long recipeId) {
        final String sqlDeleteRecipes = "DELETE FROM Recipe WHERE RecipeId = ?";

        int deletedRows = jdbcTemplate.update(sqlDeleteRecipes, ps -> ps.setLong(1, recipeId));

        if (deletedRows != 1)
            throw new RuntimeException(String.format("Unable to delete recipe with id: %d", recipeId));

        return true;
    }
}
