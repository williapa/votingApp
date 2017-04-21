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

		var vwt = localStorage.getItem("vwt");
		$scope.username = "";
		$scope.loggedin = false;

		if(vwt !== null) {

			$scope.loggedin = true;

			$scope.username = JSON.parse(vwt).name;
		}

		$scope.winner = function(qid) {
			$location.path("/winner/"+qid);
		}

		$scope.logout = function () {

			while(vwt !== null) {

				localStorage.removeItem("vwt");

				vwt = localStorage.getItem("vwt");

			}

			$location.path("/");

		};

	});