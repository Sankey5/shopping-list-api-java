package com.shoppinglist.model;

import com.fasterxml.jackson.annotation.*;
import com.shoppinglist.api.model.GroceryItem;
import com.shoppinglist.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "name", "quantity", "measure"})
public class GroceryItemImpl implements GroceryItem {

    private final Logger LOGGER = LoggerFactory.getLogger(GroceryItemImpl.class);

    @JsonProperty("id")
    private final long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("quantity")
    private BigDecimal quantity;
    @JsonProperty("measure")
    private GroceryItemMeasure measure;

    public GroceryItemImpl(@JsonProperty("name") String name,
                           @JsonProperty("quantity") Double quantity,
                           @JsonProperty("measure") String measure) {
        System.out.println("Creating groceryItemImpl empty");
        this.id = 0;
        setName(name);
        System.out.println("Name set");
        setQuantity(quantity);
        System.out.println("quantity set");
        setMeasure(measure);

        System.out.printf("Current grocery item %s%n", this);
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
    @Override public long getId() {return id;}

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
            return this.getId() == ((GroceryItem) g).getId();
    }

    // TODO: Override the hasCode() method

    @Override
    public String toString() {
        return String.format("id: %s, name: %s, quantity: %s, measure: %s", this.id, this.name, this.quantity.toPlainString(), this.measure.name());
    }
}
