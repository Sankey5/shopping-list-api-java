package com.shoppinglist.api.dao;

import com.shoppinglist.model.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface GroceryListDAO extends JpaRepository<GroceryItem, Long> {
    void deleteFirstByGroceryItemId(long groceryItemId);
}
