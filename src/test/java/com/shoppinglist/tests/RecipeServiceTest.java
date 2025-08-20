package com.shoppinglist.tests;

//@SpringBootTest(classes = {
//        RecipeDAOJdbc.class,
//        RecipeServiceImpl.class,
//})
//class RecipeServiceTest {
//
//    @Autowired
//    private RecipeService recipeService;
//    @MockBean
//    private RecipeDAO recipeDAOJdbc;
//    @MockBean
//    private GroceryItemService groceryItemService;
//    private final Recipe expectedRecipe;
//    private final Recipe emptyRecipe;
//    private final Recipe newRecipeEmptyGroceryList;
//    private final Recipe existingRecipePositiveId;
//    private final Recipe existingRecipeNegativeId;
//
//    public RecipeServiceTest() {
//        this.expectedRecipe = new RecipeImpl(1, "Expected Recipe");
//        this.emptyRecipe = new RecipeImpl(null, null);
//        this.newRecipeEmptyGroceryList = new RecipeImpl(0, "Test Recipe");
//        this.existingRecipePositiveId = new RecipeImpl(1L, "Test Recipe");
//        this.existingRecipeNegativeId = new RecipeImpl(-1L, "Test Recipe");
//    }
//
//    @Test
//    void nullInputReturnsEmptyRecipeObject() {
//        Recipe savedRecipe = this.recipeService.saveRecipe(this.emptyRecipe);
//        Assertions.assertNotNull(savedRecipe);
//    }
//
//    @Test
//    void createRecipeWithIdGreaterThanZero() {
//        Recipe savedRecipe = this.recipeService.saveRecipe(existingRecipePositiveId);
//        Assertions.assertTrue(savedRecipe.isAllDefault());
//    }
//
//    @Test
//    void createRecipeWithIdLessThanZero() {
//        Mockito
//                .when(recipeDAOJdbc.saveRecipe(this.existingRecipeNegativeId.getName()))
//                .thenReturn(this.expectedRecipe);
//        Recipe savedRecipe = this.recipeService.saveRecipe(this.existingRecipeNegativeId);
//        Assertions.assertEquals(this.expectedRecipe.getRecipeId(), savedRecipe.getRecipeId());
//        Assertions.assertEquals(this.expectedRecipe.getName(), savedRecipe.getName());
//    }
//
//    @Test
//    void updateRecipeWithEmptyRecipe() {
//        Recipe updateRecipe = this.recipeService.updateRecipe(this.emptyRecipe.getRecipeId(),
//                                                                this.emptyRecipe);
//        Assertions.assertTrue(updateRecipe.isAllDefault());
//    }
//
//    @Test
//    void updateRecipeWithIdOfZero() {
//        Recipe updateRecipe = this.recipeService.updateRecipe(this.newRecipeEmptyGroceryList.getRecipeId(),
//                                                                this.newRecipeEmptyGroceryList);
//        Assertions.assertTrue(updateRecipe.isAllDefault());
//    }
//
//    @Test
//    void deleteRecipeWithNegativeId() {
//        boolean recipeDeleted = this.recipeService.deleteRecipe(-1);
//        Assertions.assertFalse(recipeDeleted);
//    }
//}
