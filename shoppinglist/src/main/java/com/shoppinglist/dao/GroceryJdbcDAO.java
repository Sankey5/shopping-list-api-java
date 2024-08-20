package com.shoppinglist.dao;

import com.google.common.collect.ImmutableList;
import com.shoppinglist.api.dao.GroceryDAO;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.model.GroceryItemImpl;
import com.shoppinglist.util.Database;
import com.shoppinglist.util.SQLExceptionHandler;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GroceryJdbcDAO implements GroceryDAO {

    public GroceryJdbcDAO() {
        super();
    }

    @Override
    public List<GroceryItem> getGroceryItems(long RecipeId) {
        final String sqlStatement = "SELECT * FROM GroceryItems WHERE RecipeId = ?";
        ArrayList<GroceryItemImpl> groceryItemsList = new ArrayList<>();

        try(Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement(sqlStatement);) {

            ps.setLong(1, RecipeId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                GroceryItemImpl currGI = new GroceryItemImpl(
                        rs.getLong("GroceryItemId"),
                        rs.getString("Name"),
                        rs.getDouble("Quantity"),
                        rs.getString("Measure"),
                        rs.getLong("RecipeId")
                );

                groceryItemsList.add(currGI);
            }

        } catch (SQLException sqlException) {
            SQLExceptionHandler.handle(sqlException);
        }

        return ImmutableList.copyOf(groceryItemsList);}

    @Override
    public boolean saveGroceryItem(GroceryItem newGroceryItem) {
        return false; //groceryItemsList.add((GroceryItemImpl) newGroceryItem);
    }

    @Override
    public boolean updateGroceryItem(GroceryItem updatedGroceryItem) {

//        // TODO: Change to a stream
//        // If it can be found, update the item
//        for(int i = 0; i < groceryItemsList.size(); i++) {
//            if(groceryItemsList.get(i).equals(updatedGroceryItem)) {
//                groceryItemsList.set(i, (GroceryItemImpl) updatedGroceryItem);
//                return true;
//            }
//        }

        return false;
    }

    @Override
    public boolean deleteGroceryItem(String groceryItemName) {
//        for(int r = 0; r < groceryItemsList.size(); r++) {
//            if(groceryItemName.equals(groceryItemsList.get(r).getName())) {
//                groceryItemsList.remove(r);
//                return true;
//            }
//        }
        return false;
    }
}
