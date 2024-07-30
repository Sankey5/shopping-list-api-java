package com.api.shoppinglist.controlller;

import com.api.shoppinglist.model.GroceryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ShoppingListController {

    //@Autowired
    //private GroceryItem groceryItems = new GroceryItem();

    @GetMapping(value = "/health", produces = "text/json")
    public String health() {
        //return new ResponseEntity<String>("I am healthy", HttpStatus.OK);
        return "{code: 200, response: 'I am healthy'}";
    }
}
