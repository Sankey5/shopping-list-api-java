'use strict';

// TODO: Understand how the .controller function works for angular
angular.module('Shopping_List_app').controller('Grocery_List_Controller',
		[ 'Grocery_List_Service', function(Grocery_List_Service) {
			var self = this;
			self.groceryItem = {
				id : '',
				name : '',
				groceryList : []
			};
			self.groceryItems = [];

			self.fetchGroceryList = function(){
				Grocery_List_Service.fetchGroceryList().then(function(data) {
					self.groceryItems = data.map(s => s.name);
					console.log(self.groceryItems);
				});
			}

			self.createGroceryItem = function(){
				return Grocery_List_Service.createGroceryItem(self.groceryItem).then( function() {
				self.fetchGroceryList();
				});
			}

			self.fetchGroceryList();
		} ]);
