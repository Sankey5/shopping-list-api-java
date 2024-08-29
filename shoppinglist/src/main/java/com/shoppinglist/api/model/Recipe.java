package com.shoppinglist.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.shoppinglist.model.RecipeImpl;

import java.util.List;

@JsonDeserialize(as = RecipeImpl.class)
public interface Recipe {

    long getId();

    String getName();

    void setName(String name);

    List<GroceryItem> getGroceryItems();

    void setGroceryItems(List<GroceryItem> groceryItems);
}
