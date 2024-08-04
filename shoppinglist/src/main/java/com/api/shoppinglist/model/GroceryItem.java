package com.api.shoppinglist.model;

import java.util.Objects;

public class GroceryItem {

    private static int nextId = 0;
    private final int id;
    private String name;
    private double quantity;
    private String measure;

    public GroceryItem(String name, double quantity, String measure) {
        this.id = ++nextId;
        this.name = name.toLowerCase();
        this.quantity = quantity;
        this.measure = measure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toLowerCase();
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public int getId() {return id;}

    public String getMeasure() {return measure;}

    public void setMeasure(String measure) {this.measure = measure;}

    public boolean equals(GroceryItem g) {
        if (g == null || this.getClass() != g.getClass())
            return false;
        else if (this.getId() == g.getId()) {
            return true;
        }

        // Compare all
        return Objects.equals(this.name, g.getName()) &&
                Objects.equals(this.quantity, g.getQuantity()) &&
                Objects.equals(this.measure, g.getMeasure());
    }
}
