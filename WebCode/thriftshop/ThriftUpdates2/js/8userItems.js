(function() {   
	var app = angular.module('store', [ ]); 

	app.controller('StoreController', function($scope, $http) {
		// js/post2.json http://genuine-amulet-864.appspot.com/
		// think about the google cloud platform hosting
		// http://en.wikipedia.org/wiki/Same-origin_policy

        // this works somehow
        // http://2.genuine-amulet-864.appspot.com/getlistings/1
        test = document.URL; 
        var va = 9; 

        var url1 = 'http://2.genuine-amulet-864.appspot.com/getlistings/user/10200204025/1'; 

        // var url0 = 'http://2.genuine-amulet-864.appspot.com/getlistings/1'; 


        var app = angular.module('app', []);
        $scope.value = "All Listings";

        $scope.acases = [
            {id:0, value:"Others", url:"1others.php"},
            {id:1, value:"Cars", url:"2cars.php"},
            {id:2, value:"Sports", url:"3sports.php"},
            {id:3, value:"Tickets", url:"4tickets.php"},
            {id:4, value:"Clothing", url:"5clothing.php"},
            {id:5, value:"Electronics", url:"6electronics.php"},
            {id:6, value:"Housing", url:"7housing.php"},
            {id:7, value:"all listings", url:"#"}
           // {id:0, value:"Others", url:"http://2.genuine-amulet-864.appspot.com/getlistings/others/1"},
           // {id:1, value:"Cars", url:"http://2.genuine-amulet-864.appspot.com/getlistings/cars/1"},
           // {id:2, value:"Sports", url:"http://2.genuine-amulet-864.appspot.com/getlistings/sports/1"},
           // {id:3, value:"Tickets", url:"http://2.genuine-amulet-864.appspot.com/getlistings/tickets/1"},
           // {id:4, value:"Clothing", url:"http://2.genuine-amulet-864.appspot.com/getlistings/clothing/1"},
           // {id:5, value:"Electronics", url:"http://2.genuine-amulet-864.appspot.com/getlistings/electronics/1"},
           // {id:6, value:"Housing", url:"http://2.genuine-amulet-864.appspot.com/getlistings/housing/1"},
           // {id:7, value:"All Listings", url:"http://2.genuine-amulet-864.appspot.com/getlistings/1"},
           // {id:8, value:"My Listings", url:"http://2.genuine-amulet-864.appspot.com/getlistings/user/10200204025/1"},
        ];

        $scope.theurl = "#";

        $scope.changeURL = function(item) {
            // console.log($scope.acases.url); 
            for(var i in $scope.acases) {
                    var match = $scope.acases[i]; 
                    if(item.url === match.url) {
                        // console.log(item.url);
                        url1 = item.url;  
                        console.log(url1); 
                    }
            }

            $scope.theurl = url1; 
            return url1; 
            // console.log("yeah");
        }

        // $scope.setViewing = function() {
        //     console.write("yeah")
        //     // alert("item"); 
        //     // $scope.value = item; 
        //     // $window.alert("hellllooo"); 
        //     // var url1 = item; 
        // }



        // var url1 = 'js/item1.json'; 
        /*
            http://localhost:8080/getlistings/1
            Category listings ( 1 is the page no, you will get 20 per page sorted by created time of the post on facebook with the most recent first)
            http://localhost:8080/getlistings/others/1  
            http://localhost:8080/getlistings/cars/1
            http://localhost:8080/getlistings/sports/1
            http://localhost:8080/getlistings/tickets/1
            http://localhost:8080/getlistings/clothing/1
            http://localhost:8080/getlistings/home/1
            http://localhost:8080/getlistings/electronics/1
            http://localhost:8080/getlistings/housing/1
            By user listings (userid/pageno)
            http://localhost:8080/getlistings/user/812955955458492/1
        */



        // for local


        $http({method: 'GET', url: url1}).success(function(data) {
            $scope.posts = data; 

            $scope.doesItemMatch = function(item) {
                for(var i in $scope.posts) {
                    var match = $scope.posts[i]; 
                    if(item.post_id === match.post_id) {
                        // alert(match.author_name + " " + match.post_id + " " + i); 
                        va = i; 
                        var pid = match.post_id;
                        // console.log(i);
                        return i; 
                        // alert(va);
                        // url1 = 'js/item1.json'; 
                    }
                }
            }
            // console.log(data);
            // window.tempg = data;
            // response data
            // console.write(data);  
        });

        // for deployed
        // $http({method: 'GET', url: $scope.acases[7].url}).success(function(data) {
        //     $scope.posts = data; 

        //     $scope.doesItemMatch = function(item) {
        //         for(var i in $scope.posts) {
        //             var match = $scope.posts[i]; 
        //             if(item.post_id === match.post_id) {
        //                 // alert(match.author_name + " " + match.post_id + " " + i); 
        //                 va = i; 
        //                 var pid = match.post_id;
        //             }
        //                 // return i; 
        //                 // alert($scope.inde)
        //                 // url1 = 'js/item1.json'; 
        //         }
        //     }
        //     // console.log(data);
        //     // window.tempg = data;
        //     // response data
        //     // console.write(data);  
        // });

        // $scope.inde = va; 



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
