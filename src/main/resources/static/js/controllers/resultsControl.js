angular.module('voting')
	.controller('resultsControl', function electionControl($http, $scope, $routeParams, $filter, $location) {

		var electionId = $routeParams.rid;
		$scope.voteresults = [];

		$http({
			url: '/api/vote/election/'+electionId,
			method: 'GET',
		}).then(function(response) {
			if(response.data) {
				$scope.voteresults = response.data;
			} else {
				console.log("error!!!");
			}
		}, function(error) {
			console.log("error");
			console.log(error);
		});

		$scope.vwt = false;
		var token = localStorage.getItem("vwt");

		if(token) {
			$scope.vwt = true;
		}

		$scope.results 

		$scope.logout = function () {

			var vwt = localStorage.getItem("vwt");

			while(vwt !== null) {

				localStorage.removeItem("vwt");

				vwt = localStorage.getItem("vwt");

			}

			$location.path("/");

		};

	});