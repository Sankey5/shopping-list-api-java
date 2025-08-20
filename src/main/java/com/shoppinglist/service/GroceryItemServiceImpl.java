package com.shoppinglist.service;

import com.google.common.collect.ImmutableList;
import com.shoppinglist.api.dao.GroceryItemDAO;
import com.shoppinglist.api.service.GroceryItemService;
import com.shoppinglist.api.service.GroceryListService;
import com.shoppinglist.model.GroceryItem;
import com.shoppinglist.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class GroceryItemServiceImpl implements GroceryItemService {

    @Autowired
    GroceryItemDAO groceryItemDAO;
    @Autowired
    GroceryListService groceryListService;

    public List<GroceryItem> getGroceryItemsForRecipe(long recipeId) {
        return ImmutableList.copyOf(groceryItemDAO.findAllByRecipeId(recipeId));
    }

    @Override
    public List<GroceryItem> saveGroceryItemsForRecipe(Recipe recipe, List<GroceryItem> newGroceryItems) {
        newGroceryItems = removeEmptyGroceryItems(newGroceryItems);
        List<GroceryItem> concreteGroceryItems = addRecipeToGroceryItemListAndCast(recipe, newGroceryItems);
        return ImmutableList.copyOf(groceryItemDAO.saveAll(concreteGroceryItems));
    }

    @Override
    @Transactional
    public List<GroceryItem> updateGroceryItemsForRecipe(Recipe recipe, List<GroceryItem> updatedGroceryItems) {
        updatedGroceryItems = removeEmptyGroceryItems(updatedGroceryItems);
        List<GroceryItem> concreteGroceryItems = addRecipeToGroceryItemListAndCast(recipe, updatedGroceryItems);
        return ImmutableList.copyOf(groceryItemDAO.saveAll(concreteGroceryItems));
    }

    @Override
    @Transactional
    public void deleteAllGroceryItemsForRecipe(long recipeId) {
        List<GroceryItem> groceryItems = groceryItemDAO.findAllByRecipeId(recipeId);
        List<Long> groceryItemIds = groceryItems.stream()
                .map(GroceryItem::getGroceryItemId)
                .toList();
        groceryListService.deleteAllGroceryListItems(groceryItems);
        groceryItemDAO.deleteAllByGroceryItemIdIn(groceryItemIds);
    }

    @Override
    @Transactional
    public void deleteGroceryItem(long groceryItemId) {
        // TODO: Transform this after grocery list has been converted to use spring data
        this.groceryListService.deleteGroceryListItem(groceryItemId);
        this.groceryItemDAO.deleteByGroceryItemId(groceryItemId);
    }

    private List<GroceryItem> removeEmptyGroceryItems(List<GroceryItem> groceryItems) {
        return groceryItems.stream()
                .filter(groceryItem -> groceryItem != null && !groceryItem.isAllDefault())
                .toList();
    }

    private List<GroceryItem> addRecipeToGroceryItemListAndCast(Recipe recipe, List<GroceryItem> groceryItems) {
        return groceryItems.stream().peek(groceryItem -> groceryItem.setRecipe(recipe)).toList();
    }

}
