package com.shoppinglist.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.collect.ImmutableList;
import com.shoppinglist.api.model.BaseRecipe;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.model.Recipe;
import com.shoppinglist.util.StringUtil;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Access(AccessType.FIELD)
@Table(name = "Recipe")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "name", "groceryItems"})
public class RecipeImpl extends BaseRecipe {

    @JsonProperty("name")
    private String name;
    @JsonProperty("groceryItems")
    @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER)
    private List<GroceryItemImpl> groceryItems;

    public RecipeImpl() {
        super.setRecipeId(0L);
        this.setName("");
        this.setGroceryItems(List.of());
    }

    public RecipeImpl(@JsonProperty("name") String name,
                      @JsonProperty("groceryItems") List<GroceryItem> groceryItems) {
        super.setRecipeId(0L);
        this.setName(name);
        this.setGroceryItems(groceryItems);
    }

    public RecipeImpl(long recipeId, String name, List<GroceryItem> groceryItems) {
        super.setRecipeId(recipeId);
        this.setName(name);
        this.setGroceryItems(groceryItems);
    }

    // Used for lazy-loading the recipes
    public RecipeImpl(long recipeId, String name) {
        super.setRecipeId(recipeId);
        this.setName(name);
        this.groceryItems = new ArrayList<>();
    }

    @Override
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @Override
    @JsonProperty("name")
    public void setName(String name) {
        if (name == null) {
            this.name = "";
            return;
        }
        this.name = StringUtil.toTitleCase(name);
    }

    @Override
    @JsonProperty("groceryItems")
    public List<GroceryItem> getGroceryItems() {
        return ImmutableList.copyOf(this.groceryItems);
    }

    @Override
    @JsonProperty("groceryItems")
    public void setGroceryItems(List<GroceryItem> groceryItems) {
        this.groceryItems = groceryItems.stream().map(g -> {
           if(g.getClass() != GroceryItemImpl.class) {
               throw new IllegalArgumentException(
                       String.format(
                               "Attempt to convert grocery item to solid implementation failed for grocery item: %s",
                               g)
               );
           }
           return (GroceryItemImpl) g;
        }).toList();
    }

    @Override
    public boolean isAllDefault() {
        return this.getRecipeId() == 0 && this.name.isEmpty() && this.groceryItems.isEmpty();
    }

    @Override
    public boolean equals(Object g) {
        if (g == null || this.getClass() != g.getClass())
            return false;
        else
            return super.getRecipeId() == ((Recipe) g).getRecipeId();
    }

    // TODO: Override the hasCode() method

    public String toString() {
        return String.format("Id: %s, Name: %s, GroceryItems: %s", super.getRecipeId(), this.name, this.groceryItems);
    }
}
