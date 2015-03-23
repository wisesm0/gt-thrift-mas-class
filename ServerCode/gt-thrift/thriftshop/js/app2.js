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
        test = document.URL; 

        var url1 = 'http://2.genuine-amulet-864.appspot.com/getalllistings/';
        // alert(test);
        // alert(post_id)
        if (document.URL.indexOf("item") > -1) {
            // var url1 = 'js/item1.json'; 
            // alert(document.cookie.replace(/(?:(?:^|.*;\s*)post_name\s*\=\s*([^;]*).*$)|^.*$/, "$1"));
            // alert(url1);
        }

        // if (document.URL='localhost/thriftshop/index.html'){
        //     var url1 = 'js/post2.json'; 
        //     // alert(document.URL);
        // }
        // if (document.URL='localhost/thriftshop/item.html'){
        //     url1 = 'js/item1.json'; 
        //     alert(document.URL);
        // }

        // i need to change this url when I get the post id of the view_selected_item button
        // 
        // alert(document.URL); 
        // if (at item.html page)



        $http({method: 'GET', url: url1}).success(function(data) {
            $scope.posts = data; 
            // console.log(data);
            // window.tempg = data;
            // response data
            // console.write(data);  
        });


        /* Find a way to query all the values of the current item based on the post id
        */


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
