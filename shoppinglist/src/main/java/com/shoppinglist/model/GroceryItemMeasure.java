package com.shoppinglist.model;

import com.shoppinglist.util.StringUtil;

import java.util.Arrays;
import java.util.List;

public enum GroceryItemMeasure {
    // Liquid measurements
    FLUID_OUNCE, FL_OZ,
    TEASPOON, TSP,
    TABLESPOON, TBSP,
    CUP, C,
    PINT, PT,
    QUART, QT,
    GALLON, GAL,

    // Weight measurements
    OUNCE, OZ,
    POUND, LB, LBS,

    //OTHER
    NONE;

    public static GroceryItemMeasure getGroceryItemMeasure(String val) throws IllegalArgumentException {
        return GroceryItemMeasure.valueOf(val.toUpperCase());
    }

    public static List<String> valuesToTitleCase() {
        return Arrays.stream(GroceryItemMeasure.values())
                .map(g -> StringUtil.toTitleCase(g.name()))
                .toList();
    }
}
