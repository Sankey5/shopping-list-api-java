package com.shoppinglist.model;

import com.fasterxml.jackson.annotation.*;
import com.shoppinglist.api.model.GroceryItem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "name", "quantity", "measure"})
public class GroceryItemImpl implements GroceryItem {

    @JsonProperty("id")
    private final long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("quantity")
    private BigDecimal quantity;
    // TODO: Change the measure into an enum
    @JsonProperty("measure")
    private String measure;

    public GroceryItemImpl(@JsonProperty("name") String name,
                           @JsonProperty("quantity") Double quantity,
                           @JsonProperty("measure") String measure) {
        this.id = 0;
        setName(name);
        setQuantity(quantity);
        setMeasure(measure);
    }

    public GroceryItemImpl(long id,
                           String name,
                           BigDecimal quantity,
                           String measure) {
        this.id = id;
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
        if(Objects.isNull(name))
            name = "";
        this.name = name.toLowerCase();
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

    // TODO: Change to use this instead of taking in the objects quantity and change name
    @Override public void setQuantity(BigDecimal quantity1, BigDecimal quantity2) {
        this.quantity = quantity1.add(quantity2)
                                .setScale(3, RoundingMode.HALF_UP)
                                .stripTrailingZeros();
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
    @Override public long getId() {return id;}

    @JsonGetter("measure")
    @Override public String getMeasure() {return measure;}

    @JsonSetter("measure")
    @Override public void setMeasure(String measure) {
        if (Objects.isNull(measure))
            measure = "";

        this.measure = measure;
    }

    @Override public boolean isAllDefault() {
        return this.name.isEmpty()
                && this.quantity.equals(BigDecimal.valueOf(0.0).setScale(3, RoundingMode.HALF_UP).stripTrailingZeros())
                && this.measure.isEmpty();
    }

    @Override public boolean equals(Object g) {
        if (g == null || this.getClass() != g.getClass())
            return false;
        else
            return this.getId() == ((GroceryItem) g).getId();
    }

    // TODO: Override the hasCode() method

    @Override
    public String toString() {
        return String.format("id: %s, name: %s, quantity: %s, measure: %s", this.id, this.name, this.quantity.toPlainString(), this.measure);
    }
}
