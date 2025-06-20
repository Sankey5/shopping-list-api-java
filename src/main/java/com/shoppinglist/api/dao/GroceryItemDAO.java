package com.shoppinglist.api.dao;

import com.shoppinglist.model.GroceryItemImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface GroceryItemDAO extends JpaRepository<GroceryItemImpl, Long> {
    @Query(value = "SELECT g FROM GroceryItemImpl g WHERE g.recipe.recipeId = :recipeId")
    List<GroceryItemImpl> findAllByRecipeId(long recipeId);
    int deleteAllByGroceryItemIdIn(List<Long> groceryItemIds);
    int deleteByGroceryItemId(Long groceryItemId);
}
