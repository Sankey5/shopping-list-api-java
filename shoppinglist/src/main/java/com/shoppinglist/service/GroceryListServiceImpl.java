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
import java.util.*;

@Service
public class GroceryListServiceImpl implements GroceryListService {

    private final Logger LOGGER = LoggerFactory.getLogger(GroceryListServiceImpl.class);

    @Autowired
    GroceryListDAO groceryListDAO;

    @Override
    public List<GroceryItem> getGroceryList() throws SQLException{

        List<GroceryItem> returnedList = groceryListDAO.getGroceryList();
        return reduceSimilarItems(returnedList);
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

    private List<GroceryItem> reduceSimilarItems(List<GroceryItem> unnormilizedList) {
        HashMap<String, GroceryItem> itemsHashMap = new HashMap<>();

        for(GroceryItem currItem : unnormilizedList) {
            String currName = currItem.getName();

            if(!itemsHashMap.containsKey(currName)) {
                itemsHashMap.put(currName, currItem);
            } else {
                itemsHashMap.computeIfPresent(currName, (key, val) -> {
                    val.setQuantityAndMeasure(val, currItem);
                    return val;
                });

            }
        }

        return List.copyOf(itemsHashMap.values());
    }
}
