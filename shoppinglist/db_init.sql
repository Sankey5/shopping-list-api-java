-- PRIMARY MODELS

CREATE TABLE IF NOT EXISTS Recipe (
    recipeId BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL,
    PRIMARY KEY (recipeId)
);

CREATE TABLE IF NOT EXISTS GroceryItem (
    groceryItemId BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL,
    quantity NUMERIC NOT NULL,
    measure VARCHAR(16) NOT NULL,
    recipeId BIGINT NOT NULL,
    PRIMARY KEY (groceryItemId),
    FOREIGN KEY (recipeId) REFERENCES Recipe(recipeId)
);

-- HELPER TABLES

-- Accumulation of Grocery Items on the shopping list
CREATE TABLE IF NOT EXISTS GroceryList (
    groceryItemId BIGINT NOT NULL,
    FOREIGN KEY (groceryItemId) REFERENCES GroceryItem(groceryItemId)
);

-- Insert in test data if it doesn't exist
--INSERT INTO Recipes (Name) VALUES
--    ('Chicken Bake'),
--    ('Lasagna'),
--    ('Chicken Pot Pie');
--
--INSERT INTO GroceryItems (GroceryItemId, Name, Quantity, Measure, RecipeId)
--VALUES
--(1, 'Chicken', 3.0, 'lbs', (SELECT RecipeId FROM Recipes WHERE Recipes.Name = 'Chicken Bake')),
--(2, 'Mozzarella Cheese', 1.5, 'lbs', (SELECT RecipeId FROM Recipes WHERE Recipes.Name = 'Chicken Bake')),
--(3, 'Spaghetti Sauce', 16.0, 'oz', (SELECT RecipeId FROM Recipes WHERE Recipes.Name = 'Chicken Bake'));
--
--INSERT INTO GroceryItems (GroceryItemId, Name, Quantity, Measure, RecipeId)
--VALUES
--(4, 'Ground Beef', 2.0, 'lbs', (SELECT RecipeId FROM Recipes WHERE Recipes.Name = 'Lasagna')),
--(5, 'Lasagna Noodles', 16.0, 'oz', (SELECT RecipeId FROM Recipes WHERE Recipes.Name = 'Lasagna')),
--(6, 'Mozzarella Cheese', 2.0, 'lbs', (SELECT RecipeId FROM Recipes WHERE Recipes.Name = 'Lasagna'));
--
--INSERT INTO GroceryItems (GroceryItemId, Name, Quantity, Measure, RecipeId)
--VALUES
--(7, 'Chicken', 3.0, 'lbs', (SELECT RecipeId FROM Recipes WHERE Recipes.Name = 'Chicken Pot Pie')),
--(8, 'Frozen Mixed Vegetables', 16.0, 'oz', (SELECT RecipeId FROM Recipes WHERE Recipes.Name = 'Chicken Pot Pie')),
--(9, 'Butter', 1.5, 'lbs', (SELECT RecipeId FROM Recipes WHERE Recipes.Name = 'Chicken Pot Pie'));