package com.shoppinglist.api.dao;

import com.shoppinglist.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface RecipeDAO extends JpaRepository<Recipe, Long> {

}
