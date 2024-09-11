package com.shoppinglist.dao.jdbc;

import com.google.common.collect.ImmutableList;
import com.shoppinglist.api.dao.RecipeDAO;
import com.shoppinglist.api.model.Recipe;
import com.shoppinglist.model.RecipeImpl;
import com.shoppinglist.util.DataAccessExceptionHandler;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
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

    public RecipeDAOJdbc (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Recipe> getRecipes() {
        final String sqlQuery = "SELECT RecipeId AS id, Name AS name FROM Recipe";

        List<RecipeImpl> recipesList = jdbcTemplate.queryForList(sqlQuery, RecipeImpl.class);

        return ImmutableList.copyOf(recipesList);
    }

    @Override
    public Recipe saveRecipe(Recipe recipe) {
        final String sqlQuery = "INSERT INTO Recipe (Name) VALUES (?)";

        PreparedStatementCreator psc = con -> con.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int numInsertedRows = jdbcTemplate.update(psc, keyHolder);

        if (numInsertedRows != 1 || Objects.isNull(keyHolder.getKey()))
            throw new RuntimeException(String.format("Unable to create recipe: %s", recipe));

        return new RecipeImpl(keyHolder.getKey().longValue(), recipe.getName());
    }

    @Override
    public Recipe updateRecipe(long recipeId, Recipe updatedRecipe) {
        final String sqlQuery = "UPDATE Recipe SET Name = ? WHERE RecipeId = ?";

        int numUpdatedRows = jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sqlQuery);

            ps.setString(1, updatedRecipe.getName());
            ps.setLong(2, recipeId);

            return ps;
        });

        if (numUpdatedRows != 1)
            throw new RuntimeException(String.format("Unable to update recipe: %s", updatedRecipe));

        return updatedRecipe;
    }

    @Override
    public boolean deleteRecipe(long recipeId) {
        final String sqlDeleteRecipes = "DELETE FROM Recipe WHERE RecipeId = ?";

        int deletedRows = jdbcTemplate.update(sqlDeleteRecipes, recipeId, Long.class);

        return deletedRows == 1;
    }
}
