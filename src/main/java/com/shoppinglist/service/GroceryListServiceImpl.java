package com.shoppinglist.service;

import com.google.common.collect.ImmutableList;
import com.shoppinglist.api.dao.GroceryListDAO;
import com.shoppinglist.api.service.GroceryListService;
import com.shoppinglist.model.GroceryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GroceryListServiceImpl implements GroceryListService {

    @Autowired GroceryListDAO groceryListDAO;

    @Override
    public List<GroceryItem> getGroceryList(){
        List<GroceryItem> groceryListItems;

        groceryListItems = ImmutableList.copyOf(groceryListDAO.findAll());

        if(Objects.isNull(groceryListItems))
            return List.of();

        return reduceSimilarItems(groceryListItems);
    }

    @Override
    public List<GroceryItem> addToGroceryList(List<GroceryItem> newGroceryList) {
        return ImmutableList.copyOf(groceryListDAO.saveAll(newGroceryList));
    }

    @Override
    public void deleteGroceryListItem(long groceryItemId) {
        groceryListDAO.deleteFirstByGroceryItemId(groceryItemId);
    }

    @Override
    public void deleteAllOfGroceryListItem(long groceryItemId) {
        groceryListDAO.deleteById(groceryItemId);
    }

    @Override
    public void deleteAllGroceryListItems(List<GroceryItem> groceryListItems) {
        groceryListDAO.deleteAll(groceryListItems);
    }

    @Override
    public void deleteGroceryList() {
        groceryListDAO.deleteAll();
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
