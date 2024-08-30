package com.shoppinglist.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.shoppinglist.model.GroceryItemImpl;

import java.math.BigDecimal;

@JsonDeserialize(as = GroceryItemImpl.class)
public interface GroceryItem {
    String getName();

    void setName(String name);

    BigDecimal getQuantity();

    void setQuantity(BigDecimal quantity);

    void setQuantity(Double quantity);

    long getId();

    String getMeasure();

    void setMeasure(String measure);
}
