package com.shoppinglist.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.model.GroceryListItem;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GroceryListItemImpl extends GroceryItemImpl implements GroceryListItem {

    private final long groceryListItemId;

    public GroceryListItemImpl(String name,
                               BigDecimal quantity,
                               String measure) {
        super(name, quantity, measure);
        this.groceryListItemId = 0;
    }

    public GroceryListItemImpl(long id,
                               String name,
                               BigDecimal quantity,
                               String measure,
                               long groceryListItemId) {
        super(id, name, quantity, measure);
        this.groceryListItemId = groceryListItemId;
    }

    @Override
    public long getGroceryListItemId() {
        return this.groceryListItemId;
    }

    @Override public boolean equals(Object gl) {
        if (gl == null || this.getClass() != gl.getClass())
            return false;
        else
            return this.getGroceryListItemId() == ((GroceryListItemImpl) gl).getGroceryListItemId();
    }

    // TODO: Override the hasCode() method

    @Override
    public String toString() {
        return String.format( "%s Grocery List Item Id: %s", super.toString(), this.groceryListItemId);
    }
}
