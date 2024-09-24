package com.shoppinglist.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.model.Recipe;
import com.shoppinglist.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "name", "groceryItems"})
public class RecipeImpl implements Recipe {

    @JsonProperty("id")
    private Long recipeId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("groceryItems")
    private List<GroceryItem> groceryItems;

    public RecipeImpl() {
        setRecipeId(0L);
        setName("");
        setGroceryItems(List.of());
    }

    public RecipeImpl(@JsonProperty("name") String name,
                      @JsonProperty("groceryItems") List<GroceryItem> groceryItems) {
        setRecipeId(0L);
        setName(name);
        this.groceryItems = groceryItems;
    }

    public RecipeImpl(long recipeId, String name, List<GroceryItem> groceryItems) {
        setRecipeId(recipeId);
        setName(name);
        this.groceryItems = groceryItems;
    }

    public RecipeImpl(long recipeId, String name) {
        setRecipeId(recipeId);
        setName(name);
        this.groceryItems = new ArrayList<>();
    }

    private void setRecipeId(Long recipeId) {
        if(recipeId == null || recipeId < 0) {
            this.recipeId = 0L;
            return;
        }
        this.recipeId = recipeId;
    }

    @JsonProperty("id")
    @Override
    public Long getRecipeId() {return recipeId;}

    @JsonProperty("name")
    @Override
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    @Override
    public void setName(String name) {
        if (name == null) {
            this.name = "";
            return;
        }
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
        return this.recipeId == 0 && this.name.isEmpty() && this.groceryItems.isEmpty();
    }

    @Override
    public boolean equals(Object g) {
        if (g == null || this.getClass() != g.getClass())
            return false;
        else
            return this.getRecipeId() == ((Recipe) g).getRecipeId();
    }

    // TODO: Override the hasCode() method

    public String toString() {
        return String.format("Id: %s, Name: %s, GroceryItems: %s", this.recipeId, this.name, this.groceryItems);
    }
}
