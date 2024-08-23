'use strict';

// TODO: Understand how the .factory function works for angular
angular.module('Shopping_List_app').factory('Grocery_List_Service', ['$http', function($http) {

		var factory = {
		    fetchGetRequest : fetchGetRequest,
		    fetchPostRequest : fetchPostRequest,
		    fetchPutRequest : fetchPutRequest
		}

		return factory;

        function fetchGetRequest(URI) {
            return $http.get(URI).then(function(response) {
                    return response.data;
                }
            );
        }

        function fetchPostRequest(URI, obj) {
            return $http.post(URI, obj).then(function(response) {
                    return response.data;
                }
            );
        }

        function fetchPutRequest(URI, obj) {
            return $http.put(URI, obj).then(function(response) {
                    return response.data;
                }
            );
        }

}]);
