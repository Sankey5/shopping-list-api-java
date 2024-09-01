package com.shoppinglist.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.shoppinglist.model.GroceryListItemImpl;

import java.math.BigDecimal;

@JsonDeserialize(as = GroceryListItemImpl.class)
public interface GroceryListItem extends GroceryItem {

    long getGroceryListItemId();
}
