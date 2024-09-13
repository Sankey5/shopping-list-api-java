package com.shoppinglist.model;

import com.fasterxml.jackson.annotation.*;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.util.StringUtil;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "name", "quantity", "measure"})
@Entity
public class GroceryItemImpl implements GroceryItem {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long groceryItemId;

    @JsonProperty("name")
    private String name;
    @JsonProperty("quantity")
    private BigDecimal quantity;
    @JsonProperty("measure")
    private GroceryItemMeasure measure;

    public GroceryItemImpl(@JsonProperty("name") String name,
                           @JsonProperty("quantity") Double quantity,
                           @JsonProperty("measure") String measure) {
        this.groceryItemId = null;
        setName(name);
        setQuantity(quantity);
        setMeasure(measure);
    }

    public GroceryItemImpl(Long groceryItemId,
                           String name,
                           BigDecimal quantity,
                           String measure) {
        setGroceryItemId(groceryItemId);
        setName(name);
        setQuantity(quantity);
        setMeasure(measure);
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

    @JsonGetter("quantity")
    @Override public BigDecimal getQuantity() {
        return quantity;
    }

    @JsonSetter("quantity")
    @Override public void setQuantity(Double quantity) {
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

    @JsonGetter("id")
    @Override public Long getGroceryItemId() {return groceryItemId;}

    private void setGroceryItemId(Long groceryItemId) throws IllegalArgumentException {
        if (Objects.isNull(groceryItemId))
            throw new IllegalArgumentException(String.format("Setting null groceryItemId for grocery item: %s", this));

        this.groceryItemId = groceryItemId;
    }

    @JsonGetter("measure")
    @Override public String getMeasure() {return StringUtil.toTitleCase(measure.name());}

    @JsonSetter("measure")
    @Override public void setMeasure(String measure) throws IllegalArgumentException {
        if (Objects.isNull(measure) || measure.isEmpty()) {
            this.measure = GroceryItemMeasure.NONE;
            return;
        }

        this.measure = GroceryItemMeasure.getGroceryItemMeasure(measure);
    }

    @Override public boolean isAllDefault() {
        return this.name.isEmpty()
                && this.quantity.equals(BigDecimal.valueOf(0.0).setScale(3, RoundingMode.HALF_UP).stripTrailingZeros())
                && this.measure == GroceryItemMeasure.NONE;
    }

    @Override public boolean equals(Object g) {
        if (g == null || this.getClass() != g.getClass())
            return false;
        else
            return Objects.equals(this.getGroceryItemId(), ((GroceryItem) g).getGroceryItemId());
    }

    // TODO: Override the hasCode() method

    @Override
    public String toString() {
        return String.format("id: %s, name: %s, quantity: %s, measure: %s", this.groceryItemId, this.name, this.quantity.toPlainString(), this.measure.name());
    }
}
