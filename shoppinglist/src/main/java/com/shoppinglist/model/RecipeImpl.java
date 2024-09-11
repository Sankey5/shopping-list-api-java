package com.shoppinglist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.model.Recipe;
import com.shoppinglist.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "name", "groceryItems"})
public class RecipeImpl implements Recipe {

    @JsonProperty("id")
    private final long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("groceryItems")
    private List<GroceryItem> groceryItems;

    public RecipeImpl(@JsonProperty("name") String name,
                      @JsonProperty("groceryItems") List<GroceryItem> groceryItems) {
        this.id = 0;
        setName(name);
        this.groceryItems = groceryItems;
    }

    public RecipeImpl(long id, String name, List<GroceryItem> groceryItems) {
        this.id = id;
        setName(name);
        this.groceryItems = groceryItems;
    }

    public RecipeImpl(long id, String name) {
        this.id = id;
        setName(name);
        this.groceryItems = new ArrayList<>();
    }

    @JsonProperty("id")
    @Override
    public long getId() {return id;}

    @JsonProperty("name")
    @Override
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    @Override
    public void setName(String name) {
        this.name = StringUtil.toTitleCase(name);
    }

    @JsonProperty("groceryItems")
    @Override
    public List<GroceryItem> getGroceryItems() {return groceryItems;}

    @JsonProperty("groceryItems")
    @Override
    public void setGroceryItems(List<GroceryItem> groceryItems) {
        this.groceryItems = groceryItems;
    }

    @Override
    public boolean isAllDefault() {
        return this.id == 0 && this.name.isEmpty() && this.groceryItems.isEmpty();
    }

    @Override
    public boolean equals(Object g) {
        if (g == null || this.getClass() != g.getClass())
            return false;
        else
            return this.getId() == ((Recipe) g).getId();
    }

    // TODO: Override the hasCode() method

    public String toString() {
        return String.format("Id: %s, Name: %s, GroceryItems: %s", this.id, this.name, this.groceryItems);
    }
}
