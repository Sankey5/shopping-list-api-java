package com.shoppinglist.dao.jdbc;

//@Repository
public class RecipeDAOJdbc {
//
//    public Recipe saveRecipe(String recipeName) {
//        final String sqlQuery = "INSERT INTO Recipe (name) VALUES (?)";
//
//        PreparedStatementCreator psc = new PreparedStatementCreator() {
//            @Override
//            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//                PreparedStatement ps = con.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
//                ps.setString(1, recipeName);
//                return ps;
//            }
//        };
//
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//
//        int numInsertedRows = jdbcTemplate.update(psc, keyHolder);
//
//        if (numInsertedRows != 1 || keyHolder.getKey() == null)
//            throw new RuntimeException(String.format("Unable to create recipe with name: %s", recipeName));
//
//        return new RecipeImpl(keyHolder.getKey().longValue(), recipeName);
//    }
//
//    public String updateRecipeName(long recipeId, String updatedRecipeName) {
//        final String sqlQuery = "UPDATE Recipe SET name = ? WHERE recipeId = ?";
//
//        int numUpdatedRows = jdbcTemplate.update(con -> {
//            PreparedStatement ps = con.prepareStatement(sqlQuery);
//
//            ps.setString(1, updatedRecipeName);
//            ps.setLong(2, recipeId);
//
//            return ps;
//        });
//
//        if (numUpdatedRows != 1)
//            throw new RuntimeException(String.format("Unable to update recipe with name: %s", updatedRecipeName));
//
//        return updatedRecipeName;
//    }
//
//    public boolean deleteRecipe(long recipeId) {
//        final String sqlDeleteRecipes = "DELETE FROM Recipe WHERE recipeId = ?";
//
//        int deletedRows = jdbcTemplate.update(sqlDeleteRecipes, ps -> ps.setLong(1, recipeId));
//
//        if (deletedRows != 1)
//            throw new RuntimeException(String.format("Unable to delete recipe with id: %d", recipeId));
//
//        return true;
//    }
}
