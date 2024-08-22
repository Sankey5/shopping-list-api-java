package com.shoppinglist.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.shoppinglist.model.GroceryItemImpl;

@JsonDeserialize(as = GroceryItemImpl.class)
public interface GroceryItem {
    String getName();

    void setName(String name);

    double getQuantity();

    void setQuantity(double quantity);

    long getId();

    String getMeasure();

    void setMeasure(String measure);

    long getRecipeId();

    boolean equals(GroceryItem g);
}
