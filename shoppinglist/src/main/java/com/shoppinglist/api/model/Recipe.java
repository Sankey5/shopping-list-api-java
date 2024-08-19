package com.shoppinglist.api.model;

import java.util.List;

public interface Recipe {

    long getId();

    String getName();

    void setName(String name);

    List<GroceryItem> getGroceryItems();

    void setGroceryItems(List<GroceryItem> groceryItems);

    boolean equals(Recipe g);
}
