--Drop old test data
DROP TABLE IF EXISTS GroceryItems;
DROP TABLE IF EXISTS Recipes;

CREATE TABLE IF NOT EXISTS Recipes (
    RecipeId INT AUTO_INCREMENT,
    Name VARCHAR(30) NOT NULL,
    PRIMARY KEY (RecipeId));

CREATE TABLE IF NOT EXISTS GroceryItems(
    GroceryItemId INT AUTO_INCREMENT,
    Name VARCHAR(30) NOT NULL,
    Quantity DOUBLE NOT NULL,
    Measure VARCHAR(10) NOT NULL,
    RecipeId INT NOT NULL,
    FOREIGN KEY (RecipeId) REFERENCES Recipes(RecipeId),
    CONSTRAINT PK_GroceryItem PRIMARY KEY (GroceryItemId, RecipeId));

-- Insert in test data if it doesn't exist
INSERT INTO Recipes (RecipeId, Name) VALUES
    (1, 'Chicken Bake'),
    (2, 'Lasagna'),
    (3, 'Chicken Pot Pie');

INSERT INTO GroceryItems (GroceryItemId, Name, Quantity, Measure, RecipeId)
VALUES
(1, 'Chicken', 3.0, 'lbs', (SELECT RecipeId FROM Recipes WHERE Recipes.Name = 'Chicken Bake')),
(2, 'Mozzarella Cheese', 1.5, 'lbs', (SELECT RecipeId FROM Recipes WHERE Recipes.Name = 'Chicken Bake')),
(3, 'Spaghetti Sauce', 16.0, 'oz', (SELECT RecipeId FROM Recipes WHERE Recipes.Name = 'Chicken Bake'));

INSERT INTO GroceryItems (GroceryItemId, Name, Quantity, Measure, RecipeId)
VALUES
(4, 'Ground Beef', 2.0, 'lbs', (SELECT RecipeId FROM Recipes WHERE Recipes.Name = 'Lasagna')),
(5, 'Lasagna Noodles', 16.0, 'oz', (SELECT RecipeId FROM Recipes WHERE Recipes.Name = 'Lasagna')),
(6, 'Mozzarella Cheese', 2.0, 'lbs', (SELECT RecipeId FROM Recipes WHERE Recipes.Name = 'Lasagna'));

INSERT INTO GroceryItems (GroceryItemId, Name, Quantity, Measure, RecipeId)
VALUES
(7, 'Chicken', 3.0, 'lbs', (SELECT RecipeId FROM Recipes WHERE Recipes.Name = 'Chicken Pot Pie')),
(8, 'Frozen Mixed Vegetables', 16.0, 'oz', (SELECT RecipeId FROM Recipes WHERE Recipes.Name = 'Chicken Pot Pie')),
(9, 'Butter', 1.5, 'lbs', (SELECT RecipeId FROM Recipes WHERE Recipes.Name = 'Chicken Pot Pie'));