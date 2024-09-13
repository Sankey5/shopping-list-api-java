package com.shoppinglist.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.shoppinglist.model.GroceryItemImpl;

import java.math.BigDecimal;

@JsonDeserialize(as = GroceryItemImpl.class)
public interface GroceryItem {
    String getName();

    void setName(String name);

    BigDecimal getQuantity();

    void setQuantity(Double quantity) throws IllegalArgumentException;

    void addQuantityAndMeasure(GroceryItem item1, GroceryItem item2);

    Long getGroceryItemId();

    String getMeasure();

    void setMeasure(String measure);

    boolean isAllDefault();
}
