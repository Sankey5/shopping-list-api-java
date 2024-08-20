package com.shoppinglist.model;

import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeImpl implements Recipe {

    private final long id;
    private String name;
    private List<GroceryItem> groceryItems;

    public RecipeImpl(long id, String name, List<GroceryItem> groceryItems) {
        this.id = id;
        this.name = name.toLowerCase();
        this.groceryItems = groceryItems;
    }

    public RecipeImpl(long id, String name) {
        this.id = id;
        this.name = name.toLowerCase();
        this.groceryItems = new ArrayList<>();
    }

    @Override
    public long getId() {return id;}

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name.toLowerCase();
    }

    @Override
    public List<GroceryItem> getGroceryItems() {return groceryItems;}

    @Override
    public void setGroceryItems(List<GroceryItem> groceryItems) {
        this.groceryItems = groceryItems;
    }

    @Override
    public boolean equals(Recipe g) {
        if (g == null || this.getClass() != g.getClass())
            return false;
        else
            return this.getId() == g.getId();
    }
}
