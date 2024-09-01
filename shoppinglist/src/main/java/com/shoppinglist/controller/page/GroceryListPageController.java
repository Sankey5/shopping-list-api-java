package com.shoppinglist.controller.page;

import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.model.GroceryListItem;
import com.shoppinglist.api.service.GroceryListService;
import com.shoppinglist.util.SQLExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/home/grocery-list")
public class GroceryListPageController {

    @Autowired
    private GroceryListService groceryListService;
    private final Logger LOGGER = LoggerFactory.getLogger(GroceryListPageController.class);

    @GetMapping
    public String getShoppingList(Model model) {
        List<GroceryListItem> groceryList;
        groceryList = groceryListService.getGroceryList();

        model.addAttribute("groceryList", groceryList);
        return "grocerylist.html :: grocerylist";
    }

    @PostMapping(consumes = "application/json")
    // TODO: Chance to response entity to see if this still returns the template
    public String addGroceryItemsToGroceryList(@RequestBody List<GroceryItem> groceryItemList, Model model) {

        if(groceryItemList == null)
            return "error";

        if(groceryItemList.isEmpty())
            return "grocerylist.html :: grocerylist";

        List<GroceryListItem> updatedList = groceryListService.addToGroceryList(groceryItemList);

        if(updatedList.isEmpty())
            return "grocerylist.html :: grocerylist";

        model.addAttribute("groceryList", updatedList);
        return "grocerylist.html :: grocerylist";
    }

    @DeleteMapping(value = "/{groceryListItemId}")
    public String deleteGroceryListItem(@PathVariable long groceryListItemId, @PathVariable long groceryItemId, Model model) {

        if (groceryItemId >= 0)
            return "error";

        if(!groceryListService.deleteGroceryListItem(groceryListItemId))
            return "error";

        List<GroceryListItem> groceryItems = groceryListService.getGroceryList();

        model.addAttribute("groceryList", groceryItems);
        return "grocerylist.html :: grocerylist";
    }

    @DeleteMapping(value = "/{groceryItemId}")
    public String deleteAllOfGroceryListItem(@PathVariable long groceryItemId, Model model) {

        if (groceryItemId >= 0)
            return "error";

        if(!groceryListService.deleteAllOfGroceryListItem(groceryItemId))
            return "error";

        List<GroceryListItem> groceryItems = groceryListService.getGroceryList();

        model.addAttribute("groceryList", groceryItems);
        return "grocerylist.html :: grocerylist";
    }

    @DeleteMapping
    public String deleteShoppingList(Model model) {

        if(!groceryListService.deleteGroceryList())
            return "error";

        List<GroceryListItem> groceryItems = groceryListService.getGroceryList();

        model.addAttribute("groceryList", groceryItems);
        return "grocerylist.html :: grocerylist";
    }

}