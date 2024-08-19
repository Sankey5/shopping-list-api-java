CREATE TABLE IF NOT EXISTS Recipes(
    RecipeId INT NOT NULL AUTO_INCREMENT,
    Name VARCHAR(30) NOT NULL,
    PRIMARY KEY (RecipeId));

CREATE TABLE IF NOT EXISTS GroceryItems(
    GroceryItemId INT NOT NULL AUTO_INCREMENT,
    Name VARCHAR(30) NOT NULL,
    Quantity DOUBLE NOT NULL,
    Measure VARCHAR(10) NOT NULL,
    FOREIGN KEY (RecipeId) REFERENCES Recipes(RecipeId)),
    CONSTRAINT PK_GroceryItem PRIMARY KEY (GroceryItemId, RecipeId);

-- Drop old test data
TRUNCATE TABLE Recipes;
TRUNCATE TABLE GroceryItems;

-- Insert in test data if it doesn't exist
INSERT INTO Recipes (Name)
VALUES ("Chicken Bake"),
    ("Lasagna"),
    ("Chicken Pot Pie");

INSERT INTO GroceryItems (Name, Quantity, Measure, RecipeId)
VALUES
("Bananas", 1.0, "lbs", (SELECT RecipeId FROM Recipes WHERE name = "Chicken Bake")),
("Ground Beef", 2.5, "lbs", (SELECT RecipeId FROM Recipes WHERE name = "Chicken Bake")),
("Salt", 2.2, "tpbs", (SELECT RecipeId FROM Recipes WHERE name = "Chicken Bake"));

INSERT INTO GroceryItems (Name, Quantity, Measure, RecipeId)
VALUES
("Bananas", 1.0, "lbs", (SELECT RecipeId FROM Recipes WHERE name = "Lasagna")),
("Ground Beef", 2.5, "lbs", (SELECT RecipeId FROM Recipes WHERE name = "Lasagna")),
("Salt", 2.2, "tpbs", (SELECT RecipeId FROM Recipes WHERE name = "Lasagna"));

INSERT INTO GroceryItems (Name, Quantity, Measure, RecipeId)
VALUES
("Bananas", 1.0, "lbs", (SELECT RecipeId FROM Recipes WHERE name = "Chicken Pot Pie")),
("Ground Beef", 2.5, "lbs", (SELECT RecipeId FROM Recipes WHERE name = "Chicken Pot Pie")),
("Salt", 2.2, "tpbs", (SELECT RecipeId FROM Recipes WHERE name = "Chicken Pot Pie"));