'use strict';

// TODO: Understand how the .controller function works for angular
angular.module('Shopping_List_app').controller('Grocery_List_Controller',
		[ 'Grocery_List_Service', function(Grocery_List_Service) {
			var self = this;
			var REST_SERVICE_URI = 'recipe';
			self.groceryItem = {
				id : '',
				name : '',
				groceryList : []
			};
			self.recipes = [];
			self.groceryItems = [];

			self.fetchRecipeList = function(){
				Grocery_List_Service.fetchGetRequest(REST_SERVICE_URI + "/getAll").then(function(data) {
					self.recipes = data.map(s => s.name);
					console.log(self.recipes);
				});
			}

            self.fetchGroceryItemsList = function(recipeId){
                Grocery_List_Service.fetchGetRequest(REST_SERVICE_URI + recipeId + "/grocery-items").then(function(data) {
                    self.groceryItems = data;
                    console.log(self.groceryItems);
                });
            }

			self.createRecipe = function(){
				return Grocery_List_Service.fetchPostRequest(REST_SERVICE_URI, self.groceryItem).then( function() {
				self.fetchRecipeList();
				});
			}

			self.fetchRecipeList();
		} ]);