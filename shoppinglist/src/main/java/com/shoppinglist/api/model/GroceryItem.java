package com.shoppinglist.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.shoppinglist.model.GroceryItemImpl;

import java.math.BigDecimal;

@JsonDeserialize(as = GroceryItemImpl.class)
public interface GroceryItem {
    String getName();

    void setName(String name);

    BigDecimal getQuantity();

    void setQuantity(Double quantity);

    void setQuantity(BigDecimal quantity1, BigDecimal quantity2);

    void setQuantityAndMeasure(GroceryItem item1, GroceryItem item2);

    long getId();

    String getMeasure();

    void setMeasure(String measure);
}
