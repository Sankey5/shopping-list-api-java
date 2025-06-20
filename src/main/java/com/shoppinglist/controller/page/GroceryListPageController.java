package com.shoppinglist.controller.page;

import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.service.GroceryListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/home/grocery-list")
public class GroceryListPageController {

    @Autowired
    private GroceryListService groceryListService;
    private final Logger LOGGER = LoggerFactory.getLogger(GroceryListPageController.class);

    @GetMapping
    public String getShoppingList(Model model) {
        List<GroceryItem> groceryList;

        groceryList = groceryListService.getGroceryList();

        model.addAttribute("groceryList", groceryList);
        return "grocerylist.html :: grocerylist";
    }

    @GetMapping("/rows")
    public ModelAndView getShoppingListRows() {
        ModelAndView modelAndView = new ModelAndView("grocerylist.html :: grocerylistRows");

        List<GroceryItem> groceryList = groceryListService.getGroceryList();

        modelAndView.addObject("groceryList", groceryList);
        return modelAndView;
    }

    @PostMapping(consumes = "application/json")
    // TODO: Chance to response entity to see if this still returns the template
    public String addGroceryItemsToGroceryList(@RequestBody List<GroceryItem> groceryItemList, Model model) {

        if(groceryItemList == null)
            return "error";

        if(groceryItemList.isEmpty())
            return "grocerylist.html :: grocerylist";

        List<GroceryItem> updatedList = groceryListService.addToGroceryList(groceryItemList);

        if(updatedList.isEmpty())
            return "grocerylist.html :: grocerylist";

        List<GroceryItem> allGroceryItems = groceryListService.getGroceryList();

        model.addAttribute("groceryList", allGroceryItems);
        return "grocerylist.html :: grocerylist";
    }

    @DeleteMapping(value = "/{groceryItemId}")
    public String deleteAShoppingListItem(@PathVariable long groceryItemId, Model model) {

        if(!groceryListService.deleteGroceryListItem(groceryItemId))
            return "error";

        List<GroceryItem> newGroceryItems = groceryListService.getGroceryList();
        model.addAttribute("groceryList", newGroceryItems);

        return "grocerylist.html :: grocerylist";
    }

    @DeleteMapping(value = "/{groceryItemId}/all")
    public String deleteAllOfGroceryListItem(@PathVariable long groceryItemId, Model model) {

        if(!groceryListService.deleteAllOfGroceryListItem(groceryItemId))
            return "error";

        List<GroceryItem> newGroceryItems = groceryListService.getGroceryList();
        model.addAttribute("groceryList", newGroceryItems);

        return "grocerylist.html :: grocerylist";
    }

    @DeleteMapping
    public String deleteShoppingList() {
        if(!groceryListService.deleteGroceryList()) {
            return "error";
        }

        return "grocerylist.html :: grocerylist";
    }



}