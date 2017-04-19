angular.module('voting')
	.controller('electionControl', function electionControl($http, $scope, $routeParams, $filter, $location) {
			
		$scope.elections = [];

		$scope.electionData = false;

		$http({
			url: '/api/election/',
			method: 'GET',			
		}).then(function(response){
			if(response.data) {
				$scope.elections = response.data;
				$scope.electionData = true;
				for(var i = 0; i < $scope.elections.length; i++){
					var e = $scope.elections[i];
					var startDate = new Date(e.startDate);
					var endDate = new Date(e.endDate);
					$scope.elections[i].startDate = startDate.toLocaleString();
					$scope.elections[i].endDate =endDate.toLocaleString();
				};
			} else {
				console.log("no elections. ");
			}
		}, function(error) {
			console.log("error on server: ", error);
		});

		$scope.goToElection = function(id) {
		
			//proably a $location with some passed params
		
		};

		$scope.logout = function () {
			localStorage.removeItem("vwt");
		};

		$scope.goHome = function () {
			$location.path("/");
		};

	});