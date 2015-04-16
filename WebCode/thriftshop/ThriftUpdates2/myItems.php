<!DOCTYPE html> 
<html lang="en" ng-app="store">
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
<script type="text/javascript" src="js/8useritems.js"></script> 

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

	<nav class="slide" >

	 <!-- style="position:fixed;" -->
		<ul>

			<!-- change the way this image is diplayed; comoe up with a way to move it from 
			a thumbnail that is out of place to  -->
 				<li><a href="7housing.php">Housing</a></li>
				<li><a href="6electronics.php">Electronics</a></li>
				<li><a href="5clothing.php">Clothing</a></li>
				<li><a href="4tickets.php">Tickets</a></li>
				<li><a href="3sports.php" >Sports</a></li>
				<li><a href="2cars.php" >Vehicles</a></li>
				<li><a href="1others.php" >Other</a></li>
 			<li><a href="main.php">HOME</a></li> 
			<li><a href="myitems.php"><img src = "myface.jpg" width="42" height="42"></a></li>
		</ul>
	</nav>
	
	<div class="content slide">     <!--	Add "slideRight" class to items that move right when viewing Nav Drawer  -->
		<ul class="responsive">
			<li class="header-section">
				<p class="placefiller">Your Items</p>
			</li>

			<li class="body-section">
				<hr>
				
	 			<div ng-controller="StoreController as store"> 
				<!-- 'as' is a keyword to specify an alias like "store" 
					style="background-color: yellow;"
				-->
				<div ng-repeat="product in posts | filter:query | orderBy: 'title' ">
					<!-- <div ng-hide="product.soldOut">  -->
						<div class="theitems">
<!-- 							<img class="thumbs" ng-src="{{product.picture}}" />
 -->						<h1 sytle="float:left"> {{product.title}} </h1>
<!-- 							<p sytle="float:left"> Posted {{product.date | date:"MM/dd/yyyy 'at' h:mma"}}<p>
							<h2 style="float:right"> {{product.price | currency}}</h2> -->
							<p> {{product.message}} </p><br>
							<a href = "item.html"><button type="button" class="btn btn-primary"  style="float:right">View In Detail</button></a> <!-- ng-show="product.canPurchase" -->
							<br><br>
							<hr>
							<br>
						</div>
<!-- 					</div>
 -->				
				</div>
<!-- 				<p class="placefiller">Filler</p>	
 -->

			</li>
			<li class="footer-section">
				<img src = "img/GTthrift.jpg" style="width: 100%; height: 100%"></img>
			</li>
		</ul>
	</div>
	
</div>

	
</body> 
</html>







