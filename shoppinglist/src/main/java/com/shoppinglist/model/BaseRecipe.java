package com.shoppinglist.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shoppinglist.api.model.Recipe;
import jakarta.persistence.*;

@MappedSuperclass
public abstract class BaseRecipe implements Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long recipeId;

    @Override
    public Long getRecipeId() {return this.recipeId;}

    @JsonProperty("id")
    protected void setRecipeId(Long recipeId) {
        if(recipeId == null || recipeId < 0) {
            this.recipeId = 0L;
            return;
        }
        this.recipeId = recipeId;
    }
}
