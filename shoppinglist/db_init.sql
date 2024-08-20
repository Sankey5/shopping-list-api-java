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

--Drop old test data
DELETE FROM GroceryItems WHERE 1=1;
DELETE FROM Recipes WHERE 1=1;

-- Insert in test data if it doesn't exist
INSERT INTO Recipes (Name) VALUES
    ('Chicken Bake'),
    ('Lasagna'),
    ('Chicken Pot Pie');

INSERT INTO GroceryItems (Name, Quantity, Measure, RecipeId)
VALUES
('Chicken', 3.0, 'lbs', (SELECT RecipeId FROM Recipes WHERE Name = 'Chicken Bake')),
('Mozzarella Cheese', 1.5, 'lbs', (SELECT RecipeId FROM Recipes WHERE Name = 'Chicken Bake')),
('Spaghetti Sauce', 16.0, 'oz', (SELECT RecipeId FROM Recipes WHERE Name = 'Chicken Bake'));

INSERT INTO GroceryItems (Name, Quantity, Measure, RecipeId)
VALUES
('Ground Beef', 2.0, 'lbs', (SELECT RecipeId FROM Recipes WHERE name = 'Lasagna')),
('Lasagna Noodles', 16.0, 'oz', (SELECT RecipeId FROM Recipes WHERE name = 'Lasagna')),
('Mozzarella Cheese', 2.0, 'lbs', (SELECT RecipeId FROM Recipes WHERE name = 'Lasagna'));

INSERT INTO GroceryItems (Name, Quantity, Measure, RecipeId)
VALUES
('Chicken', 3.0, 'lbs', (SELECT RecipeId FROM Recipes WHERE name = 'Chicken Pot Pie')),
('Frozen Mixed Vegetables', 16.0, 'oz', (SELECT RecipeId FROM Recipes WHERE name = 'Chicken Pot Pie')),
('Butter', 1.5, 'lbs', (SELECT RecipeId FROM Recipes WHERE name = 'Chicken Pot Pie'));