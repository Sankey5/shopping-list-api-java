package com.shoppinglist.service;

import com.shoppinglist.api.dao.GroceryListDAO;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.service.GroceryListService;
import com.shoppinglist.model.GroceryItemImpl;
import com.shoppinglist.util.SQLExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroceryListServiceImpl implements GroceryListService {

    @Autowired
    GroceryListDAO groceryListDAO;

    @Override
    public List<GroceryItem> getGroceryList() throws SQLException{
        return groceryListDAO.getGroceryList();
    }

    @Override
    public List<GroceryItem> updateGroceryList(List<GroceryItem> updatedGroceryList) {
        ArrayList<GroceryItem> oldGroceryList = new ArrayList<>();

        try {
            oldGroceryList= (ArrayList<GroceryItem>) this.getGroceryList();
        } catch (SQLException e) {
            SQLExceptionHandler.handle(e);
            return List.of();
        }

        if(oldGroceryList.isEmpty()) {
            groceryListDAO.saveGroceryList(updatedGroceryList);
            return updatedGroceryList;
        }

        oldGroceryList = (ArrayList<GroceryItem>) mergeGroceryItemListQuantity(oldGroceryList, updatedGroceryList);

        return groceryListDAO.updateGroceryList(oldGroceryList);
    }

    @Override
    public boolean deleteGroceryListItem(long groceryItemId) {
        return false;
    }

    @Override
    public boolean deleteGroceryList() {
        return false;
    }

    private List<GroceryItem> mergeGroceryItemListQuantity(List<GroceryItem> originalGroceryList,
                                                            List<GroceryItem> updatedGroceryList) {
        for(int i = 0; i < updatedGroceryList.size(); i++) {
            GroceryItem currGroceryItem = updatedGroceryList.get(i);

            if(originalGroceryList.contains(currGroceryItem)) {
                for(int j = 0; j < originalGroceryList.size(); j++) {
                    GroceryItem currOldGroceryItem = originalGroceryList.get(j);

                    if( currGroceryItem == originalGroceryList.get(i) ) {
                        GroceryItemImpl newGroceryItem = new GroceryItemImpl(
                                currOldGroceryItem.getId(),
                                currOldGroceryItem.getName(),
                                currOldGroceryItem.getQuantity() + currGroceryItem.getQuantity(),
                                currOldGroceryItem.getMeasure(),
                                0
                        );

                        originalGroceryList.set(j, newGroceryItem);
                    }
                }
            } else {
                originalGroceryList.add(currGroceryItem);
            }
        }

        return originalGroceryList;
    }
}
