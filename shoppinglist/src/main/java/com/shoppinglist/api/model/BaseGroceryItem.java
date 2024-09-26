package com.shoppinglist.api.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.shoppinglist.model.GroceryItemMeasure;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@MappedSuperclass
public abstract class BaseGroceryItem implements GroceryItem{
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "groceryItemId")
    private Long groceryItemId;

    @Override
    @JsonGetter("id")
    public Long getGroceryItemId() {return this.groceryItemId;}

    protected void setGroceryItemId(Long groceryItemId) throws IllegalArgumentException {
        if (groceryItemId == null)
            throw new IllegalArgumentException(String.format("Setting null groceryItemId for grocery item: %s", this));

        this.groceryItemId = groceryItemId;
    }

}
