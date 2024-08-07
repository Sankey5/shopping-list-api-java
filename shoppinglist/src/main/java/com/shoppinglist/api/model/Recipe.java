package com.shoppinglist.api.model;

import com.shoppinglist.model.GroceryItemList;

public interface Recipe {

    int getId();

    String getName();

    void setName(String name);

    GroceryItemList getGroceryItems();

    void setGroceryItems(GroceryItemList groceryItems);

    boolean equals(Recipe g);
}
