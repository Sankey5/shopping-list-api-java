package com.shoppinglist.model;

import com.fasterxml.jackson.annotation.*;
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
public class GroceryItem {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "groceryItemId")
    private Long groceryItemId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("quantity")
    private BigDecimal quantity;
    @Enumerated(EnumType.STRING)
    @JsonProperty("measure")
    private GroceryItemMeasure measure;
    @ManyToOne
    @JoinColumn(name = "recipeId")
    private Recipe recipe;

    public GroceryItem() {
        this.setGroceryItemId(0L);
        this.setName("");
        this.setQuantity(0.0);
        this.setMeasure("");
        this.setRecipe(null);
    }

    public GroceryItem(@JsonProperty("name") String name,
                       @JsonProperty("quantity") Double quantity,
                       @JsonProperty("measure") String measure) {
        this.setGroceryItemId(0L);
        this.setName(name);
        this.setQuantity(quantity);
        this.setMeasure(measure);
        this.setRecipe(null);
    }

    public GroceryItem(Long groceryItemId,
                       String name,
                       BigDecimal quantity,
                       String measure,
                       Recipe recipe) {
        this.setGroceryItemId(groceryItemId);
        this.setName(name);
        this.setQuantity(quantity);
        this.setMeasure(measure);
        this.setRecipe(recipe);
    }

    @JsonGetter("id")
    public Long getGroceryItemId() {return this.groceryItemId;}

    @JsonSetter("id")
    public void setGroceryItemId(Long groceryItemId) throws IllegalArgumentException {
        if (groceryItemId == null)
            throw new IllegalArgumentException(String.format("Setting null groceryItemId for grocery item: %s", this));

        this.groceryItemId = groceryItemId;
    }

    @JsonGetter("name")
    public String getName() {
        return name;
    }

    @JsonSetter("name")
    public void setName(String name) {
        if(Objects.isNull(name)) {
            name = "";
        }
        this.name = StringUtil.toTitleCase(name);
    }
    
    @JsonGetter("quantity")
    public BigDecimal getQuantity() {
        return quantity;
    }

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
    public void addQuantityAndMeasure(GroceryItem item1, GroceryItem item2) {
        // TODO: Implement future "smart" measure changes based on the quantity computed
        BigDecimal quantity1 = item1.getQuantity();
        BigDecimal quantity2 = item2.getQuantity();

        this.quantity = quantity1.add(quantity2)
                .setScale(3, RoundingMode.HALF_UP)
                .stripTrailingZeros();
    }
    
    @JsonGetter("measure")
    public String getMeasure() {return StringUtil.toTitleCase(measure.name());}
    
    @JsonSetter("measure")
    public void setMeasure(String measure) throws IllegalArgumentException {
        if (Objects.isNull(measure) || measure.isEmpty()) {
            this.measure = GroceryItemMeasure.NONE;
            return;
        }
        this.measure = GroceryItemMeasure.getGroceryItemMeasure(measure);
    }

    
    public Recipe getRecipe() {
        return this.recipe;
    }

    
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    
    public boolean isAllDefault() {
        return this.name.isEmpty()
                && this.quantity.equals(BigDecimal.valueOf(0.0)
                                                    .setScale(3,
                                                            RoundingMode.HALF_UP)
                                                    .stripTrailingZeros())
                && this.measure == GroceryItemMeasure.NONE;
    }

    
    public boolean equals(Object g) {
        if (g == null || this.getClass() != g.getClass())
            return false;
        else
            return Objects.equals(this.getGroceryItemId(),
                                ((GroceryItem) g).getGroceryItemId());
    }

    // TODO: Override the hasCode() method

    
    public String toString() {
        return String.format("id: %s, name: %s, quantity: %s, measure: %s",
                this.getGroceryItemId(),
                this.name,
                this.quantity.toPlainString(),
                this.measure.name());
    }
}
