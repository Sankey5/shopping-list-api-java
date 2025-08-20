package com.shoppinglist.api.dao;

import com.shoppinglist.model.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface GroceryItemDAO extends JpaRepository<GroceryItem, Long> {
    @Query(value = "SELECT g FROM GroceryItem g WHERE g.recipe.id = :id")
    List<GroceryItem> findAllByRecipeId(long id);
    int deleteAllByGroceryItemIdIn(List<Long> groceryItemIds);
    int deleteByGroceryItemId(Long groceryItemId);
}
