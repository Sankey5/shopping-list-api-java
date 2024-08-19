package com.shoppinglist.model;

import com.shoppinglist.api.model.GroceryItem;

import java.util.Objects;

public class GroceryItemImpl implements GroceryItem {

    private static int nextId = 0;
    private final long id;
    private String name;
    private double quantity;
    private String measure;

    public GroceryItemImpl(String name, double quantity, String measure) {
        this.id = ++nextId;
        this.name = name.toLowerCase();
        this.quantity = quantity;
        this.measure = measure;
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

    @Override public boolean equals(GroceryItem g) {
        if (g == null || this.getClass() != g.getClass())
            return false;
        else
            return this.getId() == g.getId();
    }
}
