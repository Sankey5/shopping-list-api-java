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

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    public List<GroceryItem> addToGroceryList(List<GroceryItem> newGroceryList) {
        return groceryListDAO.addToGroceryList(newGroceryList);
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
                                                            List<GroceryItem> providedGroceryList) {

        for (GroceryItem groceryItemToUpdate : providedGroceryList) {

            if (originalGroceryList.contains(groceryItemToUpdate)) {

                int indexOfItemInOldList = originalGroceryList.indexOf(groceryItemToUpdate);
                BigDecimal currentOriginalQuantity = originalGroceryList.get(indexOfItemInOldList).getQuantity();

                BigDecimal updatedQuantity = currentOriginalQuantity.add(groceryItemToUpdate.getQuantity())
                                                .setScale(3, RoundingMode.HALF_UP);

                groceryItemToUpdate.setQuantity(updatedQuantity);
                originalGroceryList.set(indexOfItemInOldList, groceryItemToUpdate);

            } else {
                originalGroceryList.add(groceryItemToUpdate);
            }
        }
    }
}
