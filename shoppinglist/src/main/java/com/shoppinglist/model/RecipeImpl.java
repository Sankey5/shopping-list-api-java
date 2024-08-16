package com.shoppinglist.model;

import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.model.Recipe;
import com.shoppinglist.dao.GroceryDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

public class RecipeImpl implements Recipe {

    private static int nextId = 0;
    private final int id;
    private String name;
    @Autowired
    private List<GroceryItem> groceryItems;

    public RecipeImpl(String name, List<GroceryItem> groceryItems) {
        this.id = ++nextId;
        this.name = name.toLowerCase();
        this.groceryItems = groceryItems;
    }

    @Override
    public int getId() {return id;}

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
        else if (this.getId() == g.getId()) {
            return true;
        }

        // Compare all
        return Objects.equals(this.name, g.getName());
    }
}
