package com.shoppinglist.service;

import com.google.common.collect.ImmutableList;
import com.shoppinglist.api.dao.GroceryListDAO;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.service.GroceryListService;
import com.shoppinglist.model.GroceryItemImpl;
import com.shoppinglist.util.SQLExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroceryListServiceImpl implements GroceryListService {

    private final Logger LOGGER = LoggerFactory.getLogger(GroceryListServiceImpl.class);

    @Autowired
    GroceryListDAO groceryListDAO;

    @Override
    public List<GroceryItem> getGroceryList() throws SQLException{
        return groceryListDAO.getGroceryList();
    }

    @Override
    public List<GroceryItem> updateGroceryList(List<GroceryItem> updatedGroceryList) {
        ArrayList<GroceryItem> oldGroceryList;

        LOGGER.info(String.format("Updated grocery list given to be updated: %s", updatedGroceryList));

        try {
            oldGroceryList = new ArrayList<>(this.getGroceryList());
        } catch (SQLException e) {
            SQLExceptionHandler.handle(e);
            return new ArrayList<>();
        }

        if(oldGroceryList.isEmpty()) {
            groceryListDAO.saveGroceryList(updatedGroceryList);
            return updatedGroceryList;
        }

        LOGGER.info(String.format("Old grocery list before mutation: %s", oldGroceryList));

        mergeGroceryItemListQuantity(oldGroceryList, updatedGroceryList);

        LOGGER.info(String.format("Updated old grocery list: %s", oldGroceryList));

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

    private void mergeGroceryItemListQuantity(ArrayList<GroceryItem> originalGroceryList,
                                                            List<GroceryItem> updatedGroceryList) {
        for(int i = 0; i < updatedGroceryList.size(); i++) {
            GroceryItem currGroceryItem = updatedGroceryList.get(i);

            LOGGER.info(String.format("Current grocery item during merge list: %s", currGroceryItem));

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
    }
}
