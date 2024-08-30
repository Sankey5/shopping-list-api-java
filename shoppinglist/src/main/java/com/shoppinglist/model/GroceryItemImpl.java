package com.shoppinglist.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.shoppinglist.api.model.GroceryItem;

import java.math.BigDecimal;
import java.math.RoundingMode;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "name", "quantity", "measure", "recipeId"})
public class GroceryItemImpl implements GroceryItem {

    @JsonProperty("id")
    private final long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("quantity")
    private BigDecimal quantity;
    @JsonProperty("measure")
    private String measure;
    @JsonProperty("recipeId")
    private final long recipeId;

    public GroceryItemImpl(@JsonProperty("name") String name,
                           @JsonProperty("quantity") BigDecimal quantity,
                           @JsonProperty("measure") String measure,
                           @JsonProperty("recipeId") long recipeId) {
        this.id = 0;
        this.name = name.toLowerCase();
        this.quantity = quantity.setScale(3, RoundingMode.HALF_UP).stripTrailingZeros();
        this.measure = measure;
        this.recipeId = recipeId;
    }

    public GroceryItemImpl(@JsonProperty("id") long id,
                           @JsonProperty("name") String name,
                           @JsonProperty("quantity") BigDecimal quantity,
                           @JsonProperty("measure") String measure,
                           @JsonProperty("recipeId") long recipeId) {
        this.id = id;
        this.name = name.toLowerCase();
        this.quantity = quantity.setScale(3, RoundingMode.HALF_UP).stripTrailingZeros();
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
    @Override public BigDecimal getQuantity() {
        return quantity;
    }

    @Override public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity.setScale(3, RoundingMode.HALF_UP).stripTrailingZeros();
    }

    @JsonProperty("quantity")
    @Override public void setQuantity(Double quantity) {
        this.setQuantity(BigDecimal.valueOf(quantity));
    }

    @JsonProperty("id")
    @Override public long getId() {return id;}

    @JsonProperty("measure")
    @Override public String getMeasure() {return measure;}

    @JsonProperty("measure")
    @Override public void setMeasure(String measure) {this.measure = measure;}

    @JsonProperty("recipeId")
    @Override public long getRecipeId() { return this.recipeId; }

    @Override public boolean equals(Object g) {
        if (g == null || this.getClass() != g.getClass())
            return false;
        else
            return this.getId() == ((GroceryItem) g).getId();
    }

    // TODO: Override the hasCode() method

    @Override
    public String toString() {
        return String.format("id: %s, name: %s, quantity: %s, measure: %s", this.id, this.name, this.quantity, this.measure);
    }
}
