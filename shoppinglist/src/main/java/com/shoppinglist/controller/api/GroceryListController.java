package com.shoppinglist.controller.api;

import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.service.GroceryListService;
import com.shoppinglist.util.SQLExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/grocery-list")
public class GroceryListController {

    @Autowired
    GroceryListService groceryListService;

    @GetMapping
    public List<GroceryItem> getGroceryItems() {
        List<GroceryItem> groceryList;

        try {
            groceryList = groceryListService.getGroceryList();
        } catch (SQLException e) {
            SQLExceptionHandler.handle(e);
            groceryList = List.of();
        }

        return groceryList;
    }

    @PutMapping("/")
    public List<GroceryItem> saveOrUpdateGroceryList(List<GroceryItem> groceryItemList) {
        List<GroceryItem> updatedList = groceryListService.updateGroceryList(groceryItemList);

        if(updatedList.isEmpty())
            return List.of();

        return updatedList;
    }
}
