'use strict';

// TODO: Understand how the .factory function works for angular
angular.module('Shopping_List_app').factory('Grocery_List_Service', ['$http', function($http) {

		var REST_SERVICE_URI = 'grocery-list/';

		var factory = {
			fetchGroceryList : fetchGroceryList,
			createGroceryItem : createGroceryItem
		};

		return factory;

		function fetchGroceryList() {
			return $http.get(REST_SERVICE_URI).then(function(response) {
					return response.data;
				}
			);
		}

		function createGroceryItem(groceryItem) {
			return $http.post(REST_SERVICE_URI, groceryItem).then(function(response) {
					return response.data;
				}
			);
		}

}]);
