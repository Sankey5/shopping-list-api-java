package com.shoppinglist.model;

import com.fasterxml.jackson.annotation.*;
import com.shoppinglist.api.model.BaseGroceryItem;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.api.model.Recipe;
import com.shoppinglist.util.StringUtil;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Entity
@Access(AccessType.FIELD)
@Table(name = "GroceryItem")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "name", "quantity", "measure"})
public class GroceryItemImpl extends BaseGroceryItem {

    @JsonProperty("name")
    private String name;
    @JsonProperty("quantity")
    private BigDecimal quantity;
    @Enumerated(EnumType.STRING)
    @JsonProperty("measure")
    private GroceryItemMeasure measure;
    @ManyToOne
    @JoinColumn(name = "recipeId")
    private RecipeImpl recipe;

    public GroceryItemImpl() {
        super.setGroceryItemId(0L);
        this.setName("");
        this.setQuantity(0.0);
        this.setMeasure("");
        this.setRecipe(null);
    }

    public GroceryItemImpl(@JsonProperty("name") String name,
                           @JsonProperty("quantity") Double quantity,
                           @JsonProperty("measure") String measure) {
        super.setGroceryItemId(0L);
        this.setName(name);
        this.setQuantity(quantity);
        this.setMeasure(measure);
        this.setRecipe(null);
    }

    public GroceryItemImpl(Long groceryItemId,
                           String name,
                           BigDecimal quantity,
                           String measure,
                           Recipe recipe) {
        super.setGroceryItemId(groceryItemId);
        this.setName(name);
        this.setQuantity(quantity);
        this.setMeasure(measure);
        this.setRecipe(recipe);
    }

    @JsonGetter("name")
    @Override public String getName() {
        return name;
    }

    @JsonSetter("name")
    @Override public void setName(String name) {
        if(Objects.isNull(name)) {
            name = "";
        }
        this.name = StringUtil.toTitleCase(name);
    }

    @Override
    @JsonGetter("quantity")
    public BigDecimal getQuantity() {
        return quantity;
    }

    @Override
    @JsonSetter("quantity")
    public void setQuantity(Double quantity) {
        if(Objects.isNull(quantity))
            quantity = 0.0;

        this.setQuantity(BigDecimal.valueOf(quantity));
    }

    private void setQuantity(BigDecimal newQuantity) {
        this.quantity = newQuantity.setScale(3, RoundingMode.HALF_UP).stripTrailingZeros();
    }

    // TODO: Change to use this instead of taking in the objects quantity
    @Override public void addQuantityAndMeasure(GroceryItem item1, GroceryItem item2) {
        // TODO: Implement future "smart" measure changes based on the quantity computed
        BigDecimal quantity1 = item1.getQuantity();
        BigDecimal quantity2 = item2.getQuantity();

        this.quantity = quantity1.add(quantity2)
                .setScale(3, RoundingMode.HALF_UP)
                .stripTrailingZeros();
    }

    @Override
    @JsonGetter("measure")
    public String getMeasure() {return StringUtil.toTitleCase(measure.name());}

    @Override
    @JsonSetter("measure")
    public void setMeasure(String measure) throws IllegalArgumentException {
        if (Objects.isNull(measure) || measure.isEmpty()) {
            this.measure = GroceryItemMeasure.NONE;
            return;
        }
        this.measure = GroceryItemMeasure.getGroceryItemMeasure(measure);
    }

    @Override
    public Recipe getRecipe() {
        return this.recipe;
    }

    @Override
    public void setRecipe(Recipe recipe) {
        this.recipe = (RecipeImpl) recipe;
    }

    @Override
    public boolean isAllDefault() {
        return this.name.isEmpty()
                && this.quantity.equals(BigDecimal.valueOf(0.0)
                                                    .setScale(3,
                                                            RoundingMode.HALF_UP)
                                                    .stripTrailingZeros())
                && this.measure == GroceryItemMeasure.NONE;
    }

    @Override
    public boolean equals(Object g) {
        if (g == null || this.getClass() != g.getClass())
            return false;
        else
            return Objects.equals(super.getGroceryItemId(),
                                ((GroceryItem) g).getGroceryItemId());
    }

    // TODO: Override the hasCode() method

    @Override
    public String toString() {
        return String.format("id: %s, name: %s, quantity: %s, measure: %s",
                super.getGroceryItemId(),
                this.name,
                this.quantity.toPlainString(),
                this.measure.name());
    }
}
