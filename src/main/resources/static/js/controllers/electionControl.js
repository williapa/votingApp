angular.module('voting')
	.controller('electionControl', function electionControl($http, $scope, $routeParams, $filter, $location) {
			
		$scope.elections = [];

		$http({
			url: '/api/election/',
			method: 'GET',			
		}).then(function(response){
			if(response.data) {
				$scope.elections = response.data;
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

			// if we are signed in, we will go to our ballot. If we aren't we will see the election results 
			var vwt = localStorage.getItem("vwt");

			if(vwt !== null) { 

				regionId = JSON.parse(vwt).regionId;

				return $location.path("/ballot/"+regionId);

			} else {

				return $location.path("/results/1");
			}
		
		};

		$scope.logout = function () {

			var vwt = localStorage.getItem("vwt");

			while(vwt !== null) {

				localStorage.removeItem("vwt");

				vwt = localStorage.getItem("vwt");

			}

			$location.path("/");

		};

});