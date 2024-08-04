package com.api.shoppinglist.controlller;

import com.api.shoppinglist.model.GroceryItem;
import com.api.shoppinglist.model.GroceryItemList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ShoppingListController {

    @Autowired
    private GroceryItemList groceryItems;

    @GetMapping(value = "/health", produces = "text/json")
    public ResponseEntity<String> health() {
        return new ResponseEntity<String>("I am healthy", HttpStatus.OK);
    }


    // TODO: Move this to it's own REST controller
    @GetMapping(value = "/groceryList")
    public GroceryItemList getGroceryItems() {
        return groceryItems;
    }

    @PostMapping(value = "/groceryList")
    public ResponseEntity<String> addOrUpdateGroceryItems(@RequestBody GroceryItem newGroceryItem) {
        if (!groceryItems.addOrUpdateItem(newGroceryItem)) {
            return new ResponseEntity<String>("Failed to add grocery", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("Added new grocery", HttpStatus.OK);
    }

}
