package com.shoppinglist.controlller;

import com.shoppinglist.model.GroceryItem;
import com.shoppinglist.model.GroceryItemList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShoppingListController {

//    @Autowired
//    private GroceryItemList groceryItems;
//
//    @GetMapping(value = "/grocery-list")
//    public GroceryItemList getGroceryItems() {
//        return groceryItems;
//    }
//
//    @PostMapping(value = "/grocery-list")
//    public ResponseEntity<String> addOrUpdateGroceryItems(@RequestBody GroceryItem newGroceryItem) {
//        if (!groceryItems.addOrUpdateItem(newGroceryItem)) {
//            return new ResponseEntity<String>("Failed to add grocery", HttpStatus.BAD_REQUEST);
//        }
//
//        return new ResponseEntity<String>("Added new grocery", HttpStatus.OK);
//    }
}
