(function() {   
	var app = angular.module('store', [ ]); 
	app.controller('StoreController', function() {
		this.products = gems;  // a property of our controller
	});
	// the second parameter is an anonymous function
	// will be executed when 'StoreController' is called

	var gems = [
	{
		name: "Ikea Furniture", // these are called properties
		price: 2, 
		description: "This is a nice piece of thing I am selling.",
		canPurchase: true,
		soldOut: false,
		date: 1288323623406
	}, 
	{
		name: "Golden Lamp", // these are called properties
		price: 5.95, 
		description: "You are the man",
		canPurchase: true,
		soldOut: false,
		date: 1128323623006
	},

	{
		name: "Dog", // these are called properties
		price: 1000.95, 
		description: "Buy my dog",
		canPurchase: true,
		soldOut: false,
		date: 1211333623006
	}

	];

})(); 
