package com.shoppinglist.controller;

import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.service.GroceryListService;
import com.shoppinglist.util.SQLExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/home/grocery-list")
public class GroceryListPageController {

    @Autowired
    private GroceryListService groceryListService;
    private final Logger LOGGER = LoggerFactory.getLogger(GroceryListPageController.class);

    @GetMapping
    public String getShoppingList(Model model) {
        List<GroceryItem> groceryList;

        try {
            groceryList = groceryListService.getGroceryList();
        } catch (SQLException e) {
            SQLExceptionHandler.handle(e);
            groceryList = new ArrayList<>();
        }

        LOGGER.info(String.format("Array List: %s", groceryList));

        model.addAttribute("groceryList", groceryList);
        return "grocerylist.html :: grocerylist";
    }

    @PutMapping(consumes = "application/json")
    public String saveOrUpdateGroceryList(@RequestBody List<GroceryItem> groceryItemList, Model model) {

        if(groceryItemList == null)
            return "error";

        List<GroceryItem> updatedList = groceryListService.updateGroceryList(groceryItemList);

        if(updatedList.isEmpty())
            return "grocerylist.html :: grocerylist";

        model.addAttribute("groceryList", updatedList);
        return "grocerylist.html :: grocerylist";
    }


    @DeleteMapping
    public String deleteShoppingList() {
        return "";
    }



}