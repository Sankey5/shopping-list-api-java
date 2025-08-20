package com.shoppinglist.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.shoppinglist.util.StringUtil;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Access(AccessType.FIELD)
@Table(name = "Recipe")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "name", "groceryItems"})
public class Recipe {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("groceryItems")
    @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER)
    private List<GroceryItem> groceryItems;

    public Recipe() {
        this.setId(0L);
        this.setName("");
        this.setGroceryItems(List.of());
    }

    public Recipe(@JsonProperty("name") String name,
                  @JsonProperty("groceryItems") List<GroceryItem> groceryItems) {
        this.setId(0L);
        this.setName(name);
        this.setGroceryItems(groceryItems);
    }

    public Recipe(long recipeId, String name, List<GroceryItem> groceryItems) {
        this.setId(recipeId);
        this.setName(name);
        this.setGroceryItems(groceryItems);
    }

    // Used for lazy-loading the recipes
    public Recipe(long recipeId, String name) {
        this.setId(recipeId);
        this.setName(name);
        this.groceryItems = new ArrayList<>();
    }
    
    @JsonProperty("id")
    public Long getId() {
        return this.id;
    }
    
    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }
    
    @JsonProperty("name")
    public String getName() {
        return name;
    }
    
    @JsonProperty("name")
    public void setName(String name) {
        if (name == null) {
            this.name = "";
            return;
        }
        this.name = StringUtil.toTitleCase(name);
    }

    @JsonProperty("groceryItems")
    public List<GroceryItem> getGroceryItems() {
        return new ArrayList<>(this.groceryItems);
    }

    @JsonProperty("groceryItems")
    public void setGroceryItems(List<GroceryItem> groceryItems) {
        this.groceryItems = groceryItems;
    }

    public boolean isAllDefault() {
        return this.getId() == 0 && this.name.isEmpty() && this.groceryItems.isEmpty();
    }


    public boolean equals(Object g) {
        if (g == null || this.getClass() != g.getClass())
            return false;
        else
            return Objects.equals(this.getId(), ((Recipe) g).getId());
    }

    // TODO: Override the hasCode() method
    public String toString() {
        return String.format("Id: %s, Name: %s, GroceryItems: %s", this.getId(), this.name, this.groceryItems);
    }
}
