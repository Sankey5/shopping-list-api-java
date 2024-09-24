package com.shoppinglist.tests;

import com.shoppinglist.api.dao.GroceryItemDAO;
import com.shoppinglist.api.dao.RecipeDAO;
import com.shoppinglist.api.model.Recipe;
import com.shoppinglist.api.service.GroceryItemService;
import com.shoppinglist.api.service.RecipeService;
import com.shoppinglist.dao.jdbc.GroceryItemDAOJdbc;
import com.shoppinglist.dao.jdbc.RecipeDAOJdbc;
import com.shoppinglist.model.GroceryItemImpl;
import com.shoppinglist.model.RecipeImpl;
import com.shoppinglist.service.GroceryItemServiceImpl;
import com.shoppinglist.service.RecipeServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@SpringBootTest(classes = {
        RecipeDAOJdbc.class,
        RecipeServiceImpl.class,
})
@ComponentScan("com.shoppinglist")
class RecipeServiceTest {

    @Autowired
    RecipeService recipeService;
    @MockBean
    RecipeDAO recipeDAOJdbc;
    @MockBean
    GroceryItemService groceryItemService;

    public RecipeServiceTest() {
        //this.testRecipe = new RecipeImpl("Chicken Pot Pie", List.of());
    }

    @Test
    void nullInputReturnsEmptyRecipeObject() {
        Recipe savedRecipe = this.recipeService.saveRecipe(new RecipeImpl(null, null));
        Assertions.assertNotNull(savedRecipe);
    }

    @Test
    void createRecipeWithIdGreaterThanZero() {
        RecipeImpl recipeWithNonZeroId = new RecipeImpl(1L, "Bad Recipe");
        Recipe savedRecipe = this.recipeService.saveRecipe(recipeWithNonZeroId);
        Assertions.assertTrue(savedRecipe.isAllDefault());
    }

    @Test
    void createRecipeWithIdLessThanZero() {
        RecipeImpl recipeWithNonZeroId = new RecipeImpl(-1L, "Bad Recipe");
        Recipe savedRecipe = this.recipeService.saveRecipe(recipeWithNonZeroId);
        Assertions.assertTrue(savedRecipe.isAllDefault());
    }

}
