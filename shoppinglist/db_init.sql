--Drop old test data
--DROP TABLE IF EXISTS ShoppingList;
DROP TABLE IF EXISTS Recipe_GroceryItems;
DROP TABLE IF EXISTS GroceryItems;
DROP TABLE IF EXISTS Recipes;
DROP TABLE IF EXISTS ShoppingList;

-- PRIMARY MODELS

CREATE TABLE IF NOT EXISTS Recipe (
    RecipeId INT NOT NULL AUTO_INCREMENT,
    Name VARCHAR(30) NOT NULL,
    PRIMARY KEY (RecipeId)
);

CREATE TABLE IF NOT EXISTS GroceryItem (
    GroceryItemId INT NOT NULL AUTO_INCREMENT,
    Name VARCHAR(30) NOT NULL,
    Quantity DOUBLE NOT NULL,
    Measure VARCHAR(10) NOT NULL,
    RecipeId INT NOT NULL,
    PRIMARY KEY (GroceryItemId),
    FOREIGN KEY (RecipeId) REFERENCES Recipe(RecipeId)
);

-- HELPER TABLES

-- Accumulation of Grocery Items on the shopping list
CREATE TABLE IF NOT EXISTS GroceryList (
    GroceryItemId INT NOT NULL,
    FOREIGN KEY (GroceryItemId) REFERENCES GroceryItem(GroceryItemId)
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