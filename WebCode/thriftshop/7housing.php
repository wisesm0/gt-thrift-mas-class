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

	// echo $html->find('#post_id', 0);
	// var_dump($code->outertext); 
	
?>

<head> 

<title>Plain GT ThriftShop</title> 

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
<script type="text/javascript" src="js/7housing.js"></script> 
<script>

</script>

<!--[if lt IE 9]>
<script src="js/html5shiv.js"></script>
<![endif]-->
</head> 
 
<body> 

<div class="container">

	<header class="slide">     <!--	Add "slideRight" class to items that move right when viewing Nav Drawer  -->
		<ul id="navToggle" class="burger slide">    <!--	Add "slideRight" class to items that move right when viewing Nav Drawer  -->
			<li></li><li></li><li></li>
		</ul>

			<!-- http://codepen.io/PageOnline/pen/nCfAj-->		
			<div id="lebarCover" style="display: inline-block; ">
			<form> <!-- id="demo-b" -->
      			<input id ="search" ng-model="query" type="search" placeholder="search" autofocus>
			</form>
		</div>
		<div id="plussage"><button id="plussageSize">
		<a href="addItem.html"><img src="http://iconshow.me/media/images/ui/ios7-icons/png/128/plus-round.png" width="30" height ="30"></button>
		</a>
		</div>


<!-- 		<h1>Search Bar ()&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; +</h1>   
 -->
<!-- 		says gt thrift shop in html view
<div id="tfheader">
		<form id="tfnewsearch" method="get" action="http://www.google.com">
		        <input type="text" class="tftextinput" name="q" size="21" maxlength="120"><input type="submit" value="search" class="tfbutton">
		</form>
	<div class="tfclear"></div>
	</div>

 -->	
	</header>
<div ng-controller="StoreController as store"> 
	<nav class="slide" >

	 <!-- style="position:fixed;" -->
	 <!-- change the way this image is diplayed; comoe up with a way to move it from 
			a thumbnail that is out of place to  -->
			<!-- will currently hardcode posting categories -->
			<!-- home will be "electronics";;; if I screw this up, for demo I will make 8 pages-->
		<ul>
<!-- 		//ng-href="{{acas.url}}"-->			
 				<li><a href="7housing.php" class="active">Housing</a></li>
				<li><a href="6electronics.php" >Electronics</a></li>
				<li><a href="5clothing.php">Clothing</a></li>
				<li><a href="4tickets.php">Tickets</a></li>
				<li><a href="3sports.php">Sports</a></li>
				<li><a href="2cars.php" >Vehicles</a></li>
				<li><a href="1others.php" >Other</a></li>
 			<li><a href="main.php">HOME</a></li> 
			<li><a href="myItems.php"><img src = "myface.jpg" width="42" height="42"></a></li>
		</ul>
	</nav>

	<div class="content slide">     
		<!--	Add "slideRight" class to items that move right when viewing Nav Drawer  -->
		<ul class="responsive">
			<li class="header-section">
				<!-- add category for the viewing : currently the category is "all items" -->
				<p class="placefiller">Viewing: {{acases[6].value}}</p>
			</li>

			<li class="body-section">
				<hr>
				
				<!-- 'as' is a keyword to specify an alias like "store" 
					style="background-color: yellow;"
				-->

				<!-- in the next part if any post contains contain the ... -->
				<div ng-repeat="product in posts | filter:query" > <!-- ng-class="{'test-class': doesItemMatch(product)}" -->
					<!-- <div ng-hide="product.soldOut">  -->
						<div class="theitems">
						<a href = "https://facebook.com/{{product.author_id}}"> <img style = "padding:2px" ng-src="https://graph.facebook.com/{{product.author_id}}/picture?type=square"></a>
						<h1 sytle="text-align:center"> {{product.author_name}} </h1>
						<h2 sytle="text-align:center"> {{product.title}} </h2>
						<img class="thumbs" ng-src="{{product.picture}}" ng-hide="product.picture==''"/>
<!-- 						<img class="thumbs" ng-src="http://i.imgur.com/OVIDf0d.jpg" ng-show="product.picture==''"/>
 --> <!-- 						<p sytle="float:left"> Posted {{product.date | date:"MM/dd/yyyy 'at' h:mma"}}<p>
							<h2 style="float:right"> {{product.price | currency}}</h2> -->
							<p> {{product.message}} </p><br>
							<!-- if no price listed just say N/A -->
							<p style="float:right"> <b>Category: </b>{{product.category}} </p><br><br>
							<p style="float:right"> <b>Date: </b>{{product.date | date:'medium'}} </p><br><br>
<!-- 							<button class="btn btn-primary" type="button" ng-click="doesItemMatch(product)" style="float:right">fuck it</button>
 -->
							<form action="7housingItem.php" method = 'post'>
								<input type="hidden" name="author" value="{{product.author_name}}">
								<input type="hidden" name="post_id" value="{{product.post_id}}">
								<input type="hidden" name="place" value="{{doesItemMatch(product)}}">

<!-- 								<input type="hidden" name="title" value="{{product.title}}">
								<input type="hidden" name="message" value="{{product.message}}">
								<input type="hidden" name="category" value="{{product.category}}">
								<input type="hidden" name="pic" value="{{product.picture}}">
								<input type="hidden" name="date" value="{{product.date}}"> -->
								<button type="submit" class="btn btn-primary" style="float:right">View In Detail</button>
								<!-- ng-show="product.canPurchase" -->
							</form>

							<br><br>
							<hr>
							<br>
				</div>
<!-- 					</div>
 -->				
	</div>
<!-- 				<p class="placefiller">Filler</p>	
 -->
<!-- 
<div ng-if="video == video.large">
</div>
<div ng-if="video != video.large">
</div> -->

			</li>
			<li class="footer-section">
<!-- 				<p class="placefiller">FOOTER</p>
 -->				<img src = "img/GTthrift.jpg" style="width: 100%; height: 100%"></img>
			</li>
		</ul>
	</div>
	
</div>

<!--<script> document.cookie="post_name=" + product.post_id; </script> -->

</body> 
</html>







