(function() {   
	var app = angular.module('store', [ ]); 
	app.controller('StoreController', function($scope, $http) {
        $http({method: 'POST', url: 'js/posts.json'}).success(function(data) {
            $scope.posts = data; // response data 
        });
    });
})(); 
