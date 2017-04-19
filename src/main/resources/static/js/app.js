angular.module('voting', ['ngRoute']).config(function($routeProvider,$locationProvider){
	'use strict';

	// get rid of angular's obnoxious added window.location stuff
    $locationProvider.hashPrefix('');

	var homeConfig = {
		controller: 'homeControl',
		templateUrl: 'home.html',
		resolve: {
			store: function($location) {
				//voting web token
				var vwt = localStorage.getItem("vwt");
				console.log("vwt", vwt);
				if(vwt) {
					// if we already have one, we are already logged in & can go to 
					// the election page
					return $location.path("/elections");
				}
			}
		}
	};
	//view elections. protected so we can go straight from election to your ballot
	var electionConfig = {
		controller: 'electionControl',
		templateUrl: 'election.html',
	};
	//view your ballot for an election. protected to submit
	var ballotConfig = {
		controller: 'ballotControl',
		templateUrl: 'ballot.html',
		resolve: {
			store: function($location) {
				var vwt = localStorage.getItem("vwt");
				if(vwt) {
					//nothing
				}
				else {
					//need to sign in
					return $location.path("/");
				}
			}
		}
	};
	//view results for an election. not protected
	var resultsConfig = {
		controller: 'resultsControl',
		templateUrl: 'results.html',
	};

	$routeProvider
		.when('/', homeConfig)
		.when('/elections', electionConfig)
		.when('/ballot/:id', ballotConfig)
		.when('/results', resultsConfig);

}).run(function($http) {
	//attach the voting web token to every header so if they are logged in, we know!
	var vwt = JSON.parse(localStorage.getItem("vwt"));

	console.log("attaching password to header if there", vwt);

	if(vwt !== null && vwt.password) $http.defaults.headers.common['Auth-Token'] = vwt.password;

});