package com.shoppinglist.model;

import com.shoppinglist.api.model.GroceryItem;

public class GroceryItemImpl implements GroceryItem {

    private final long id;
    private String name;
    private double quantity;
    private String measure;
    private final long recipeId;

    public GroceryItemImpl(String name, double quantity, String measure) {
        this.id = 0;
        this.name = name.toLowerCase();
        this.quantity = quantity;
        this.measure = measure;
        this.recipeId = 0;
    }

    public GroceryItemImpl(long id, String name, double quantity, String measure, long recipeId) {
        this.id = id;
        this.name = name.toLowerCase();
        this.quantity = quantity;
        this.measure = measure;
        this.recipeId = recipeId;
    }

    @Override public String getName() {
        return name;
    }

    @Override public void setName(String name) {
        this.name = name.toLowerCase();
    }

    @Override public double getQuantity() {
        return quantity;
    }

    @Override public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Override public long getId() {return id;}

    @Override public String getMeasure() {return measure;}

    @Override public void setMeasure(String measure) {this.measure = measure;}

    @Override
    public long getRecipeId() { return recipeId; }

    @Override public boolean equals(GroceryItem g) {
        if (g == null || this.getClass() != g.getClass())
            return false;
        else
            return this.getId() == g.getId();
    }
}
