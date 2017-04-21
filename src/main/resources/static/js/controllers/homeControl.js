angular.module('voting')
	.controller('homeControl', function homeControl($http, $scope, $routeParams, $location) {

		$scope.regions = ["Northeast", "Northwest", "Southeast", "Southwest", "Midwest"];
		$scope.newVoter = {name: "", password: "", region: "Northeast"};
		$scope.voter = {name: "", password: ""};
		$scope.error = "";
		$scope.reg = true;

		$scope.toggle = function() {
			$scope.reg = !$scope.reg;
		}

		$scope.login = function() {
		
			var voter = {};
			
			voter["name"] = $scope.voter.name;
			voter["password"] = $scope.voter.password;
			
			$http({
				url: '/api/voter/login',
				method: 'POST',
				data: voter,
			}).then(function(response) {
				if(response.data) {
					console.log("success: ", response.data);
					localStorage.setItem("vwt", JSON.stringify(response.data));
					
				} else {
					console.log("name/password don't match.");
					$scope.error = "name and password don't match.";
				}
			}, function(error) {
				console.log("there was an error: ", error);
				$scope.error = error;
			}).finally(function() {
				if(localStorage.getItem("vwt") !== null) $location.path("/elections");
			});
			
		};

		$scope.register = function() {

			var voter = {};        

			console.log("region id: ", $scope.regions.indexOf($scope.newVoter.region));                                                                             
			voter["name"] = $scope.newVoter.name;
			voter["password"] = $scope.newVoter.password;
			voter["regionId"] = $scope.regions.indexOf($scope.newVoter.region)+1;

			$http({
				url:'/api/voter/signup',
				method:'POST',
				data: voter,
			}).then(function(response) {
				if(response.data) {
					console.log("success: ", response.data);
					localStorage.setItem("vwt", JSON.stringify(response.data));
					$location.path("/elections");
				} else {
					$scope.error = "Name is already taken. ";
				}
			}, function(error) {
				console.log("there was an error: ", error);
				$scope.error = error;
			}).finally(function() {
				if(localStorage.getItem("vwt") !== null) $location.path("/elections");
			});

		};

	});