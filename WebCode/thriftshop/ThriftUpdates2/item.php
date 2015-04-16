<!DOCTYPE html> 
<html lang="en" ng-app="store">
<?php
	$author = $_POST['author']; 
	$prodId = $_POST['post_id']; 
	$place = $_POST['place']; 
	// $title = $_POST['title']; 
	// $message = $_POST['message']; 
	// $category = $_POST['category']; 
	// $picture = $_POST['pic']; 
	// $date = $_POST['date']; 
	// echo $theValue;
	// echo $html->find('#post_id', 0);
	// var_dump($code->outertext); 
	
?>
<head> 

<title>The Item</title> 

<meta charset="utf-8">
<meta name="viewport" content="initial-scale=1.0,user-scalable=no,maximum-scale=1" media="(device-height: 568px)">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="HandheldFriendly" content="True">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<!-- Style Sheets --> 
<link rel="stylesheet" type="text/css" media="all" href="css/reset.css" />
<link rel="stylesheet" type="text/css" media="all" href="css/trunk.css" />

<!-- Scripts --> 
<script type="text/javascript">
	if (typeof jQuery == 'undefined')
		document.write(unescape("%3Cscript src='js/jquery-1.9.js'" + 
								"type='text/javascript'%3E%3C/script%3E"))
</script>
<script type="text/javascript" language="javascript" src="js/trunk.js"></script>
<!-- <link href="css/bootstrap.min.css" rel="stylesheet">
 -->
<script type="text/javascript" src="js/angular.min.js"></script>
<script type="text/javascript" src="js/app2.js"></script> 
<!--[if lt IE 9]>
<script src="js/html5shiv.js"></script>
<![endif]-->


</head> 

<script>

// id = "one"; // Or whatever
// var entry = objJsonResp[id];

var php_var = "<?php echo $prodId; ?>";
console.log(php_var); 


// console.log(GetObjectKeyIndex(url1, php_var))
// function GetObjectKeyIndex(obj, keyToFind) {
//     // var i = 0, key;
//     for (var keyToFind in json) {
//     	console.log(keyToFind + ' is ' + json[keyToFind]);
// 	}
//     // for (key in obj) {
//     // 	console.log(key); 
//     //     if (key == keyToFind) {
//     //         return i;
//     //     }
//     //     i++;
//     // }
//     // return null;
// }

</script>
 
<body> 

<div class="container">

	<header class="slide">     <!--	Add "slideRight" class to items that move right when viewing Nav Drawer  -->
		<ul id="navToggle" class="burger slide">    <!--	Add "slideRight" class to items that move right when viewing Nav Drawer  -->
			<li></li><li></li><li></li>
		</ul>
		<div id="lebarCover" style="float:left">
			<form> <!-- id="demo-b" -->
				<input type="search" placeholder="Search">
			</form>
		</div>
		<div id="plussage"><button id="plussageSize"><a href="addItem.html"><img src="http://iconshow.me/media/images/ui/ios7-icons/png/128/plus-round.png" width="30" height ="30"></a></button> </div>
<!-- 		says gt thrift shop in html view
<div id="tfheader">
		<form id="tfnewsearch" method="get" action="http://www.google.com">
		        <input type="text" class="tftextinput" name="q" size="21" maxlength="120"><input type="submit" value="search" class="tfbutton">
		</form>
	<div class="tfclear"></div>
	</div>

 -->	
	</header>

	<nav class="slide" >
	 <!-- style="position:fixed;" -->
		<ul>
			<!-- change the way this image is diplayed; comoe up with a way to move it from 
			a thumbnail that is out of place to  -->
			<li><a href="#">Housing</a></li>
			<!-- home will be "electronics";;; if I screw this up, for demo I will make 8 pages-->
			<li><a href="#">Electronics</a></li>
			<li><a href="#">Home</a></li>
			<li><a href="#">Clothing</a></li>
			<li><a href="#">Tickets</a></li>
			<li><a href="#">Sports</a></li>
			<li><a href="#">Vehicles</a></li> <!-- Motorcycles, Scooters, and Cars -->
			<li><a href="#">Others</a></li>
			<!-- home will be "getalllistings" -->
			<li><a href="main.php" class="active">HOME</a></li>
			<li><a href="myItems.html"><img src = "myface.jpg" width="42" height="42"></a></li>
		</ul>
	</nav>

	<!-- get post id data  -->
	<div ng-controller="StoreController as store"> 
		<div class="content slide">     <!--	Add "slideRight" class to items that move right when viewing Nav Drawer  -->
			<ul class="responsive">
				<li class="header-section">
					<!-- get the id of the current item, with a different json url... -->
					<p class="placefiller">Posted by <a href="https://facebook.com/{{posts[<?php echo $place ?>].author_id}}">{{posts[<?php echo $place ?>].author_name}}</a></p>
					<center><img style = "padding:2px" ng-src="https://graph.facebook.com/{{posts[<?php echo $place ?>].author_id}}/picture?type=square"></center>
					<p class="placefiller">{{posts[<?php echo $place ?>].title}}</p>
				</li>

				<li class="body-section" style="text-align:center;">
	<!-- 	 			<p class="placefiller">				</p>-->
					<!-- if no price listed just say N/A -->
					<article>{{posts[<?php echo $place ?>].date | date:'medium'}} </article><br>

<!-- 					include an ng-if if no picture exists: -->
					<span ng-if="checked" class="animate-if">This is removed when the checkbox is unchecked.</span>
		 			<img src= "{{posts[<?php echo $place ?>].picture}}" style="max-width:40%;height:auto;">	
		 			<br><br>
		 			<article>{{posts[<?php echo $place ?>].message}}</article>
		 			<br><br>
		 			<button>Reply</button>
				</li>


				<li class="footer-section" >
	<!-- 		style = "text-align:center"		<p class="placefiller">FOOTER</p>-->
					<div id="fb-root"></div>
					<script>(function(d, s, id) {
					  var js, fjs = d.getElementsByTagName(s)[0];
					  if (d.getElementById(id)) return;
					  js = d.createElement(s); js.id = id;
					  js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.0";
					  fjs.parentNode.insertBefore(js, fjs);
					}(document, 'script', 'facebook-jssdk'));</script>	

	<!-- 				http://www.e-socialite.com/2014/solved-facebook-comments-100-width-problems
	data-mobile="true" - find a way to associate it with the current url -->				
					<div class="fb-comments" data-width = "100%" data-href="https://www.facebook.com/groups/199456403537988/permalink/452793968204229/
" data-numposts="5" data-colorscheme="light" ></div>		
				</li>
			</ul>
		</div>
	</div>
	
</div>

	
</body> 
</html>







