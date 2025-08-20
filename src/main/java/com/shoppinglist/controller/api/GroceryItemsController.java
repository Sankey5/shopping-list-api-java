package com.shoppinglist.controller.api;

import com.shoppinglist.api.service.GroceryItemService;
import com.shoppinglist.model.GroceryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class GroceryItemsController {

    @Autowired
    private GroceryItemService groceryItemService;

    @GetMapping(value = "/recipes/{recipeId}/grocery-items", produces = "application/json")
    public ResponseEntity<List<GroceryItem>> getGroceryItems(@PathVariable long recipeId) {
        return new ResponseEntity<>(groceryItemService.getGroceryItemsForRecipe(recipeId), HttpStatus.OK) ;
    }

    @DeleteMapping(value = "/grocery-items/{groceryItemId}", produces = "application/json")
    public ResponseEntity<String> deleteAGroceryItem(@PathVariable long groceryItemId) {
        groceryItemService.deleteGroceryItem(groceryItemId);
        return new ResponseEntity<>("Deleted grocery item", HttpStatus.OK);
    }
}
