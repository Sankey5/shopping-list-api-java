package com.shoppinglist.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class GroceryItemList {

    private ArrayList<GroceryItem> items;

    public GroceryItemList() {
        items = new ArrayList<GroceryItem>();

        items.add(new GroceryItem("Bananas", 1.0, "lbs"));
        items.add(new GroceryItem("Ground Beef", 2.5, "lbs"));
        items.add(new GroceryItem("Salt", 2.2, "tpbs"));

    }

    public ArrayList<GroceryItem> getItems() { return items;}

    public boolean setItems(ArrayList<GroceryItem> newItems) {
        this.items = newItems;
        return true;
    }

    public boolean addOrUpdateItem(GroceryItem groceryItem) {

        // TODO: Change to a stream
        // If it can be found, update the item
        for(int i = 0; i < items.size(); i++) {
            if(items.get(i).equals(groceryItem)) {
                items.set(i, groceryItem);
                return true;
            }
        }

        // If not, add the new item
        return items.add(groceryItem);
    }
}
