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

        groceryList = groceryListService.getGroceryList();

        return groceryList;
    }

    @PostMapping
    public List<GroceryItem> addGroceryItemsToGroceryList(List<GroceryItem> newGroceryItems) {
        List<GroceryItem> updatedList = groceryListService.addToGroceryList(newGroceryItems);

        if(updatedList.isEmpty())
            return List.of();

        return updatedList;
    }
}
