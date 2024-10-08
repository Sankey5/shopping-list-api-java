package com.shoppinglist.service;

import com.shoppinglist.api.dao.GroceryListDAO;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.service.GroceryListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GroceryListServiceImpl implements GroceryListService {

    @Autowired GroceryListDAO groceryListDAO;

    @Override
    public List<GroceryItem> getGroceryList(){
        List<GroceryItem> groceryListItems;

        groceryListItems = groceryListDAO.getGroceryList();

        if(Objects.isNull(groceryListItems))
            return List.of();

        return reduceSimilarItems(groceryListItems);
    }

    @Override
    public List<GroceryItem> addToGroceryList(List<GroceryItem> newGroceryList) {
        return groceryListDAO.addToGroceryList(newGroceryList);
    }

    @Override
    public boolean deleteGroceryListItem(long groceryItemId) {
        return groceryListDAO.deleteGroceryListItem(groceryItemId);
    }

    @Override
    public boolean deleteAllOfGroceryListItem(long groceryItemId) {
        return groceryListDAO.deleteAllOfGroceryListItem(groceryItemId);
    }

    @Override
    public boolean deleteAllGroceryListItems(List<GroceryItem> groceryListItems) {
        return groceryListDAO.deleteAllGroceryListItems(groceryListItems);
    }

    @Override
    public boolean deleteGroceryList() {
        return groceryListDAO.deleteGroceryList();
    }

    private List<GroceryItem> reduceSimilarItems(List<GroceryItem> unnormilizedList) {
        HashMap<String, GroceryItem> itemsHashMap = new HashMap<>();

        for(GroceryItem currItem : unnormilizedList) {
            String currName = currItem.getName();

            if(!itemsHashMap.containsKey(currName)) {
                itemsHashMap.put(currName, currItem);
            } else {
                itemsHashMap.computeIfPresent(currName, (key, val) -> {
                    val.addQuantityAndMeasure(val, currItem);
                    return val;
                });

            }
        }

        return List.copyOf(itemsHashMap.values());
    }
}
