package com.shoppinglist.tests;

import com.shoppinglist.api.dao.RecipeDAO;
import com.shoppinglist.api.model.Recipe;
import com.shoppinglist.api.service.GroceryItemService;
import com.shoppinglist.api.service.RecipeService;
import com.shoppinglist.dao.jdbc.RecipeDAOJdbc;
import com.shoppinglist.model.RecipeImpl;
import com.shoppinglist.service.RecipeServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.Answers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@SpringBootTest(classes = {
        RecipeDAOJdbc.class,
        RecipeServiceImpl.class,
})
@ComponentScan("com.shoppinglist")
class RecipeServiceTest {

    @Autowired
    private RecipeService recipeService;
    @MockBean//(answer = Answers.RETURNS_MOCKS)
    private RecipeDAO recipeDAOJdbc;
    @MockBean
    private GroceryItemService groceryItemService;
    private final Recipe expectedRecipe;
    private final Recipe emptyRecipe;
    private final Recipe newRecipeEmptyGroceryList;
    private final Recipe existingRecipePositiveId;
    private final Recipe existingRecipeNegativeId;

    public RecipeServiceTest() {
        this.expectedRecipe = new RecipeImpl(1, "Expected Recipe");
        this.emptyRecipe = new RecipeImpl(null, null);
        this.newRecipeEmptyGroceryList = new RecipeImpl(0, "Test Recipe");
        this.existingRecipePositiveId = new RecipeImpl(1L, "Test Recipe");
        this.existingRecipeNegativeId = new RecipeImpl(-1L, "Test Recipe");
    }

    @Test
    void nullInputReturnsEmptyRecipeObject() {
        Recipe savedRecipe = this.recipeService.saveRecipe(this.emptyRecipe);
        Assertions.assertNotNull(savedRecipe);
    }

    @Test
    void createRecipeWithIdGreaterThanZero() {
        Recipe savedRecipe = this.recipeService.saveRecipe(existingRecipePositiveId);
        Assertions.assertTrue(savedRecipe.isAllDefault());
    }

    @Test
    void createRecipeWithIdLessThanZero() {
        Mockito.when(recipeDAOJdbc.saveRecipe(this.existingRecipeNegativeId.getName()))
                .thenReturn(this.expectedRecipe);
        Recipe savedRecipe = this.recipeService.saveRecipe(this.existingRecipeNegativeId);
        Assertions.assertEquals(this.expectedRecipe.getRecipeId(), savedRecipe.getRecipeId());
        Assertions.assertEquals(this.expectedRecipe.getName(), savedRecipe.getName());
    }

    @Test
    void updateRecipeWithEmptyRecipe() {
        Recipe updateRecipe = this.recipeService.updateRecipe(this.emptyRecipe.getRecipeId(),
                                                                this.emptyRecipe);
        Assertions.assertTrue(updateRecipe.isAllDefault());
    }

    @Test
    void updateRecipeWithIdOfZero() {
        Recipe updateRecipe = this.recipeService.updateRecipe(this.newRecipeEmptyGroceryList.getRecipeId(),
                                                                this.newRecipeEmptyGroceryList);
        Assertions.assertTrue(updateRecipe.isAllDefault());
    }

    @Test
    void deleteRecipeWithNegativeId() {
        boolean recipeDeleted = this.recipeService.deleteRecipe(-1);
        Assertions.assertFalse(recipeDeleted);
    }
}
