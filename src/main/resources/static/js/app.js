angular.module('voting', ['ngRoute']).config(function($routeProvider){
	'use strict';
	//login & signup, not protected
	var homeConfig = {
		controller: 'homeControl',
		templateUrl: 'home.html',
		resolve: {
			store: function($location) {
				//voting web token
				var vwt = localStorage.getItem("vwt");
				if(vwt) {
					// if we already have one, we are already logged in & can go to 
					// the election page
					return $location.path("/election");
				}
			}
		}
	};
	//view elections. protected so we can go straight from election to your ballot
	var electionConfig = {
		controller: 'electionControl',
		templateUrl: 'election.html',
		resolve: {
			store: function($location) {
			var vwt = localStorage.getItem("vwt");
				if(vwt) {
					// we good
				} else {
					// need to sign in
					return $location.path("/");
				}
			}
		}
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
		resolve: {
			store: function($location) {
				var vwt = localStorage.getItem("vwt");
			}
		}
	};

	$routeProvider
		.when('/', homeConfig)
		.when('/elections', electionConfig)
		.when('/ballot/:id', ballotConfig)
		.when('/results', resultsConfig)
		.otherwise({
			redirectTo: "/"
		});

}).run(function($http) {
	//attach the voting web token to every header so if they are logged in, we know!
	$http.defaults.headers.common['Auth-Token'] = localStorage.getItem("vwt");
});

// move these to controller scripts