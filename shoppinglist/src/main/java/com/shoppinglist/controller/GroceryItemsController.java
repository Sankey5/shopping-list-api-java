package com.shoppinglist.controller;

import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.service.GroceryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class GroceryItemsController {

    @Autowired
    private GroceryService groceryService;

    @GetMapping(value = "/recipes/{recipeId}/grocery-items", produces = "application/json")
    public ResponseEntity<List<GroceryItem>> getGroceryItems(@PathVariable long recipeId) {
        return new ResponseEntity<>(groceryService.getGroceryItems(recipeId), HttpStatus.OK) ;
    }

    @DeleteMapping(value = "/grocery-items/{groceryItemId}", produces = "application/json")
    public ResponseEntity<String> deleteAGroceryItem(@PathVariable long groceryItemId) {
        if (!groceryService.deleteGroceryItem(groceryItemId)) {
            return new ResponseEntity<>("Failed to delete grocery item", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Deleted grocery item", HttpStatus.OK);
    }
}
