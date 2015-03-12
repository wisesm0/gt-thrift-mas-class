(function() {   
	var app = angular.module('store', [ ]); 
	app.controller('StoreController', function($scope, $http) {
		// js/post2.json http://genuine-amulet-864.appspot.com/
		// think about the google cloud platform hosting
		// http://en.wikipedia.org/wiki/Same-origin_policy
		// http://genuine-amulet-864.appspot.com/

        // this works somehow
        // var url2 = "http://genuine-amulet-864.appspot.com/?callback=JSON_CALLBACK";
        // var url2 = "https://public-api.wordpress.com/rest/v1/sites/wtmpeachtest.wordpress.com/posts?callback=JSON_CALLBACK";
        var url1 = 'http://2.genuine-amulet-864.appspot.com/';
        $http({method: 'GET', url: url1}).success(function(data) {
            $scope.posts = data; 
            // console.log(data);
            // window.tempg = data;
            // response data
            // console.write(data);  
        });


        /*

        You need to configure the server to send a JavaScript response with Content-Type: application/javascript
        */

        // $http.jsonp(url2).success(function(data) {
        //     $scope.posts = data; 
        //     // window.tempg = data;
        //     console.log(data);
        //     // response data
        //     // console.write(data);  
        // });
    });
})(); 
