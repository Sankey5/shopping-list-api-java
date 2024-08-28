package com.shoppinglist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.shoppinglist.api.model.GroceryItem;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "name", "quantity", "measure"})
public class GroceryItemImpl implements GroceryItem {

    @JsonProperty("id")
    private final long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("quantity")
    private double quantity;
    @JsonProperty("measure")
    private String measure;
    @JsonProperty("recipeId")
    private final long recipeId;

    public GroceryItemImpl(@JsonProperty("id") long id,
                           @JsonProperty("name") String name,
                           @JsonProperty("quantity") double quantity,
                           @JsonProperty("measure") String measure) {
        this.id = id;
        this.name = name.toLowerCase();
        this.quantity = quantity;
        this.measure = measure;
        this.recipeId = 0;
    }

    public GroceryItemImpl(@JsonProperty("name") String name,
                           @JsonProperty("quantity") double quantity,
                           @JsonProperty("measure") String measure) {
        this.id = 0;
        this.name = name.toLowerCase();
        this.quantity = quantity;
        this.measure = measure;
        this.recipeId = 0;
    }

    // TODO: Decouple recipeId from GroceryItemImpl
    public GroceryItemImpl(long id, String name, double quantity, String measure, long recipeId) {
        this.id = id;
        this.name = name.toLowerCase();
        this.quantity = quantity;
        this.measure = measure;
        this.recipeId = recipeId;
    }

    @JsonProperty("name")
    @Override public String getName() {
        return name;
    }

    @JsonProperty("name")
    @Override public void setName(String name) {
        this.name = name.toLowerCase();
    }

    @JsonProperty("quantity")
    @Override public double getQuantity() {
        return quantity;
    }

    @JsonProperty("quantity")
    @Override public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @JsonProperty("id")
    @Override public long getId() {return id;}

    @JsonProperty("measure")
    @Override public String getMeasure() {return measure;}

    @JsonProperty("measure")
    @Override public void setMeasure(String measure) {this.measure = measure;}

    @JsonProperty("recipeId")
    @Override
    public long getRecipeId() { return recipeId; }

    @Override public boolean equals(GroceryItem g) {
        if (g == null || this.getClass() != g.getClass())
            return false;
        else
            return this.getId() == g.getId();
    }

    @Override
    public String toString() {
        return String.format("id: %s, name: %s, quantity: %s, measure: %s", this.id, this.name, this.quantity, this.measure);
    }
}
