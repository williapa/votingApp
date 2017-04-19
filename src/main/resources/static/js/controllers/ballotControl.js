angular.module('voting')
	.controller('ballotControl', function electionControl($http, $scope, $routeParams, $filter, $location) {
			
		$scope.questions = [];
		$scope.ballotId = $routeParams.id;

		//build questionData
		$http({
			url: 'api/ballotquestion/forballot/'+$routeParams.id,
			method: 'GET',
		}).then(function(response) {
			if(response.data){
				console.log("questions for ballot id "+ $scope.balotId  + ": ", response.data);
				$scope.questions = response.data;
			} else {
				console.log("no questions for this ballot yet. ");
			}
		}, function(error){
			console.log("there was a server error?", error);
		});

		$scope.goToElection = function(id) {
		
			//proably a $location with some passed params
		
		};

		$scope.logout = function () {
			localStorage.removeItem("vwt");
		};

		$scope.goHome = function () {
			$location.path("/");
		}

	});