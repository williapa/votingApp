angular.module('voting')
	.controller('resultsControl', function electionControl($http, $scope, $routeParams, $filter, $location) {

		$scope.vwt = false;
		var token = localStorage.getItem("vwt");

		if(token) {
			$scope.vwt = true;
		}

		$scope.logout = function () {
			localStorage.removeItem("vwt");
			setTimeout(function() {
				$location.path("/");
			},250);

		};

	});