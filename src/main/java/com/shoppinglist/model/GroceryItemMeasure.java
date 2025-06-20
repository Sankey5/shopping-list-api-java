package com.shoppinglist.model;

import com.shoppinglist.util.StringUtil;

import java.util.Arrays;
import java.util.List;

public enum GroceryItemMeasure {
    // Dry measurements
    OUNCE("dry", 1),
    OZ("dry", 1),
    POUND("dry", 16),
    LB("dry", 16),
    LBS("dry", 16),

    // Liquid measurements
    PINCH("liquid", 1.0/12.0),
    TEASPOON("liquid",1.0/6.0),
    TSP("liquid",1.0/6.0),
    TABLESPOON("liquid",0.5),
    TBSP("liquid",0.5),
    CUP("liquid",8),
    C("liquid",8),
    PINT("liquid",16),
    PT("liquid",16),
    QUART("liquid",32),
    QT("liquid",32),
    GALLON("liquid",64),
    GAL("liquid",64),
    FLUID_OUNCE("liquid",1),
    FL_OZ("liquid",1),

    //OTHER
    NONE("", 0);
    
    private final String measurementType;
    private final double ounces;

    GroceryItemMeasure(String measurementType, double ounces) {
        this.measurementType = measurementType;
        this.ounces = ounces;
    }

    public double getOunces() {return this.ounces;}

    public String getMeasurementType() { return this.measurementType;}

    public static GroceryItemMeasure getGroceryItemMeasure(String val) throws IllegalArgumentException {
        return GroceryItemMeasure.valueOf(val.toUpperCase());
    }

    public static List<String> valuesToTitleCase() {
        return Arrays.stream(GroceryItemMeasure.values())
                .map(g -> StringUtil.toTitleCase(g.name()))
                .toList();
    }
}
