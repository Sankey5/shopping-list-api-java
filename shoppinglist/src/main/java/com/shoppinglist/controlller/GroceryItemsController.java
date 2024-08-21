package com.shoppinglist.controlller;

import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.service.GroceryService;
import com.shoppinglist.api.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/recipes/{id}/grocery-items")
@RestController
public class GroceryItemsController {

    @Autowired
    private GroceryService groceryService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<GroceryItem>> getGroceryItems(@PathVariable long recipeId) {
        return new ResponseEntity<>(groceryService.getGroceryItems(recipeId), HttpStatus.OK) ;
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<String> deleteAGroceryItem(@PathVariable long groceryItemId) {
        if (!groceryService.deleteGroceryItem(groceryItemId)) {
            return new ResponseEntity<String>("Failed to delete grocery item", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("Deleted grocery item", HttpStatus.OK);
    }
}
